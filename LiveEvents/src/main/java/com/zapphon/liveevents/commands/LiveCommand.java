package com.zapphon.liveevents.commands;

import com.zapphon.liveevents.managers.EventManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LiveCommand implements CommandExecutor {

    private final EventManager manager = new EventManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        manager.executeTest(sender);

        return true;

    }

}