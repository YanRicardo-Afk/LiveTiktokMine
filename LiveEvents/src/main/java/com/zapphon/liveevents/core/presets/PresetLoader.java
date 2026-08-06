package com.zapphon.liveevents.core.presets;

import com.zapphon.liveevents.LiveEvents;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PresetLoader {

    private final LiveEvents plugin;
    private final PresetRegistry registry;

    public PresetLoader(
            LiveEvents plugin,
            PresetRegistry registry
    ) {
        this.plugin = plugin;
        this.registry = registry;
    }

    public void load() {

        File presetsFolder =
                new File(plugin.getDataFolder(), "presets");

        criarPasta(presetsFolder);
        copiarPresetPadrao(presetsFolder);

        registry.clear();

        File[] files = presetsFolder.listFiles(
                (folder, name) ->
                        name.toLowerCase().endsWith(".yml")
        );

        if (files == null) {
            return;
        }

        for (File file : files) {
            carregarArquivo(file);
        }

        plugin.getLogger().info(
                registry.size() + " preset(s) carregado(s)."
        );
    }

    private void criarPasta(File folder) {

        if (!folder.exists() && !folder.mkdirs()) {
            plugin.getLogger().severe(
                    "Não foi possível criar a pasta de presets."
            );
        }
    }

    private void copiarPresetPadrao(File folder) {

        File roseFile = new File(folder, "rose.yml");

        if (roseFile.exists()) {
            return;
        }

        try {
            plugin.saveResource("presets/rose.yml", false);
        } catch (IllegalArgumentException exception) {
            plugin.getLogger().severe(
                    "O arquivo presets/rose.yml não foi encontrado."
            );
        }
    }

    private void carregarArquivo(File file) {

        YamlConfiguration yaml =
                YamlConfiguration.loadConfiguration(file);

        String id = yaml.getString("id");

        if (id == null || id.isBlank()) {
            plugin.getLogger().warning(
                    "Preset sem ID: " + file.getName()
            );
            return;
        }

        List<Map<?, ?>> rawEvents =
                yaml.getMapList("events");

        List<PresetEvent> events =
                new ArrayList<>();

        for (Map<?, ?> rawEvent : rawEvents) {

            Map<String, Object> options =
                    new LinkedHashMap<>();

            for (
                    Map.Entry<?, ?> entry
                    : rawEvent.entrySet()
            ) {
                options.put(
                        String.valueOf(entry.getKey()),
                        entry.getValue()
                );
            }

            String type =
                    String.valueOf(options.remove("type"));

            if (
                    type == null ||
                    type.isBlank() ||
                    type.equals("null")
            ) {
                continue;
            }

            events.add(
                    new PresetEvent(type, options)
            );
        }

        registry.register(
                new Preset(id, events)
        );
    }
}