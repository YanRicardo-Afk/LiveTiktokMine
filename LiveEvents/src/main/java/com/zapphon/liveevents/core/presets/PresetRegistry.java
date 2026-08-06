package com.zapphon.liveevents.core.presets;

import java.util.HashMap;
import java.util.Map;

public class PresetRegistry {

    private final Map<String, Preset> presets =
            new HashMap<>();

    public void register(Preset preset) {
        presets.put(
                preset.getId().toLowerCase(),
                preset
        );
    }

    public Preset get(String id) {

        if (id == null) {
            return null;
        }

        return presets.get(id.toLowerCase());
    }

    public boolean contains(String id) {
        return get(id) != null;
    }

    public void clear() {
        presets.clear();
    }

    public int size() {
        return presets.size();
    }
}