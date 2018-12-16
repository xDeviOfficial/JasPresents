package pl.jasmc.presents.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.Present;
import pl.jasmc.presents.utils.RekruItem;
import pl.jasmc.presents.utils.Utils;

import java.util.Arrays;

public class PresentInventory {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "Prezenty JasMC");
        int i = 9;
        for(Present p : DataManager.loadedPresenents) {
            i++;
            if(i == 17 || i == 27) i = i+2;
            if(DataManager.getJPlayer(player.getName()).presentAlreadyFound(p)) {
                inv.setItem(i, Utils.createSkullToInventory(p.getTextureID(), Utils.color(p.getName()), Arrays.asList(Utils.color(" "), Utils.color("&ePrezent numer: &a" + p.getId()), Utils.color("&eNazwa: &a" + p.getName()), "    ", Utils.color("&eStatus: &cZNALEZIONO" ))));
            } else {
                inv.setItem(i, Utils.createSkullToInventory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19", "?", Arrays.asList("?")));
            }
        }
        player.openInventory(inv);
    }


}
