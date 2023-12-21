package org.info_0.activatetracker.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.info_0.activatetracker.ActivateTracker;
import org.info_0.activatetracker.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.info_0.activatetracker.files.DataBase.playerDB;

public class Reporter {
    private static FileConfiguration config = ActivateTracker.getInstance().getConfig();
    public static void createReport(String player){
        File reportFolder = new File(ActivateTracker.getInstance().getDataFolder(), "reports");
        if(!reportFolder.exists()) reportFolder.mkdir();
        File reportFile = new File(reportFolder, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-W-EEE-kk-mm-ss"))+".txt");
        if(!reportFile.exists()) {
            try {
                reportFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try{
            FileWriter fw = new FileWriter(reportFile);
            PrintWriter pw = new PrintWriter(fw);
            Logger.createLog(LocalDateTime.now(),player,Util.getMessage("CreateReport"));
            pw.printf(Util.getMessage("ReportCreator")+"\n",player);
            for(String key: playerDB.keySet()){
                pw.printf(((playerDB.get(key)>=config.getInt("minactivatetime"))
                        ? Util.getMessage("PassedActivate"):Util.getMessage("FailedActivate")),
                        key,timeManager(playerDB.get(key)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String timeManager(int seconds){
        int second = seconds%60;
        int minute = seconds%3600-second;
        int hour = seconds/3600;
        return String.format(Util.getMessage("ActivateFormat"),hour,minute,second);
    }

}
