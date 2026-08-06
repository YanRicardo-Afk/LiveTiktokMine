package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.entity.Player;

public abstract class PlayerEvent extends BaseEvent {

    private final Player target;

    protected PlayerEvent(
            String viewer,
            GiftType gift,
            Player target
    ) {
        this(viewer, gift, target, 0);
    }

    protected PlayerEvent(
            String viewer,
            GiftType gift,
            Player target,
            int delayTicks
    ) {
        super(viewer, gift, delayTicks);
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}