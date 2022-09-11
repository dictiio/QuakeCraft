package me.dictiooo.quakecraft.manager;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.display.WaitingSb;
import me.dictiooo.quakecraft.events.QuakeJoinGameEvent;
import me.dictiooo.quakecraft.events.QuakeKillEvent;
import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.phase.ActivePhase;
import me.dictiooo.quakecraft.phase.Phase;
import me.dictiooo.quakecraft.player.QuakePlayer;
import me.dictiooo.quakecraft.utils.GlobalVariables;
import me.dictiooo.quakecraft.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private Game game;

    // General player variables.
    private List<QuakePlayer> quakePlayerList = new ArrayList<>();
    private HashMap<UUID, QuakePlayer> uuidToQuakePlayer = new HashMap<>();
    private HashMap<QuakePlayer, Integer> quakePlayerToScore = new HashMap<>();
    private int maxSlots = GlobalVariables.MAX_SLOTS;
    private int minSlots = GlobalVariables.MIN_SLOTS;

    public PlayerManager(Game game){
        this.game = game;
    }

    // Add a player to the game.
    public void addPlayer(Player player){
        if(uuidToQuakePlayer.containsKey(player.getUniqueId())) {
            Messages.send(player, "&cYou are already in the game!", GlobalVariables.PREFIX, Sound.BLOCK_ANVIL_PLACE);
            return;
        }
        if(game.getCurrentPhase().getType() != Phase.GamePhase.PREGAME && game.getCurrentPhase().getType() != Phase.GamePhase.STARTING) {
            Messages.send(player, "&cThe game already started!", GlobalVariables.PREFIX, Sound.BLOCK_ANVIL_PLACE);
            return;
        }
        if(quakePlayerList.size() >= maxSlots){
            Messages.send(player, "&cThe maximum capacity has been reached!", GlobalVariables.PREFIX, Sound.BLOCK_ANVIL_PLACE);
            return;
        }

        QuakePlayer quakePlayer = new QuakePlayer(player.getUniqueId(), game);

        uuidToQuakePlayer.put(quakePlayer.getUuid(), quakePlayer);
        quakePlayerToScore.put(quakePlayer, 0);
        quakePlayerList.add(quakePlayer);
        QuakeJoinGameEvent joinEvent = new QuakeJoinGameEvent(quakePlayer);
        QuakeCraft.getInstance().getServer().getPluginManager().callEvent(joinEvent);

        Messages.broadcast("&e" + player.getName() + " &7joined the game! &a(" + quakePlayerList.size() + "/" + maxSlots + ")", GlobalVariables.PREFIX, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);

        try{
            quakePlayer.teleport(SpawnpointManager.getLobby());
        } catch (Exception e){
            QuakeCraft.getInstance().getLogger().info("The location of the lobby was not correctly set!");
        }
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setScoreboard(WaitingSb.getWaitingSb());

    }

    // Remove a Quake player from the game.
    public void removePlayer(QuakePlayer quakePlayer){
        if(quakePlayerList.contains(quakePlayer)){
            quakePlayerList.remove(quakePlayer);
            quakePlayerToScore.remove(quakePlayer);
            uuidToQuakePlayer.remove(quakePlayer.getUuid());
            if(game.getCurrentPhase().getType() == Phase.GamePhase.PREGAME){
                Messages.broadcast("&e" + quakePlayer.getPlayer().getName() + " &7left the game! &a(" + quakePlayerList.size() + "/" + maxSlots + ")", GlobalVariables.PREFIX, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                return;
            }
            if(game.getCurrentPhase().getType() == Phase.GamePhase.ACTIVE){
                if(quakePlayerList.size() <= 1){
                    try{
                        quakePlayerList.get(0).setKills(game.getScoreToWin());
                        QuakeKillEvent killEvent = new QuakeKillEvent(null, null);
                        QuakeCraft.getInstance().getServer().getPluginManager().callEvent(killEvent);
                        Messages.broadcast("&e" + quakePlayer.getPlayer().getName() + " &7died!", GlobalVariables.PREFIX);
                    } catch (Exception e){
                        QuakeCraft.getInstance().getLogger().info("Something went wrong! (remove player)");
                        game.cleanup();
                    }
                    return;

                }
            }
        }
    }

    // Returns the list of Quake players.
    public List<QuakePlayer> getQuakePlayerList() {
        return quakePlayerList;
    }

    public HashMap<UUID, QuakePlayer> getUuidToQuakePlayer() {
        return uuidToQuakePlayer;
    }

    public HashMap<QuakePlayer, Integer> getQuakePlayerToScore() {
        return quakePlayerToScore;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public int getMinSlots() {
        return minSlots;
    }

    public void cleanup(){
        quakePlayerList = null;
        quakePlayerToScore = null;
        uuidToQuakePlayer = null;
    }
}
