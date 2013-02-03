package me.KeybordPiano459.Newspaper;

import me.KeybordPiano459.Newspaper.BukkitMetrics.Graph;

public class MetricsGraph {
    Newspaper plugin;
    public MetricsGraph(Newspaper plugin) {
        this.plugin = plugin;
        pages = plugin.getNewspaper().getNews().size();
    }
    
    private int pages;
    
    public void addPagesGraph(BukkitMetrics metrics) {
        Graph graph = metrics.createGraph("Pages in Newspaper");
        graph.addPlotter(new BukkitMetrics.Plotter(pages + " page(s)") {
            @Override
            public int getValue() {
                return pages;
            }
        });
    }
}