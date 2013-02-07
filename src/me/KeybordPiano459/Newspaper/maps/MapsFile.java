package me.KeybordPiano459.Newspaper.maps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapsFile {
    
    private File folder = new File("plugins" + File.separator + "Newspaper");
    private File mapsFile = null;
    
    public void createMapsFile() {
        mapsFile = new File(folder, "map.txt");
        if (!mapsFile.exists()) {
            try {
                mapsFile.createNewFile();
                FileWriter writer = new FileWriter(mapsFile);
                writer.write("This is on a map.\n");
                writer.write("Color symbols are not\n");
                writer.write("supported.");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}