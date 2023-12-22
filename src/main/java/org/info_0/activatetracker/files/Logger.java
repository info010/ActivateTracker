package org.info_0.activatetracker.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.info_0.activatetracker.ActivateTracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static FileConfiguration config = ActivateTracker.getInstance().getConfig();
    public static void createLog(LocalDateTime date, String player,String logMessage) throws IOException {
        if(!config.getBoolean("logger")) return;
        File logFolder = new File(ActivateTracker.getInstance().getDataFolder(),"logs");
        if(!logFolder.exists()) logFolder.mkdir();
        File logFile = new File(logFolder, date.format(DateTimeFormatter.ofPattern("ww-yyyy"))+".log");
        if(!logFile.exists()) logFile.createNewFile();
        FileWriter fw = new FileWriter(logFile,true);
        PrintWriter pw = new PrintWriter(fw);
        pw.printf(logMessage,date.format(DateTimeFormatter.ofPattern("EEE:HH-mm-ss")),player);
        pw.flush();
        pw.close();
    }
}
