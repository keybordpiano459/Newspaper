package me.KeybordPiano459.Newspaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

public class UpdateChecker {
    Newspaper plugin;
    public UpdateChecker(Newspaper plugin) {
        this.plugin = plugin;
        currentVersion = plugin.version;
    }

    private String currentVersion;

    public void startUpdateCheck() {
        if (plugin.getConfig().getBoolean("update-checker")) {
            Logger log = plugin.getLogger();
            try {
                log.info("Checking for a new version...");
                URL url = new URL("https://raw.github.com/keybordpiano459/Newspaper/master/version.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = br.readLine()) != null) {
                    String line = str;
                    if (line.charAt(0) == '#');
                    if (line.charAt(0) == '1' && line.charAt(2) == '3' && line.charAt(4) == '1') {
                        plugin.updatemsg = line.substring(5);
                        log.info(plugin.updatemsg);
                    }
                }
                br.close();
            } catch (IOException e) {
                log.severe("The UpdateChecker URL is invalid! Please let KeybordPiano459 know!");
            }
        }
    }
}
