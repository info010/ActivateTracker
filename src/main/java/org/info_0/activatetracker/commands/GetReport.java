package org.info_0.activatetracker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.info_0.activatetracker.files.Reporter.createReport;

public class GetReport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        createReport(commandSender.getName());
        return true;
    }
}
