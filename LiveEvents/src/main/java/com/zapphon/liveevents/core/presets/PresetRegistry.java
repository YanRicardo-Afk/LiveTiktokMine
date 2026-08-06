package com.zapphon.liveevents.core.presets;

import java.util.HashMap;
import java.util.Map;

public class PresetRegistry {

    private final Map<String, Preset> presets =
            new HashMap<>();

    public void register(Preset preset){

        presets.put(
                preset.id().toLowerCase(),
                preset
        );

    }

    public Preset get(String id){

        return presets.get(
                id.toLowerCase()
        );

    }

}