package com.zapphon.liveevents.core.handlers;

import com.zapphon.liveevents.core.events.TitleEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

import java.time.Duration;

public class TitleHandler implements EventHandler<TitleEvent> {

    @Override
    public void handle(TitleEvent event) {

        Title title = Title.title(
                Component.text(event.getTitle()),
                Component.text(event.getSubtitle()),
                Title.Times.times(
                        Duration.ofMillis(300),
                        Duration.ofSeconds(2),
                        Duration.ofMillis(500)
                )
        );

        event.getTarget().showTitle(title);
    }
}