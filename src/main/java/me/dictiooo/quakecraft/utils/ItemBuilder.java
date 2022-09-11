package me.dictiooo.quakecraft.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack is;
    private ItemMeta im;

    public ItemBuilder(Material m){
        this(m, 1);
    }

    public ItemBuilder(Material m, int amount){
        is = new ItemStack(m, amount);
        im = is.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name){
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String name){
        List<String> lore = new ArrayList<>();
        if(im.getLore() != null){
            lore = im.getLore();
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchant, int level){
        im.addEnchant(enchant, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        im.setUnbreakable(unbreakable);

        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag){
        im.addItemFlags();
        is.setItemMeta(im);
        return this;
    }

    public ItemStack build(){
        return is;
    }
}