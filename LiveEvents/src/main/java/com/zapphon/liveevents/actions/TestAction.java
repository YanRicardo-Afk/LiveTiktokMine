package com.zapphon.liveevents.actions;

import org.bukkit.command.CommandSender;

public class TestAction implements Action {

    private final CommandSender sender;

    public TestAction(CommandSender sender) {

        this.sender = sender;

    }

    @Override
    public void execute() {

        sender.sendMessage("§aTestAction executada!");

    }

}