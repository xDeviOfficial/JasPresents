package pl.jasmc.presents.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.List;

public class RekruItem {

    public Material material;
    public int amount;
    public short data;
    public String displayname;
    public List<String> lore;
    public Color color;
    public String playerName;




    public RekruItem(Material material) {
        this.material = material;
    }

    public RekruItem(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public RekruItem(Material material, int amount, short data) {
        this.material = material;
        this.amount = amount;
        this.data = data;
    }

    public RekruItem(Material material, int amount, short data, String displayName) {
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.displayname = displayName;

    }

    public RekruItem(Material material, int amount, short data, String displayName, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.displayname = displayName;
        this.lore = lore;
    }

    public RekruItem(Material material, int amount, short data, String displayName, List<String> lore, Color color) {
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.displayname = displayName;
        this.lore = lore;
        this.color = color;
    }

    public RekruItem(Material material, int amount, short data, String displayName, List<String> lore, String playerName) {
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.displayname = displayName;
        this.lore = lore;
        this.displayname = playerName;
    }








    public ItemStack toItemStack() {
        ItemStack itemStack = null;
        if(material != null) {
            itemStack = new ItemStack(material);
        } else if(material != null && amount != 0) {
            itemStack = new ItemStack(material, amount);
        } else if(material != null && amount != 0 && data != 0) {
            itemStack = new ItemStack(material, amount, data);
        } else if(material != null && amount != 0 && data != 0 && displayname != null) {
            ItemStack item = new ItemStack(material, amount, data);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(displayname);
            item.setItemMeta(meta);
            itemStack = item;
        }  else if(material != null && amount != 0 && data != 0 && displayname != null && lore != null) {
            ItemStack item = new ItemStack(material, amount, data);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(displayname);
            meta.setLore(lore);
            item.setItemMeta(meta);
            itemStack = item;
        } else if(material != null && amount != 0 && data != 0 && displayname != null && lore != null && color != null) {
            ItemStack item = new ItemStack(material, amount, data);
            Firework firework = (Firework) item;
            FireworkMeta fmeta = firework.getFireworkMeta();
            fmeta.setDisplayName(displayname);
            fmeta.setLore(lore);
            fmeta.addEffect(FireworkEffect.builder().withColor(color).build());
            firework.setFireworkMeta(fmeta);
            itemStack = item;
        } else if(material != null && amount != 0 && data != 0 && displayname != null && lore != null && playerName != null) {
            ItemStack item = new ItemStack(material, amount, data);
            SkullMeta smeta = (SkullMeta) item.getItemMeta();
            smeta.setDisplayName(displayname);
            smeta.setLore(lore);
            smeta.setOwner(playerName);
            item.setItemMeta(smeta);
        }

        return itemStack;
    }


}
