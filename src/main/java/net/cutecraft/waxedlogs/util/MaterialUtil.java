package net.cutecraft.waxedlogs.util;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Set;

public class MaterialUtil {
    private static final Set<String> LOGS = Set.of(
            "OAK_LOG",
            "OAK_WOOD",
            "BIRCH_LOG",
            "BIRCH_WOOD",
            "SPRUCE_LOG",
            "SPRUCE_WOOD",
            "JUNGLE_LOG",
            "JUNGLE_WOOD",
            "ACACIA_LOG",
            "ACACIA_WOOD",
            "DARK_OAK_LOG",
            "DARK_OAK_WOOD",
            "CRIMSON_STEM",
            "CRIMSON_HYPHAE",
            "WARPED_STEM",
            "WARPED_HYPHAE",
            // 1.17+
            "MANGROVE_LOG",
            "MANGROVE_WOOD",
            "CHERRY_LOG",
            "CHERRY_WOOD",
            "PALE_OAK_LOG",
            "PALE_OAK_WOOD"
    );

    public static boolean isAxe(Material material) {
        return material.name().endsWith("_AXE");
    }

    public static boolean isLog(Block block) {
        return LOGS.contains(block.getType().name());
    }
}