package com.zapphon.liveevents.services;

import org.bukkit.entity.EntityType;

public class CommandParser {

    public EntityType parseEntity(String name){

        return switch(name.toLowerCase()){

            case "creeper" -> EntityType.CREEPER;

            case "zombie", "zumbi" -> EntityType.ZOMBIE;

            case "skeleton", "esqueleto" -> EntityType.SKELETON;

            case "spider", "aranha" -> EntityType.SPIDER;

            case "pig", "porco" -> EntityType.PIG;

            default -> null;

        };

    }

}