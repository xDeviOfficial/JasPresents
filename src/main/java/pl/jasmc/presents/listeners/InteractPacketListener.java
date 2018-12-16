package pl.jasmc.presents.listeners;

import com.comphenix.protocol.PacketType;

import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Location;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.Present;
import pl.jasmc.presents.packets.WrapperPlayServerBlockChange;

public class InteractPacketListener extends PacketAdapter {


    private JavaPlugin plugin;

    public InteractPacketListener(JavaPlugin plugin) {
        super(plugin, ListenerPriority.HIGHEST,
                PacketType.Play.Server.BLOCK_CHANGE);
        this.plugin = plugin;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getPacketType() == PacketType.Play.Server.BLOCK_CHANGE) {
            WrapperPlayServerBlockChange wrapper = new WrapperPlayServerBlockChange(event.getPacket());
            Location blockLocation = wrapper.getLocation(event);

            if (hasBeenFaked(event.getPlayer(), blockLocation)) {
                //wrapper.setBlockType(Material.AIR);
                //wrapper.setBlockMetadata((byte) 0);

                System.out.println("PACKET INTERCEPTED");
                event.setCancelled(true);
            }
        }
    }

    /*
     * A helper method to determine if a block at a location has been faked
     */
    private boolean hasBeenFaked(Player player, Location location) {
        for(Present p : DataManager.loadedPresenents) {
            if(location.equals(p.getLocation())) {
                return true;
            }
        }
        return false; // Implementation needed
    }
}
