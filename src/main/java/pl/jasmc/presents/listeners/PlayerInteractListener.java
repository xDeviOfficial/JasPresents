package pl.jasmc.presents.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.JPlayer;
import pl.jasmc.presents.objects.Present;
import pl.jasmc.presents.utils.RandomFireWorks;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getAction() != null) {
                JPlayer player = DataManager.getJPlayer(event.getPlayer().getName());
                for(Present present : player.getPresentsToFind()) {
                    if(event.getClickedBlock().getLocation().equals(present.getLocation())) {
                        if(player.presentAlreadyFound(present)) {
                            player.foundPresent(present);
                            event.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Jas" + ChatColor.RED + ChatColor.BOLD + "MC " + ChatColor.GREEN + "» " + ChatColor.GREEN + "Juz znalazles ten prezent! Szukaj dalej...");
                        } else {
                            event.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Jas" + ChatColor.RED + ChatColor.BOLD + "MC " + ChatColor.GREEN + "» " + ChatColor.GREEN + "Gratulacje! Znalazłeś/aś prezent, [" + DataManager.getJPlayer(event.getPlayer().getName()).presentsFound.size() + "/30]");
                            present.setTextureID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1ZDEzY2ExNGJjYmJkMWNkZTIxYWEwNjYwMDEwMWU0NTZkMzE4YWFkZjE3OGIyNzkzNjc4YjQ5NGY2ZGNlOCJ9fX0=");
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                        }
                    }
                }
            }
        }

    }
}
