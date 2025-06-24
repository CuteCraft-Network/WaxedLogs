package net.cutecraft.waxedlogs.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class PlayerWaxLogEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final ItemStack itemStack;
    private final EquipmentSlot hand;
    private final Block block;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
