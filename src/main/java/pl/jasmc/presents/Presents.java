package pl.jasmc.presents;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.zaxxer.hikari.HikariDataSource;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import pl.jasmc.presents.commands.PresentCommand;
import pl.jasmc.presents.database.DatabaseConfiguration;
import pl.jasmc.presents.inventory.PresentInventory;
import pl.jasmc.presents.listeners.*;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.JPlayer;
import pl.jasmc.presents.objects.Present;
import pl.jasmc.presents.packets.WrapperPlayServerTileEntityData;
import pl.jasmc.presents.utils.RandomFireWorks;
import pl.jasmc.presents.utils.Utils;
import org.bukkit.Location;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class Presents extends JavaPlugin {

    private static Presents inst;
    private HikariDataSource hikari;

    private ProtocolManager protocolManager;

    public static Presents getInstance() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        saveDefaultConfig();
        if (this.getConfig().getBoolean("General.Database.DatabaseUse")) {
            hikari = new HikariDataSource();
            hikari.setDataSourceClassName(this.getConfig().getString("General.Database.DataSourceClass"));
            hikari.addDataSourceProperty("serverName", this.getConfig().getString("General.Database.ServerIP"));
            hikari.addDataSourceProperty("port", this.getConfig().getString("General.Database.ServerPort"));
            hikari.addDataSourceProperty("databaseName", this.getConfig().getString("General.Database.DatabaseName"));
            hikari.addDataSourceProperty("user", this.getConfig().getString("General.Database.DatabaseUser"));
            hikari.addDataSourceProperty("password", this.getConfig().getString("General.Database.DatabasePassword"));

            try {
                DatabaseConfiguration.checkTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        getCommand("prezent").setExecutor(new PresentCommand());
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new QuitListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new InventoryInteractListener(), this);
        //this.registerPacketIntercepter();



        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                Utils.sendColoredInfo("&cJas Presents have been enabled");
                if (getConfig().getBoolean("General.Database.DatabaseUse")) {
                   Utils.sendColoredInfo("&cDatabase status = &aON");
                    try {
                        DatabaseConfiguration.checkTable();
                        DatabaseConfiguration.loadPresents();
                        startPacketTask();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Utils.sendColoredInfo("&cDatabase status = &4OFF");
                    Utils.sendColoredInfo("&6A lot of functions will doesn't work!");
                }
            }
        }, 80);

        RandomFireWorks.getManager().addColors();
        RandomFireWorks.getManager().addTypes();



    }

    @Override
    public void onDisable() {
        if (hikari != null) {
            hikari.close();
        }
    }

    public void startPacketTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                if(DataManager.players.size() >= 1) {
                    for(JPlayer j : DataManager.players) {
                        for(Present p : j.presentsToFind) {
                            if(p.getLocation() != null) {
                                if(j.getPresentsFound().contains(p)) {
                                    placeSkull(Bukkit.getPlayer(j.name), p.getLocation(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1ZDEzY2ExNGJjYmJkMWNkZTIxYWEwNjYwMDEwMWU0NTZkMzE4YWFkZjE3OGIyNzkzNjc4YjQ5NGY2ZGNlOCJ9fX0=");
                                } else {
                                    placeSkull(Bukkit.getPlayer(j.name), p.getLocation(), p.getTextureID());
                                }

                            }
                        }
                    }
                }

            }
        }, 0, 10);

    }


    /*

      @p - Gracz do ktorego ma byc wyslana glowa
      @loc - Lokacja prezenty w ktorym ma byc wyswietlony
      @texture - Textura w Base64 (Mozna znalesc tutaj: https://minecraft-heads.com/custom/heads/var/tag/Present)

      Metoda na wysyłanie pakietu głowy z customowym skinem w danej lokacji do danego gracza.

     */

    public static void placeSkull(Player p, Location loc, String texture) {
        BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());

        GameProfile gameProfile = Utils.getGameProfile(texture);

        NBTTagCompound tag = CraftItemStack.asNMSCopy(Utils.createSkull(texture, "test")).getTag();


        TileEntitySkull skull = new TileEntitySkull();
        skull.setRotation(3);
        skull.setSkullType(3);
        skull.setPosition(pos);
        skull.setGameProfile(gameProfile);
        skull.save(tag);


        ((CraftPlayer)p).sendBlockChange(loc, Material.SKULL, (byte) 3);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(skull.getUpdatePacket());
    }

    private void registerPacketIntercepter() {
        protocolManager.addPacketListener(new InteractPacketListener(this));
    }

    public HikariDataSource getHikari() {
        return hikari;
    }
}
