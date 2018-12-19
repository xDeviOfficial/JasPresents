package pl.jasmc.presents.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.jasmc.presents.Presents;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.JPlayer;
import pl.jasmc.presents.objects.Present;
import pl.jasmc.presents.utils.ItemBuilder;
import pl.jasmc.presents.utils.Utils;

import java.util.Arrays;

public class PresentInventory {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Presents.getInstance().getConfig().getString("Messages.inventory_title"));
        int i = 9;
        JPlayer jPlayer = DataManager.getJPlayer(player.getName());
        ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 10).setName(" ").toItemStack();
        for(int glassPos = 0; glassPos < 9; glassPos++) {
            inv.setItem(glassPos, glass);
        }
        //LWEA STRONA
        inv.setItem(9, glass);
        inv.setItem(18, glass);
        inv.setItem(27, glass);
        inv.setItem(36, glass);

        //PRAWA STRONA
        inv.setItem(17, glass);
        inv.setItem(26, glass);
        inv.setItem(35, glass);
        inv.setItem(45, glass);
        for(int glassPos = 44; glassPos < 54; glassPos++) {
            inv.setItem(glassPos, glass);
        }



        for(Present p : jPlayer.getPresentsFound()) {
            i++;
            if(i == 17 || i == 26 || i == 34) i = i+2;
            inv.setItem(i, Utils.createSkullToInventory(p.getTextureID(), Utils.color(p.getName()), Arrays.asList(Utils.color(" "), Utils.color("&ePrezent numer: &a" + p.getId()), Utils.color("&eNazwa: &a" + p.getName()), "    ", Utils.color("&eStatus: &cZNALEZIONO" ))));
        }
        for(Present present : jPlayer.getPresentsToFind()) {
            i++;
            if(i == 17 || i == 26 || i == 34) i = i+2;
            inv.setItem(i, Utils.createSkullToInventory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19", "?", Arrays.asList("?")));
        }

        player.openInventory(inv);
    }


}
