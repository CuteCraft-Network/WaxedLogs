package net.cutecraft.waxedlogs.listener;

import net.cutecraft.waxedlogs.model.WaxedBlockData;
import net.cutecraft.waxedlogs.util.MaterialUtil;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class BlockDataListener implements Listener {

    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent event) {
        final Block block = event.getBlock();
        onBlockBreak(block);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        final Block block = event.getBlock();
        onBlockBreak(block);
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        for (Block block : event.blockList()) {
            onBlockBreak(block);
        }
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            onBlockBreak(block);
        }
    }

    @EventHandler
    public void onPistonExtendEvent(BlockPistonExtendEvent event) {
        final List<Block> blocks = event.getBlocks();
        onPiston(blocks, event);
    }

    @EventHandler
    public void onPistonRetractEvent(BlockPistonRetractEvent event) {
        final List<Block> blocks = event.getBlocks();
        onPiston(blocks, event);
    }

    private void onPiston(List<Block> blocks, BlockPistonEvent event) {
        final BlockFace direction = event.getDirection();
        for (Block block : blocks) {
            if (!MaterialUtil.isLog(block)) {
                continue;
            }

            final WaxedBlockData waxedBlockData = new WaxedBlockData(block);
            if(!waxedBlockData.removeWaxed()) {
                continue;
            }

            final Block targetBlock = block.getRelative(direction);
            new WaxedBlockData(targetBlock).setWaxed();
        }
    }

    private void onBlockBreak(Block block) {
        if (!MaterialUtil.isLog(block)) {
            return;
        }

        final WaxedBlockData waxedBlockData = new WaxedBlockData(block);
        waxedBlockData.removeWaxed();
    }
}
