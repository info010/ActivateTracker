package org.info_0.activatetracker.activates;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.info_0.activatetracker.Util;
import org.info_0.activatetracker.logger.LogCreate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class InGameTime implements Listener {
    public Map<Player, Integer> inGameTime = new HashMap<>();
    public LocalDateTime joinTime;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        joinTime = LocalDateTime.now();
        try {
            LogCreate.createLog(joinTime,event.getPlayer(), Util.getMessage("JoinPlayer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        getTotalMinute(event.getPlayer(),LocalDateTime.now());
    }
    public void getTotalMinute(Player player,LocalDateTime quitTime){
        long seconds = ChronoUnit.SECONDS.between(joinTime,quitTime);
        int totalSecond = (int) seconds;
        inGameTime.put(player, totalSecond);
        System.out.println(totalSecond+" seconds.");
        try {
            LogCreate.createLog(joinTime,player, Util.getMessage("QuitPlayer"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
