package net.cutecraft.waxedlogs.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class LogStripEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Block block;

    @Setter
    private boolean cancelled;

    public LogStripEvent(Block block) {
        this.block = block;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
