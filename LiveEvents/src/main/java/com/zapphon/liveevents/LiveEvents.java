package com.zapphon.liveevents;

import com.zapphon.liveevents.commands.LiveCommand;
import com.zapphon.liveevents.core.engine.LiveEngine;
import com.zapphon.liveevents.core.presets.PresetLoader;
import com.zapphon.liveevents.core.presets.PresetRegistry;
import com.zapphon.liveevents.core.scheduler.EventScheduler;
import com.zapphon.liveevents.listeners.LiveMobDamageListener;
import com.zapphon.liveevents.listeners.PlayerDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LiveEvents extends JavaPlugin {

    private LiveEngine engine;
    private EventScheduler scheduler;
    private PresetRegistry presetRegistry;
    private PresetLoader presetLoader;

    @Override
    public void onEnable() {

        getLogger().info("=================================");
        getLogger().info(" LiveEvents iniciado com sucesso!");
        getLogger().info("=================================");

        engine = new LiveEngine(this);

        scheduler = new EventScheduler(engine);
        scheduler.start(this);

        presetRegistry = new PresetRegistry();
        presetLoader = new PresetLoader(this, presetRegistry);
        presetLoader.load();

        if (getCommand("live") == null) {

            getLogger().severe(
                    "O comando /live não foi encontrado no plugin.yml."
            );

            getServer()
                    .getPluginManager()
                    .disablePlugin(this);

            return;
        }

        getCommand("live").setExecutor(
                new LiveCommand(
                        this,
                        engine,
                        presetRegistry,
                        presetLoader
                )
        );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new LiveMobDamageListener(this),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new PlayerDeathListener(this),
                        this
                );
    }

    @Override
    public void onDisable() {

        if (scheduler != null) {
            scheduler.cancel();
        }

        getLogger().info("LiveEvents desligado.");
    }

    public LiveEngine getEngine() {
        return engine;
    }

    public PresetRegistry getPresetRegistry() {
        return presetRegistry;
    }

    public PresetLoader getPresetLoader() {
        return presetLoader;
    }
}