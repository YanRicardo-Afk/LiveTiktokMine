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

        super(viewer, gift);

        this.target = target;

    }

    public Player getTarget() {
        return target;
    }

}