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
import java.util.HashMap;
import java.util.Map;

import static org.info_0.activatetracker.files.DataBase.playerDB;

public class InGameTime implements Listener {
    private static Map<Player,LocalDateTime> joinTime = new HashMap<>();
    public static void setJoinTime(Player player,LocalDateTime time){
        joinTime.put(player,time);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        setJoinTime(event.getPlayer(),LocalDateTime.now());
        try {
            Logger.createLog(joinTime.get(event.getPlayer()),event.getPlayer().getName(), Util.getMessage("JoinPlayer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        getTotalSeconds(event.getPlayer(),LocalDateTime.now());
    }
    public static void getTotalSeconds(Player player, LocalDateTime time){
        long seconds = ChronoUnit.SECONDS.between(joinTime.get(player),time);
        int totalSecond = (int) seconds;
        try {
            if(!playerDB.containsKey(player.getName())) playerDB.put(player.getName(),0);
            DataBase.createData(player, playerDB.get(player.getName())+totalSecond);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Logger.createLog(joinTime.get(player),player.getName(), Util.getMessage("QuitPlayer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
