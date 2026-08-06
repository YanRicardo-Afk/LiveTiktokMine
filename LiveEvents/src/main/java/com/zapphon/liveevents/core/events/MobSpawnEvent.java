package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class MobSpawnEvent extends PlayerEvent {

    private final EntityType entity;

    private final int amount;

    public MobSpawnEvent(
            String viewer,
            GiftType gift,
            Player target,
            EntityType entity,
            int amount
    ) {

        super(viewer, gift, target);

        this.entity = entity;
        this.amount = amount;

    }

    public EntityType getEntity() {
        return entity;
    }

    public int getAmount() {
        return amount;
    }

}