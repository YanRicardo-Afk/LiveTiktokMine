package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;

public abstract class BaseEvent {

    private final String viewer;

    private final GiftType gift;

    protected BaseEvent(
            String viewer,
            GiftType gift
    ) {

        this.viewer = viewer;
        this.gift = gift;

    }

    public String getViewer() {
        return viewer;
    }

    public GiftType getGift() {
        return gift;
    }

}