package pl.jasmc.presents.listeners;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.jasmc.presents.Presents;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.JPlayer;
import pl.jasmc.presents.objects.Present;
import pl.jasmc.presents.utils.RandomFireWorks;
import pl.jasmc.presents.utils.Utils;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getAction() != null) {
                JPlayer player = DataManager.getJPlayer(event.getPlayer().getName());
                for(Present present : player.getPresentsFound()) {
                    if(event.getClickedBlock().getLocation().equals(present.getLocation())) {
                        event.getPlayer().sendMessage(Utils.color(Presents.TAG + Presents.getInstance().getConfig().getString("Messages.already_found_present")));
                        return;
                    }
                }
                for(Present presentToFind : player.getPresentsToFind()) {
                    if(event.getClickedBlock().getLocation().equals(presentToFind.getLocation())) {
                        player.foundPresent(presentToFind);
                        event.getPlayer().sendMessage(Utils.color(Presents.TAG + Presents.getInstance().getConfig().getString("Messages.found_present").replace("{presents_found}", String.valueOf(player.getPresentsFound().size())).replace("{presents_to_find}", String.valueOf(player.getPresentsFound().size() + player.getPresentsToFind().size()))));
                        RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                        RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                        RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                        if(player.presentsToFind.isEmpty()) {
                            event.getPlayer().sendMessage(Utils.color(Presents.TAG + Presents.getInstance().getConfig().getString("Messages.found_all_presents")));
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            RandomFireWorks.getManager().launchRandomFirework(event.getClickedBlock().getLocation());
                            EconomyResponse r = Presents.getEcon().depositPlayer(event.getPlayer(), Presents.COINS_REWARD);
                            if(r.transactionSuccess()) {
                                event.getPlayer().sendMessage(Utils.color(Presents.TAG + Presents.getInstance().getConfig().getString("Messages.coins_receive_successfull").replace("{coins}", String.valueOf(Presents.COINS_REWARD))));
                            } else {
                                event.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Jas" + ChatColor.RED + ChatColor.BOLD + "MC " + ChatColor.GREEN + "» " + ChatColor.RED + "Wystapil blad podczas dawania monet, zrob screenshota i skontaktuj sie z administratorem.");
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
