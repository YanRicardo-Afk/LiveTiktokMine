package com.zapphon.liveevents.services;

import com.zapphon.liveevents.models.EventType;
import com.zapphon.liveevents.models.GiftType;
import com.zapphon.liveevents.models.LiveEvent;
import com.zapphon.liveevents.models.TargetType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class EventFactory {

    public LiveEvent createMobEvent(
            Player target,
            String viewer,
            EntityType entity,
            int amount
    ) {

        return new LiveEvent(
                EventType.SPAWN_MOB,
                TargetType.PLAYER,
                target,
                viewer,
                GiftType.CUSTOM,
                entity,
                amount
        );

    }

    public LiveEvent createTntEvent(
            Player target,
            String viewer,
            int amount
    ) {

        return new LiveEvent(
                EventType.TNT,
                TargetType.PLAYER,
                target,
                viewer,
                GiftType.CUSTOM,
                null,
                amount
        );

    }

    public LiveEvent createLightningEvent(
            Player target,
            String viewer,
            int amount
    ) {

        return new LiveEvent(
                EventType.LIGHTNING,
                TargetType.PLAYER,
                target,
                viewer,
                GiftType.CUSTOM,
                null,
                amount
        );

    }

}