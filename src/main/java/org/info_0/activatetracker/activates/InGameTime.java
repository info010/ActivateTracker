package org.info_0.activatetracker.activates;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.info_0.activatetracker.Util;
import org.info_0.activatetracker.files.DataBase;
import org.info_0.activatetracker.files.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.info_0.activatetracker.files.DataBase.playerDB;

public class InGameTime implements Listener {
    private LocalDateTime joinTime;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        joinTime = LocalDateTime.now();
        try {
            Logger.createLog(joinTime,event.getPlayer().getName(), Util.getMessage("JoinPlayer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        getTotalSeconds(event.getPlayer(),LocalDateTime.now());
    }
    public void getTotalSeconds(Player player, LocalDateTime quitTime){
        long seconds = ChronoUnit.SECONDS.between(joinTime,quitTime);
        int totalSecond = (int) seconds;
        try {
            if(!playerDB.containsKey(player.getName())) playerDB.put(player.getName(),0);
            DataBase.createData(player, playerDB.get(player.getName())+totalSecond);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Logger.createLog(joinTime,player.getName(), Util.getMessage("QuitPlayer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
