package com.zapphon.liveevents;

import com.zapphon.liveevents.commands.LiveCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class LiveEvents extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("=================================");
        getLogger().info(" LiveEvents iniciado com sucesso!");
        getLogger().info("=================================");

        getCommand("live").setExecutor(new LiveCommand());

    }

    @Override
    public void onDisable() {

        getLogger().info("LiveEvents desligado.");

    }

}