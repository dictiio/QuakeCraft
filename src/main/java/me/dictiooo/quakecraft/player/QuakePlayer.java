package me.dictiooo.quakecraft.player;

import me.dictiooo.quakecraft.QuakeCraft;
import me.dictiooo.quakecraft.events.QuakeKillEvent;
import me.dictiooo.quakecraft.game.Game;
import me.dictiooo.quakecraft.manager.SpawnpointManager;
import me.dictiooo.quakecraft.utils.GlobalVariables;
import me.dictiooo.quakecraft.utils.ItemBuilder;
import me.dictiooo.quakecraft.utils.Messages;
import me.dictiooo.quakecraft.utils.QuakeGun;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class QuakePlayer {

    private UUID uuid;
    private Game game;
    private QuakePlayer lastAttacker;
    private int kills;
    private int killsSinceDeath = 0;
    private int deaths;
    private boolean alive;

    public QuakePlayer(UUID uuid, Game game){
        this.game = game;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setKills(int kills) {

        this.kills = kills;
        game.getPlayerManager().getQuakePlayerToScore().put(this, this.kills);
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKillsSinceDeath() {
        return killsSinceDeath;
    }

    public QuakePlayer getLastAttacker() {
        return lastAttacker;
    }

    // Adds 1 kill to the Quake Player.
    public void addKill(){

        setKills(this.kills + 1);
        this.killsSinceDeath++;
    }

    // Adds 1 death to the Quake Player.
    public void addDeath(){
        setDeaths(this.deaths + 1);
    }

    // Returns true if the Quake Player is playing and false if it is eliminated.
    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    // Gives the starting kit to the Quake Player.
    public void giveKit(){
        getPlayer().getInventory().addItem(QuakeGun.getItem());
    }

    public void setLastAttacker(QuakePlayer quakePlayer){
        lastAttacker = quakePlayer;
    }

    public void setLastAttacker(Player player){
        if(game.getPlayerManager().getUuidToQuakePlayer().containsKey(player.getUniqueId())){
            setLastAttacker(game.getPlayerManager().getUuidToQuakePlayer().get(player.getUniqueId()));
        }

    }

    public void kill(){
        QuakeKillEvent killEvent;
        teleport(SpawnpointManager.getRandomSpawnPoint());
        drawSpawnAnimation(getPlayer().getLocation(), 1);
        getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 1));
        if(lastAttacker != null){
            addDeath();
            lastAttacker.addKill();
            lastAttacker.playKillSound();
            killEvent = new QuakeKillEvent(lastAttacker, this);
            if(!lastAttacker.getKillEffect().isEmpty()){
                Messages.broadcast("&e" + getPlayer().getName() + " &7was killed by &e" + lastAttacker.getPlayer().getName() + " &7(" + lastAttacker.getKillEffect() + "&7)", GlobalVariables.PREFIX);
            } else {
                Messages.broadcast("&e" + getPlayer().getName() + " &7was killed by &e" + lastAttacker.getPlayer().getName(), GlobalVariables.PREFIX);
            }

        } else {
            Messages.broadcast("§e" + getPlayer().getName() + " &7 died!");
            addDeath();
            killEvent = new QuakeKillEvent(null, this);
        }
        this.killsSinceDeath = 0;
        QuakeCraft.getInstance().getServer().getPluginManager().callEvent(killEvent);

    }

    public void drawSpawnAnimation(Location location, double radius){
        for(int i = 0; i < 3; i++){
            for (int degree = 0; degree < 360; degree++) {
                double radians = Math.toRadians(degree);
                double x = Math.cos(radians) * radius;
                double z = Math.sin(radians) * radius;
                location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.clone().add(x,i,z), 1, 0, 0, 0, 0);
            }
        }

    }

    public void playKillSound(){
        if(killsSinceDeath < 5) {
            if(killsSinceDeath <= 1){
                getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
            if (killsSinceDeath == 2) {
                getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.2f);
            } else if (killsSinceDeath == 3) {
                getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.4f);
            } else if (killsSinceDeath == 4) {
                getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.6f);
            }
        }
        else{
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
        }
        getPlayer().sendTitle("§a", getKillEffect(), 1, 60, 20);


    }

    public String getKillEffect(){
        String killEffect = "";
        switch(killsSinceDeath){
            case 2:
                killEffect = "§eDouble-kill";
                break;
            case 3:
                killEffect = "§6Triple-kill";
                break;
            case 4:
                killEffect = "§cQuadra-kill";
                break;
            case 5:
                killEffect = "§5§lMULTI-KILL";
                break;
            default:
                if(killsSinceDeath > 5){
                    int extraKills = killsSinceDeath-4;
                    killEffect = "§5§lMULTI-KILL (" + extraKills + ")";
                }
                break;
        }

        return killEffect;
    }

    public void teleport(Location location){
        getPlayer().teleport(location);
    }
}
