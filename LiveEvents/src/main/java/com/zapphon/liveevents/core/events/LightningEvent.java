package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.entity.Player;

public class LightningEvent extends PlayerEvent {

    private final int amount;

    public LightningEvent(
            String viewer,
            GiftType gift,
            Player target,
            int amount
    ) {
        this(viewer, gift, target, amount, 0);
    }

    public LightningEvent(
            String viewer,
            GiftType gift,
            Player target,
            int amount,
            int delayTicks
    ) {
        super(viewer, gift, target, delayTicks);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}