package com.zapphon.liveevents.core.presets;

import com.zapphon.liveevents.LiveEvents;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
        copiarPresetsPadrao();

        registry.clear();

        File[] files = presetsFolder.listFiles(
                (folder, name) ->
                        name.toLowerCase().endsWith(".yml")
        );

        if (files == null) {
            plugin.getLogger().warning(
                    "Nenhum arquivo de preset foi encontrado."
            );
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

    private void copiarPresetsPadrao() {

        InputStream indexStream =
                plugin.getResource("presets/index.txt");

        if (indexStream == null) {
            plugin.getLogger().warning(
                    "O arquivo presets/index.txt não foi encontrado."
            );
            return;
        }

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                indexStream,
                                StandardCharsets.UTF_8
                        )
                )
        ) {

            String fileName;

            while ((fileName = reader.readLine()) != null) {

                fileName = fileName.trim();

                if (
                        fileName.isBlank() ||
                        fileName.startsWith("#")
                ) {
                    continue;
                }

                copiarPresetSeNaoExistir(fileName);
            }

        } catch (IOException exception) {

            plugin.getLogger().severe(
                    "Erro ao ler presets/index.txt: "
                            + exception.getMessage()
            );
        }
    }

    private void copiarPresetSeNaoExistir(
            String fileName
    ) {

        File destination = new File(
                new File(plugin.getDataFolder(), "presets"),
                fileName
        );

        if (destination.exists()) {
            return;
        }

        String resourcePath =
                "presets/" + fileName;

        try {

            plugin.saveResource(
                    resourcePath,
                    false
            );

            plugin.getLogger().info(
                    "Preset padrão criado: " + fileName
            );

        } catch (IllegalArgumentException exception) {

            plugin.getLogger().warning(
                    "Preset listado, mas não encontrado: "
                            + resourcePath
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

            Object rawType = options.remove("type");

            if (rawType == null) {
                plugin.getLogger().warning(
                        "Evento sem tipo no preset: " + id
                );
                continue;
            }

            String type =
                    String.valueOf(rawType).trim();

            if (type.isBlank()) {
                continue;
            }

            events.add(
                    new PresetEvent(type, options)
            );
        }

        if (events.isEmpty()) {
            plugin.getLogger().warning(
                    "Preset sem eventos válidos: " + id
            );
            return;
        }

        registry.register(
                new Preset(id, events)
        );
    }
}