package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;

public abstract class BaseEvent {

    private final String viewer;
    private final GiftType gift;
    private final int delayTicks;

    protected BaseEvent(
            String viewer,
            GiftType gift
    ) {
        this(viewer, gift, 0);
    }

    protected BaseEvent(
            String viewer,
            GiftType gift,
            int delayTicks
    ) {
        this.viewer = viewer;
        this.gift = gift;
        this.delayTicks = Math.max(0, delayTicks);
    }

    public String getViewer() {
        return viewer;
    }

    public GiftType getGift() {
        return gift;
    }

    public int getDelayTicks() {
        return delayTicks;
    }
}