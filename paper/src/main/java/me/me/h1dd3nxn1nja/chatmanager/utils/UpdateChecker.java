package me.me.h1dd3nxn1nja.chatmanager.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {
	
    private static int project = 52245;
    private URL checkURL;
    private static String newVersion = "";
    private JavaPlugin plugin;

    public UpdateChecker(JavaPlugin plugin, int projectID) {
        this.plugin = plugin;
        UpdateChecker.newVersion = plugin.getDescription().getVersion();
        UpdateChecker.project = projectID;
        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public int getProjectID() {
        return project;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public static String getLatestVersion() {
        return newVersion;
    }

    public static String getResourceURL() {
        return "https://www.spigotmc.org/resources/" + project;
    }

    public boolean checkForUpdates() throws Exception {
        URLConnection con = checkURL.openConnection();
        UpdateChecker.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream()))
                .readLine();
        return !plugin.getDescription().getVersion().equals(newVersion);
    }
}