package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.entity.Player;

public class WolfFollowEvent extends PlayerEvent {

    public WolfFollowEvent(
            String viewer,
            GiftType gift,
            Player target,
            int delayTicks
    ) {
        super(viewer, gift, target, delayTicks);
    }
}