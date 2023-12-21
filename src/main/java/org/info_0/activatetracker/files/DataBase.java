package org.info_0.activatetracker.files;

import org.bukkit.entity.Player;
import org.info_0.activatetracker.ActivateTracker;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    public static Map<String,Integer> playerDB = new HashMap<>();
    private static String line;
    public static void createData(Player player,int seconds) throws IOException {
        if(!playerDB.containsKey(player.getName())) playerDB.put(player.getName(),0);
        File dbFolder = new File(ActivateTracker.getInstance().getDataFolder(), "database");
        if(!dbFolder.exists()) dbFolder.mkdir();
        File dbFile = new File(dbFolder, LocalDateTime.now().format(DateTimeFormatter.ofPattern("ww-yyyy"))+".db");
        if(!dbFile.exists()) dbFolder.createNewFile();
        playerDB.put(player.getName(),playerDB.get(player.getName())+seconds);
        FileWriter fw = new FileWriter(dbFile);
        PrintWriter pw = new PrintWriter(fw);
        String data = player.getName()+":"+seconds;
        pw.println(data);
        pw.flush();
        pw.close();
    }
    public static void loadDB() {
        File dbFolder = new File(ActivateTracker.getInstance().getDataFolder(), "database");
        if(!dbFolder.exists()) dbFolder.mkdir();
        File dbFile = new File(dbFolder, LocalDateTime.now().format(DateTimeFormatter.ofPattern("ww-yyyy"))+".db");
        if(!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(dbFile));
            while((line = br.readLine()) != null){
                String[] keyValuePair = line.split(":",2);
                if(keyValuePair.length < 1) continue;
                String key = keyValuePair[0];
                int value = Integer.parseInt(keyValuePair[1]);
                playerDB.put(key,value);
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
