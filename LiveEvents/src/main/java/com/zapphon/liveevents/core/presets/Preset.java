package com.zapphon.liveevents.core.presets;

import java.util.List;

public class Preset {

    private final String id;
    private final List<PresetEvent> events;

    public Preset(String id, List<PresetEvent> events) {
        this.id = id;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public List<PresetEvent> getEvents() {
        return events;
    }
}