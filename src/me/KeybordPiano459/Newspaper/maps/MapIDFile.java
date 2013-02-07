package me.KeybordPiano459.Newspaper.maps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapIDFile {
    
    private File folder = new File("plugins" + File.separator + "Newspaper");
    private File mapsFile = null;
    
    public void createMapIDFile() {
        mapsFile = new File(folder, "mapids.txt");
        if (!mapsFile.exists()) {
            try {
                mapsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void putMapID(Short id) {
        try {
            ArrayList<Short> ids = getMapIDs();
            String idlist = "";
            for (Short s : ids) idlist += s + "\n";
            idlist += id;
            FileWriter w = new FileWriter(mapsFile);
            w.write(idlist);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Short> getMapIDs() {
        ArrayList<Short> ids = new ArrayList<>();
        try {
            Scanner s = new Scanner(mapsFile);
            while (s.hasNextLine()) {
                ids.add(Short.valueOf(s.nextLine()));
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return ids;
    }
}