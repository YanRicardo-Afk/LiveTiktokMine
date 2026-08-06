package com.zapphon.liveevents.models;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LiveEvent {

    private final EventType eventType;
    private final TargetType targetType;
    private final Player targetPlayer;
    private final String viewerName;
    private final GiftType giftType;
    private final EntityType entityType;
    private final int amount;

    public LiveEvent(
            EventType eventType,
            TargetType targetType,
            Player targetPlayer,
            String viewerName,
            GiftType giftType,
            EntityType entityType,
            int amount
    ) {

        this.eventType = eventType;
        this.targetType = targetType;
        this.targetPlayer = targetPlayer;
        this.viewerName = viewerName;
        this.giftType = giftType;
        this.entityType = entityType;
        this.amount = amount;

    }

    public EventType getEventType() {
        return eventType;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public String getViewerName() {
        return viewerName;
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getAmount() {
        return amount;
    }

}