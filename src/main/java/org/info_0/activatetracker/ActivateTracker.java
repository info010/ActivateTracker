package org.info_0.activatetracker;

import com.dropbox.core.DbxException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.info_0.activatetracker.activates.InGameTime;
import org.info_0.activatetracker.clouds.DropBox;
import org.info_0.activatetracker.clouds.PathManager;
import org.info_0.activatetracker.commands.GetReport;
import org.info_0.activatetracker.files.DataBase;

import java.io.IOException;
import java.time.LocalDateTime;

public final class ActivateTracker extends JavaPlugin {
    private FileConfiguration config = this.getConfig();
    private static ActivateTracker instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Util.loadMessages();
        DataBase.loadDB();
        if(config.getBoolean("clouds.dropbox.active")) {
            try {
                DropBox.authDropBox();
            } catch (DbxException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        for(Player player : getServer().getOnlinePlayers()){
            InGameTime.setJoinTime(player,LocalDateTime.now());
        }
        getServer().getPluginManager().registerEvents(new InGameTime(),this);
        getCommand("getreport").setExecutor(new GetReport());
    }

    @Override
    public void onDisable() {
        for(Player player : getServer().getOnlinePlayers()){
            InGameTime.getTotalSeconds(player,LocalDateTime.now());
        }
    }
    public static ActivateTracker getInstance(){
        return instance;
    }
}
