package pl.jasmc.presents.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.jasmc.presents.Presents;

public class InventoryInteractListener implements Listener {

    @EventHandler
    public void onInvInteract(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getName().startsWith(Presents.getInstance().getConfig().getString("Messages.inventory_title"))) {
                event.setCancelled(true);
            }
        }

    }
}
