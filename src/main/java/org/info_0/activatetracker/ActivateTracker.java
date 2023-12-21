package org.info_0.activatetracker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.info_0.activatetracker.activates.InGameTime;
import org.info_0.activatetracker.commands.GetReport;
import org.info_0.activatetracker.files.DataBase;

import java.time.LocalDateTime;

public final class ActivateTracker extends JavaPlugin {
    private static ActivateTracker instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Util.loadMessages();
        DataBase.loadDB();
        getServer().getPluginManager().registerEvents(new InGameTime(),this);
        getCommand("getreport").setExecutor(new GetReport());
    }

    @Override
    public void onDisable() {
        for(Player player : getServer().getOnlinePlayers()){
            InGameTime inGameTime = new InGameTime();
            inGameTime.getTotalSeconds(player, LocalDateTime.now());
        }
    }
    public static ActivateTracker getInstance(){
        return instance;
    }
}
