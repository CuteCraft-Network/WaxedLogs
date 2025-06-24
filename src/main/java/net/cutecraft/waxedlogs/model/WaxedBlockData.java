package net.cutecraft.waxedlogs.model;

import lombok.AllArgsConstructor;
import net.cutecraft.waxedlogs.WaxedLogsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class WaxedBlockData {

    private static final NamespacedKey WAXED_KEY = new NamespacedKey(WaxedLogsPlugin.getInstance(), "waxed");

    private final Block block;

    public void setWaxed() {
        HashSet<Location> locations = getWaxedLocations();
        locations.add(block.getLocation());
        save(locations);
    }

    public boolean removeWaxed() {
        final HashSet<Location> locations = getWaxedLocations();
        if (!locations.remove(block.getLocation())) {
            return false;
        }

        if (locations.isEmpty()) {
            Chunk chunk = block.getChunk();
            chunk.getPersistentDataContainer().remove(WAXED_KEY);
            return true;
        }

        save(locations);
        return true;
    }

    public boolean isWaxed() {
        return getWaxedLocations().contains(block.getLocation());
    }

    private HashSet<Location> getWaxedLocations() {
        final Chunk chunk = block.getChunk();
        final byte[] bytes = chunk.getPersistentDataContainer().get(WAXED_KEY, PersistentDataType.BYTE_ARRAY);
        return bytes != null ? deserializeLocationList(bytes) : new HashSet<>();
    }

    private void save(Set<Location> locations) {
        final Chunk chunk = block.getChunk();
        final byte[] serializedLocations = serializeLocationList(locations);
        chunk.getPersistentDataContainer().set(WAXED_KEY, PersistentDataType.BYTE_ARRAY, serializedLocations);
    }

    private byte[] serializeLocationList(Set<Location> locations) {
        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream output = new ObjectOutputStream(byteOutputStream)) {
            output.writeInt(locations.size());
            for (Location location : locations) {
                output.writeUTF(location.getWorld().getName());
                output.writeInt(location.getBlockX());
                output.writeInt(location.getBlockY());
                output.writeInt(location.getBlockZ());
            }
            output.flush();
            return byteOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize location list", e);
        }
    }

    private HashSet<Location> deserializeLocationList(byte[] data) {
        final HashSet<Location> locations = new HashSet<>();
        try (ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
             ObjectInputStream input = new ObjectInputStream(byteInputStream)) {
            final int count = input.readInt();
            for (int i = 0; i < count; i++) {
                String world = input.readUTF();
                final int x = input.readInt();
                final int y = input.readInt();
                final int z = input.readInt();
                locations.add(new Location(Bukkit.getWorld(world), x, y, z));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize location list", e);
        }
        return locations;
    }
}
