package org.info_0.activatetracker.clouds;

import org.info_0.activatetracker.ActivateTracker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathManager {
    public static Map<File,List<File>> paths = new HashMap<>();
    public static void getPaths(){
        getReportPaths();
    }
    private static void getReportPaths() {
        File reportFolder = new File(ActivateTracker.getInstance().getDataFolder(), "reports");
        if(!reportFolder.exists()) reportFolder.mkdir();
        List<File> reportFiles = new ArrayList<>();
        for(File reportFile : reportFolder.listFiles()){
            reportFiles.add(reportFile);
        }
        paths.put(reportFolder,reportFiles);
    }
    public static void addPath(File folder,File file){
        List<File> fileList;
        if(paths.get(folder).isEmpty()) {
            fileList = new ArrayList<>();
        }else{
            fileList = paths.get(folder);
        }
        fileList.add(file);
        paths.put(folder,fileList);
    }
}
