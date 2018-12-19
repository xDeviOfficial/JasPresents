package pl.jasmc.presents.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static void sendColoredInfo(String info) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', info));
    }


    /*

        createSkull(texture, displayName):
        Zwraca ItemStacka, główke pustą lub z teksturą która jest podana i nazwą



     */

    public static ItemStack createSkull(String texture, String displayName){

        final ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

        if(texture.isEmpty()) {
            return head;
        }
        final SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            final Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        skullMeta.setDisplayName(displayName);
        head.setItemMeta(skullMeta);

        return head;
    }

    public static ItemStack createSkullToInventory(String texture, String displayName, List<String> lore){

        final ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

        if(texture.isEmpty()) {
            return head;
        }
        final SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            final Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        skullMeta.setDisplayName(displayName.toUpperCase());
        skullMeta.setLore(lore);
        head.setItemMeta(skullMeta);


        return head;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static GameProfile getGameProfile(String url) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        return profile;
    }

    public static Location stringToLoc(String location) {
        String[] splitLoc = location.split("/");
        //world;x;y;z
        return new Location(Bukkit.getWorld(splitLoc[0]), Double.parseDouble(splitLoc[1]),Double.parseDouble(splitLoc[2]),Double.parseDouble(splitLoc[3]));
    }

    public static List<Location> stringLocationListToLoc(List<String> locations) {

        List<Location> convertedLocationList = new ArrayList<Location>();
        for(String location : locations) {
            convertedLocationList.add(stringToLoc(location));
            System.out.println("Adding : " + location.toString());
        }
        System.out.println("Returning: " + locations.toString());
        return convertedLocationList;
    }

    public static String locationToString(Location location) {
        return location.getWorld().getName() + "/" + location.getBlockX() + "/" + location.getBlockY() + "/" + location.getBlockZ();
    }






    public static GameProfile getItem(String b64stringtexture) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        propertyMap.put("textures", new Property("textures", b64stringtexture));
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta headMeta = head.getItemMeta();
        Class<?> headMetaClass = headMeta.getClass();
        try {
            getField(headMetaClass, "profile", GameProfile.class, 0).set(headMeta, profile);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return profile;
    }

    private static <T> Field getField(Class<?> target, String name, Class<T> fieldType, int index) {
        for (final Field field : target.getDeclaredFields()) {
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);
                return field;
            }
        }

        if (target.getSuperclass() != null)
            return getField(target.getSuperclass(), name, fieldType, index);
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }



}
