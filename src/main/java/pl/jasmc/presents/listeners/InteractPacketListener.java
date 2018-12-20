package pl.jasmc.presents.listeners;

import com.comphenix.protocol.PacketType;

import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import pl.jasmc.presents.objects.JPlayer;

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
            for(WrappedBlockData data : event.getPacket().getBlockData().getValues()) {
                if(data.getType().equals(Material.AIR)) {
                    event.setCancelled(true);
                }
            }


        }

    }
}
