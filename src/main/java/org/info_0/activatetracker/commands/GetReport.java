package org.info_0.activatetracker.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.info_0.activatetracker.Util;
import org.info_0.activatetracker.clouds.DropBox;
import org.info_0.activatetracker.files.Reporter;

public class GetReport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Reporter.createReport(String.valueOf(commandSender));
        System.out.println(DropBox.downloadLink);
        TextComponent component = new TextComponent(Util.getMessage("ReportDownload"));
        component.setColor(ChatColor.AQUA);
        component.setClickEvent( new ClickEvent(ClickEvent.Action.OPEN_URL, DropBox.downloadLink));
        commandSender.spigot().sendMessage(component);
        return true;
    }
}
