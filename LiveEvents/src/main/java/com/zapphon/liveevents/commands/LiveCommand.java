package com.zapphon.liveevents.commands;

import com.zapphon.liveevents.LiveEvents;
import com.zapphon.liveevents.core.engine.LiveEngine;
import com.zapphon.liveevents.core.events.LightningEvent;
import com.zapphon.liveevents.core.events.MobSpawnEvent;
import com.zapphon.liveevents.core.events.TntEvent;
import com.zapphon.liveevents.models.GiftType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LiveCommand implements CommandExecutor {

    private final LiveEvents plugin;
    private final LiveEngine engine;

    public LiveCommand(LiveEvents plugin, LiveEngine engine) {
        this.plugin = plugin;
        this.engine = engine;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player senderPlayer)) {
            sender.sendMessage("Este comando precisa ser usado por um jogador.");
            return true;
        }

        if (args.length == 0) {
            senderPlayer.sendMessage("§aLiveEvents funcionando com a nova engine!");
            enviarAjuda(senderPlayer);
            return true;
        }

        if (!args[0].equalsIgnoreCase("trigger")) {
            enviarAjuda(senderPlayer);
            return true;
        }

        if (args.length < 2) {
            senderPlayer.sendMessage("§cInforme o evento.");
            enviarAjuda(senderPlayer);
            return true;
        }

        String eventName = args[1].toLowerCase();
        int amount = lerQuantidade(args, senderPlayer);

        if (amount == -1) {
            return true;
        }

        String viewerName = args.length >= 4
                ? args[3]
                : "Espectador";

        String targetName = args.length >= 5
                ? args[4]
                : senderPlayer.getName();

        List<Player> targets = buscarAlvos(targetName, senderPlayer);

        if (targets.isEmpty()) {
            senderPlayer.sendMessage("§cNenhum jogador-alvo foi encontrado.");
            return true;
        }

        switch (eventName) {

            case "tnt" -> {

                if (amount > 30) {
                    senderPlayer.sendMessage("§cO limite temporário é de 30 TNTs.");
                    return true;
                }

                for (Player target : targets) {
                    engine.submit(
                            new TntEvent(
                                    viewerName,
                                    GiftType.CUSTOM,
                                    target,
                                    amount
                            )
                    );
                }

                senderPlayer.sendMessage("§aEvento de TNT adicionado à fila.");
                return true;
            }

            case "lightning", "raio" -> {

                if (amount > 10) {
                    senderPlayer.sendMessage("§cO limite é de 10 raios.");
                    return true;
                }

                for (Player target : targets) {
                    engine.submit(
                            new LightningEvent(
                                    viewerName,
                                    GiftType.CUSTOM,
                                    target,
                                    amount
                            )
                    );
                }

                senderPlayer.sendMessage("§aEvento de raio adicionado à fila.");
                return true;
            }

            default -> {

                EntityType entityType = buscarMob(eventName);

                if (entityType == null) {
                    senderPlayer.sendMessage("§cEvento inválido.");
                    enviarAjuda(senderPlayer);
                    return true;
                }

                if (amount > 50) {
                    senderPlayer.sendMessage("§cO limite é de 50 mobs.");
                    return true;
                }

                for (Player target : targets) {
                    engine.submit(
                            new MobSpawnEvent(
                                    viewerName,
                                    GiftType.CUSTOM,
                                    target,
                                    entityType,
                                    amount
                            )
                    );
                }

                senderPlayer.sendMessage("§aEvento de mob adicionado à fila.");
                return true;
            }
        }
    }

    private int lerQuantidade(String[] args, Player player) {

        if (args.length < 3) {
            return 1;
        }

        try {
            int amount = Integer.parseInt(args[2]);

            if (amount < 1) {
                player.sendMessage("§cA quantidade deve ser maior que zero.");
                return -1;
            }

            return amount;

        } catch (NumberFormatException exception) {
            player.sendMessage("§cA quantidade precisa ser um número inteiro.");
            return -1;
        }
    }

    private List<Player> buscarAlvos(
            String targetName,
            Player senderPlayer
    ) {

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

        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            senderPlayer.sendMessage(
                    "§cJogador não encontrado: §e" + targetName
            );
            return targets;
        }

        targets.add(target);
        return targets;
    }

    private EntityType buscarMob(String nome) {

        return switch (nome) {
            case "creeper" -> EntityType.CREEPER;
            case "zombie", "zumbi" -> EntityType.ZOMBIE;
            case "skeleton", "esqueleto" -> EntityType.SKELETON;
            case "spider", "aranha" -> EntityType.SPIDER;
            case "pig", "porco" -> EntityType.PIG;
            default -> null;
        };
    }

    private void enviarAjuda(Player player) {

        player.sendMessage("§6Comandos do LiveEvents:");
        player.sendMessage(
                "§e/live trigger <evento> <quantidade> <espectador> <alvo>"
        );
        player.sendMessage("§7Alvos: nome do jogador, random ou all");
        player.sendMessage(
                "§7Eventos: creeper, zombie, skeleton, spider, pig, tnt e lightning"
        );
        player.sendMessage(
                "§7Exemplo: /live trigger zombie 3 Joao123 Zapphon"
        );
    }
}