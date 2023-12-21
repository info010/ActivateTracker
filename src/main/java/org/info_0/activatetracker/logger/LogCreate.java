package org.info_0.activatetracker.logger;

import org.bukkit.entity.Player;
import org.info_0.activatetracker.ActivateTracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogCreate {
    public static void createLog(LocalDateTime date, Player p,String logMessage) throws IOException {
        File logFolder = new File(ActivateTracker.getInstance().getDataFolder(),"logs");
        if(!logFolder.exists()) logFolder.mkdir();
        File logFile = new File(logFolder, date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+".log");
        if(!logFile.exists()) logFolder.createNewFile();
        FileWriter fw = new FileWriter(logFile,true);
        PrintWriter pw = new PrintWriter(fw);
        String message = "["+date.format(DateTimeFormatter.ofPattern("hh-mm-ss"))+"]=> "+p.getName()+" "+logMessage;
        pw.println(message);
        pw.flush();
        pw.close();
    }
}
