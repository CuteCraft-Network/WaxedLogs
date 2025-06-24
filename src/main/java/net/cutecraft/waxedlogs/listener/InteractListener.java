package net.cutecraft.waxedlogs.listener;

import lombok.AllArgsConstructor;
import net.cutecraft.waxedlogs.event.LogStripEvent;
import net.cutecraft.waxedlogs.event.PlayerWaxLogEvent;
import net.cutecraft.waxedlogs.service.ConfigService;
import net.cutecraft.waxedlogs.util.MaterialUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class InteractListener implements Listener {

    private final ConfigService configService;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        final Block block = event.getClickedBlock();
        if (block == null || !MaterialUtil.isLog(block)) {
            return;
        }

        final ItemStack itemStack = event.getItem();
        if (itemStack == null) {
            return;
        }

        final Material itemStackType = itemStack.getType();
        if (MaterialUtil.isAxe(itemStackType)) {
            final LogStripEvent logStripEvent = new LogStripEvent(block);
            Bukkit.getPluginManager().callEvent(logStripEvent);
            if (logStripEvent.isCancelled()) {
                event.setUseInteractedBlock(Event.Result.DENY);
            }

            return;
        }

        if (itemStackType != Material.HONEYCOMB) {
            return;
        }

        final Player player = event.getPlayer();
        if (configService.isPermissionOnly() && !player.hasPermission(configService.getPermission())) {
            return;
        }

        Bukkit.getPluginManager().callEvent(new PlayerWaxLogEvent(event.getPlayer(), itemStack, event.getHand(), block));
    }
}
