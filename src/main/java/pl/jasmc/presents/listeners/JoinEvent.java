package pl.jasmc.presents.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.jasmc.presents.database.DatabaseConfiguration;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.JPlayer;


public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        DatabaseConfiguration.loadPlayer(event.getPlayer());
    }

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event) {

    }





}
