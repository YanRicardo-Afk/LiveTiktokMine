package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.entity.Player;

public class TntEvent extends PlayerEvent {

    private final int amount;

    public TntEvent(
            String viewer,
            GiftType gift,
            Player target,
            int amount
    ) {

        super(viewer, gift, target);

        this.amount = amount;

    }

    public int getAmount() {
        return amount;
    }

}