package pl.jasmc.presents.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.jasmc.presents.managers.DataManager;

public class QuitListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        DataManager.removePlayer(event.getPlayer());

    }
}
