package org.info_0.activatetracker.clouds;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.SpaceUsage;
import org.bukkit.configuration.file.FileConfiguration;
import org.info_0.activatetracker.ActivateTracker;
import org.info_0.activatetracker.Util;

import java.io.*;

public class DropBox {
    public static String reportLink;
    private static DbxClientV2 client;
    private static FileConfiguration config = ActivateTracker.getInstance().getConfig();
    public static float usedCloud;
    public static void authDropBox() throws DbxException, IOException {
        final String token = config.getString("clouds.dropbox.token");
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/activate-tracker").build();
        client = new DbxClientV2(config,token);
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());
        System.out.println(String.format(Util.getMessage("CloudActive"),"Dropbox"));
        getAccountSpace();
    }
    private static void getAccountSpace() throws DbxException {
        SpaceUsage spaceUsage = client.users().getSpaceUsage();
        long allocated;
        if (spaceUsage.getAllocation().isTeam()) {
            allocated = spaceUsage.getAllocation().getTeamValue().getAllocated();
        } else {
            allocated = spaceUsage.getAllocation().getIndividualValue().getAllocated();
        }
        String usage = new String();
        usedCloud = (float) ((spaceUsage.getUsed()/allocated)*100);
        for(int i=1;i<=100;i++){
            if(i<= usedCloud) {
                usage += "|";
                continue;
            }
            usage += " ";
        }
        System.out.println(String.format(Util.getMessage("CloudUsage"),usage, usedCloud));
    }
    public static void uploadFile(File folder,File file) throws IOException, DbxException {
        InputStream in = new FileInputStream(file.getPath());
        System.out.println(ActivateTracker.getInstance().getDataFolder().getPath()+file.getPath());
        client.files().uploadBuilder("/"+folder.getName()+"/"+file.getName()).uploadAndFinish(in);
    }
    public static void createLink(File folder, File file) throws DbxException {
        SharedLinkMetadata metadata = client.sharing().createSharedLinkWithSettings("/"+folder.getName()+"/"+file.getName());
        reportLink = metadata.getUrl();
    }
}
