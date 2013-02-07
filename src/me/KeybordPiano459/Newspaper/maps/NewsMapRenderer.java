package me.KeybordPiano459.Newspaper.maps;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import me.KeybordPiano459.Newspaper.Newspaper;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class NewsMapRenderer extends MapRenderer {
    Newspaper plugin;
    public NewsMapRenderer(Newspaper plugin) {
        this.plugin = plugin;
    }
    
    private File folder = new File("plugins" + File.separator + "Newspaper");
    
    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        int x = 0;
        try {
            File file = new File(folder, "map.txt");
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                canvas.drawText(0, x, MinecraftFont.Font, s.nextLine());
                x += 10;
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}