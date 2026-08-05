package com.zapphon.liveevents.commands;

import com.zapphon.liveevents.actions.SpawnMobAction;
import com.zapphon.liveevents.actions.TestAction;
import com.zapphon.liveevents.managers.EventManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LiveCommand implements CommandExecutor {

    private final EventManager manager = new EventManager();

    @Override
    public boolean onCommand(
        CommandSender sender,
        Command command,
        String label,
        String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Este comando precisa ser usado por um jogador.");
            return true;
        }

        if (args.length == 0) {
            manager.execute(new TestAction(sender));
            return true;
        }

        if (!args[0].equalsIgnoreCase("trigger")) {
            enviarAjuda(player);
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("§cInforme o evento.");
            enviarAjuda(player);
            return true;
        }

        EntityType entityType = buscarMob(args[1]);

        if (entityType == null) {
            player.sendMessage("§cMob inválido.");
            enviarAjuda(player);
            return true;
        }

        int amount = 1;

        if (args.length >= 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException exception) {
                player.sendMessage("§cA quantidade precisa ser um número.");
                return true;
            }
        }

        if (amount < 1 || amount > 50) {
            player.sendMessage("§cA quantidade deve ficar entre 1 e 50.");
            return true;
        }

        manager.execute(
            new SpawnMobAction(
                player,
                entityType,
                amount
            )
        );

        return true;
    }

    private EntityType buscarMob(String nome) {

        return switch (nome.toLowerCase()) {
            case "creeper" -> EntityType.CREEPER;
            case "zombie", "zumbi" -> EntityType.ZOMBIE;
            case "skeleton", "esqueleto" -> EntityType.SKELETON;
            case "spider", "aranha" -> EntityType.SPIDER;
            default -> null;
        };
    }

    private void enviarAjuda(Player player) {

        player.sendMessage("§6Comandos do LiveEvents:");
        player.sendMessage("§e/live");
        player.sendMessage("§e/live trigger creeper <quantidade>");
        player.sendMessage("§e/live trigger zombie <quantidade>");
        player.sendMessage("§e/live trigger skeleton <quantidade>");
        player.sendMessage("§e/live trigger spider <quantidade>");
    }
}