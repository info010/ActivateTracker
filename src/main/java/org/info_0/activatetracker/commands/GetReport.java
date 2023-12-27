package org.info_0.activatetracker.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.info_0.activatetracker.ActivateTracker;
import org.info_0.activatetracker.Util;
import org.info_0.activatetracker.clouds.DropBox;
import org.info_0.activatetracker.files.Reporter;

public class GetReport implements CommandExecutor {
    private FileConfiguration config = ActivateTracker.getInstance().getConfig();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.hasPermission("activate-tracker.getreport")) return false;
        Reporter.createReport(commandSender.getName());
        if(config.getBoolean("clouds.dropbox.active")) sendReportLink(commandSender);
        return true;
    }
    private void sendReportLink(CommandSender commandSender){
        TextComponent component = new TextComponent(Util.getMessage("ReportDownload"));
        component.setColor(ChatColor.AQUA);
        component.setClickEvent( new ClickEvent(ClickEvent.Action.OPEN_URL, DropBox.reportLink));
        commandSender.spigot().sendMessage(component);
    }
}
