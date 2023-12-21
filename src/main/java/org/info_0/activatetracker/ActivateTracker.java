package org.info_0.activatetracker;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.info_0.activatetracker.activates.InGameTime;
import org.info_0.activatetracker.logger.LogCreate;

import java.time.LocalDateTime;

public final class ActivateTracker extends JavaPlugin {
    private static ActivateTracker instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Util.loadMessages();
        getServer().getPluginManager().registerEvents(new InGameTime(),this);
    }

    @Override
    public void onDisable() {
        for(Player player : getServer().getOnlinePlayers()){
            if(!player.hasPermission("activatetracker.ingametime")) continue;
            InGameTime inGameTime = new InGameTime();
            inGameTime.getTotalMinute(player, LocalDateTime.now());
        }
    }
    public static ActivateTracker getInstance(){
        return instance;
    }
}
