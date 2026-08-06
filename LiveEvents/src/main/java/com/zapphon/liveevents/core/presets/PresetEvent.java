package com.zapphon.liveevents.core.presets;

import java.util.Map;

public class PresetEvent {

    private final String type;
    private final Map<String, Object> options;

    public PresetEvent(
            String type,
            Map<String, Object> options
    ) {
        this.type = type;
        this.options = options;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public String getString(
            String key,
            String defaultValue
    ) {
        Object value = options.get(key);

        return value == null
                ? defaultValue
                : String.valueOf(value);
    }

    public int getInt(
            String key,
            int defaultValue
    ) {
        Object value = options.get(key);

        if (value instanceof Number number) {
            return number.intValue();
        }

        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (Exception exception) {
            return defaultValue;
        }
    }
}