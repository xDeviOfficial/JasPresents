package pl.jasmc.presents.packets;

import org.bukkit.Location;
import org.bukkit.Material;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import pl.jasmc.presents.packets.AbstractPacket;

public class WrapperPlayServerBlockChange extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.BLOCK_CHANGE;

    public WrapperPlayServerBlockChange() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerBlockChange(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieve block X Coordinate.
     * @return The current X
     */
    public int getX() {
        return handle.getIntegers().read(0);
    }

    /**
     * Set block X Coordinate.
     * @param value - new value.
     */
    public void setX(int value) {
        handle.getIntegers().write(0, value);
    }

    /**
     * Retrieve block Y Coordinate.
     * @return The current Y
     */
    public int getY() {
        return handle.getIntegers().read(1);
    }

    /**
     * Set block Y Coordinate.
     * @param value - new value.
     */
    public void setY(int value) {
        handle.getIntegers().write(1, value);
    }

    /**
     * Retrieve block Z Coordinate.
     * @return The current Z
     */
    public int getZ() {
        return handle.getIntegers().read(2);
    }

    /**
     * Set block Z Coordinate.
     * @param value - new value.
     */
    public void setZ(int value) {
        handle.getIntegers().write(2, value);
    }

    /**
     * Retrieve the location of the block that is changing.
     * @param event - the parent event.
     * @return The location.
     */
    public Location getLocation(PacketEvent event) {
        return new Location(event.getPlayer().getWorld(), getX(), getY(), getZ());
    }

    /**
     * Set the location of the block that is changing.
     * @param loc - the new location.
     */
    public void setLocation(Location loc) {
        setX(loc.getBlockX());
        setY((byte) loc.getBlockY());
        setZ(loc.getBlockZ());
    }

    /**
     * Retrieve the new type of the block.
     * @return The current Block ID
     */
    public Material getBlockType() {
        return handle.getBlocks().read(0);
    }

    /**
     * Set the new type of the block.
     * @param value - new value.
     */
    public void setBlockType(Material value) {
        handle.getBlocks().write(0, value);
    }

    /**
     * Retrieve the new Metadata for the block.
     * @return The current Block Metadata
     */
    public byte getBlockMetadata() {
        return handle.getIntegers().read(3).byteValue();
    }

    /**
     * Set the new Metadata for the block.
     * @param value - new value.
     */
    public void setBlockMetadata(byte value) {
        handle.getIntegers().write(3, (int) value);
    }
}