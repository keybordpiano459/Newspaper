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
    
    public void addNewsTypeGraph(BukkitMetrics metrics) {
        Graph graph = metrics.createGraph("News Type Used");
        String nt = plugin.getConfig().getString("news-type");
        if (nt.equalsIgnoreCase("book")) addNTPlotter(graph, "Book");
        else if (nt.equalsIgnoreCase("map")) addNTPlotter(graph, "Map");
    }
    
    private void addNTPlotter(Graph graph, String name) {
        graph.addPlotter(new BukkitMetrics.Plotter(name) {
            @Override
            public int getValue() {
                return 1;
            }
        });
    }
}