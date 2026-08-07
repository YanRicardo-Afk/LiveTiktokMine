package com.zapphon.liveevents.connectors.simulator;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.engine.LiveEngine;
import com.zapphon.liveevents.core.presets.Preset;
import com.zapphon.liveevents.core.presets.PresetExecutor;
import com.zapphon.liveevents.core.presets.PresetRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class SimulatorApiServer {

    private final LiveEvents plugin;
    private final PresetRegistry presetRegistry;
    private final PresetExecutor presetExecutor;

    private HttpServer server;

    public SimulatorApiServer(
            LiveEvents plugin,
            LiveEngine engine,
            PresetRegistry presetRegistry
    ) {
        this.plugin = plugin;
        this.presetRegistry = presetRegistry;
        this.presetExecutor = new PresetExecutor(engine);
    }

    public void start() {

        if (!plugin.getConfig().getBoolean("api.enabled", true)) {
            plugin.getLogger().info("API do simulador está desativada.");
            return;
        }

        String host = plugin.getConfig().getString("api.host", "0.0.0.0");
        int port = plugin.getConfig().getInt("api.port", 8765);

        try {
            server = HttpServer.create(
                    new InetSocketAddress(host, port),
                    0
            );

            server.createContext("/health", this::handleHealth);
            server.createContext("/preset", this::handlePreset);

            server.setExecutor(
                    Executors.newFixedThreadPool(2)
            );

            server.start();

            plugin.getLogger().info(
                    "API do simulador iniciada em http://"
                            + host + ":" + port
            );

        } catch (IOException exception) {
            plugin.getLogger().severe(
                    "Não foi possível iniciar a API: "
                            + exception.getMessage()
            );
        }
    }

    public void stop() {

        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    private void handleHealth(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            responder(exchange, 405, "Método não permitido.");
            return;
        }

        responder(exchange, 200, "LiveEvents API online");
    }

    private void handlePreset(HttpExchange exchange) throws IOException {

        adicionarCors(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            responder(exchange, 405, "Método não permitido.");
            return;
        }

        Map<String, String> form = lerFormulario(exchange);

        String tokenRecebido = form.getOrDefault("token", "");
        String tokenConfigurado =
                plugin.getConfig().getString("api.token", "troque-este-token");

        if (!tokenConfigurado.equals(tokenRecebido)) {
            responder(exchange, 401, "Token inválido.");
            return;
        }

        String presetId = form.getOrDefault("preset", "").trim();
        String viewer = form.getOrDefault("viewer", "Espectador").trim();
        String targetName = form.getOrDefault("target", "random").trim();

        if (presetId.isBlank()) {
            responder(exchange, 400, "Informe o preset.");
            return;
        }

        Preset preset = presetRegistry.get(presetId);

        if (preset == null) {
            responder(exchange, 404, "Preset não encontrado: " + presetId);
            return;
        }

        Bukkit.getScheduler().runTask(
                plugin,
                () -> executarPreset(
                        preset,
                        viewer,
                        targetName
                )
        );

        responder(exchange, 202, "Evento recebido.");
    }

    private void executarPreset(
            Preset preset,
            String viewer,
            String targetName
    ) {

        List<Player> targets = buscarAlvos(targetName);

        if (targets.isEmpty()) {
            plugin.getLogger().warning(
                    "Preset ignorado: nenhum alvo encontrado para "
                            + targetName
            );
            return;
        }

        for (Player target : targets) {
            presetExecutor.execute(
                    preset,
                    viewer,
                    target
            );
        }

        plugin.getLogger().info(
                "Preset " + preset.getId()
                        + " enviado por " + viewer
                        + " para " + targetName
        );
    }

    private List<Player> buscarAlvos(String targetName) {

        List<Player> targets = new ArrayList<>();

        if (targetName.equalsIgnoreCase("all")) {
            targets.addAll(Bukkit.getOnlinePlayers());
            return targets;
        }

        if (targetName.equalsIgnoreCase("random")) {

            List<Player> onlinePlayers =
                    new ArrayList<>(Bukkit.getOnlinePlayers());

            if (onlinePlayers.isEmpty()) {
                return targets;
            }

            int index = ThreadLocalRandom
                    .current()
                    .nextInt(onlinePlayers.size());

            targets.add(onlinePlayers.get(index));
            return targets;
        }

        Player player = Bukkit.getPlayerExact(targetName);

        if (player != null) {
            targets.add(player);
        }

        return targets;
    }

    private Map<String, String> lerFormulario(
            HttpExchange exchange
    ) throws IOException {

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> values = new HashMap<>();

        if (body.isBlank()) {
            return values;
        }

        for (String pair : body.split("&")) {

            String[] parts = pair.split("=", 2);

            String key = URLDecoder.decode(
                    parts[0],
                    StandardCharsets.UTF_8
            );

            String value = parts.length > 1
                    ? URLDecoder.decode(
                            parts[1],
                            StandardCharsets.UTF_8
                    )
                    : "";

            values.put(key, value);
        }

        return values;
    }

    private void responder(
            HttpExchange exchange,
            int status,
            String message
    ) throws IOException {

        adicionarCors(exchange);

        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type",
                "text/plain; charset=UTF-8"
        );

        exchange.sendResponseHeaders(
                status,
                bytes.length
        );

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

    private void adicionarCors(HttpExchange exchange) {
        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, POST, OPTIONS"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
    }
}