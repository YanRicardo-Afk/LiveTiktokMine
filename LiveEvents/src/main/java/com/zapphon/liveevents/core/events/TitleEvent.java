package com.zapphon.liveevents.core.events;

import com.zapphon.liveevents.models.GiftType;
import org.bukkit.entity.Player;

public class TitleEvent extends PlayerEvent {

    private final String title;
    private final String subtitle;

    public TitleEvent(
            String viewer,
            GiftType gift,
            Player target,
            String title,
            String subtitle,
            int delayTicks
    ) {
        super(viewer, gift, target, delayTicks);
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}