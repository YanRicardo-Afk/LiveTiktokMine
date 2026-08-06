package com.zapphon.liveevents.registry;

import com.zapphon.liveevents.models.EventType;

import java.util.HashMap;
import java.util.Map;

public class ActionRegistry {

    private final Map<String, EventType> registry = new HashMap<>();

    public ActionRegistry() {

        register("creeper", EventType.SPAWN_MOB);
        register("zombie", EventType.SPAWN_MOB);
        register("zumbi", EventType.SPAWN_MOB);
        register("skeleton", EventType.SPAWN_MOB);
        register("esqueleto", EventType.SPAWN_MOB);
        register("spider", EventType.SPAWN_MOB);
        register("aranha", EventType.SPAWN_MOB);
        register("pig", EventType.SPAWN_MOB);
        register("porco", EventType.SPAWN_MOB);

        register("tnt", EventType.TNT);

        register("lightning", EventType.LIGHTNING);
        register("raio", EventType.LIGHTNING);

    }

    public void register(String id, EventType type){

        registry.put(id.toLowerCase(), type);

    }

    public EventType get(String id){

        return registry.get(id.toLowerCase());

    }

}