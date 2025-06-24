package net.cutecraft.waxedlogs.listener;

import net.cutecraft.waxedlogs.event.LogStripEvent;
import net.cutecraft.waxedlogs.event.PlayerWaxLogEvent;
import net.cutecraft.waxedlogs.model.WaxedBlockData;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class LogListener implements Listener {

    @EventHandler
    public void onPlayerWaxLogEvent(PlayerWaxLogEvent event) {
        final Block block = event.getBlock();
        final WaxedBlockData waxedBlockData = new WaxedBlockData(block);
        if (waxedBlockData.isWaxed()) {
            return;
        }

        final Player player = event.getPlayer();
        final EquipmentSlot hand = event.getHand();
        player.playSound(block.getLocation(), Sound.ITEM_HONEYCOMB_WAX_ON, 1f, 1f);
        switch (hand) {
            case HAND:
                player.swingMainHand();
                break;
            case OFF_HAND:
                player.swingOffHand();
                break;
        }

        if (player.getGameMode() != GameMode.CREATIVE) {
            final ItemStack itemStack = event.getItemStack();
            itemStack.setAmount(itemStack.getAmount() - 1);
        }

        block.getWorld().spawnParticle(Particle.WAX_ON, block.getLocation().clone().add(0.5, 0.5, 0.5),
                30, 0.5, 0.5, 0.5);
        waxedBlockData.setWaxed();
    }

    @EventHandler
    public void onLogStripEvent(LogStripEvent event) {
        final Block block = event.getBlock();
        final WaxedBlockData waxedBlockData = new WaxedBlockData(block);
        boolean isWaxed = waxedBlockData.isWaxed();
        event.setCancelled(isWaxed);
    }
}