package me.trolking1.calorthrealmcore.guilds;

import me.trolking1.calorthrealmcore.Main;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 3/29/2017.
 */
public class Guild implements ConfigurationSerializable {

    private String name;
    private Chunk mainChunk;
    private List<Chunk> chunks;
    private List<Plot> plots;
    private Citizens citizens;
    private GuildState guildState;
    private Location guildHome;


    public Guild(String name, Location guildHome, Location mainChunkLocation, List<Location> chunkLocations, List<Plot> plots) {
        this.name = name;
        this.guildHome = guildHome;
        this.mainChunk = mainChunkLocation.getChunk();
        List<Chunk> chunks = new ArrayList<>();
        for (Location location : chunkLocations) {
            chunks.add(location.getChunk());
        }
        this.chunks = chunks;
        this.plots = plots;

        // TODO
        //SETTER OF CITIZENS AND GUILDSTATE
        //this.citizens = Main.database.getCitizens(name);
        //this.guildState = Main.database.getGuildState(name);

    }

    public Guild(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.guildHome = (Location) map.get("guildhome");
        this.mainChunk = ((Location) map.get("mainchunklocation")).getChunk();
        List<Chunk> chunks = new ArrayList<>();
        for (Location location : (List<Location>) map.get("chunklocations")) {
            chunks.add(location.getChunk());
        }
        this.chunks = chunks;
        this.plots = (List<Plot>) map.get("plots");
        this.citizens = Main.database.getCitizens(name);
        this.guildState = Main.database.getGuildState(name);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("guildhome", guildHome);
        map.put("mainchunklocation", new Location(mainChunk.getWorld(), mainChunk.getX(), mainChunk.getZ(), 1));
        List<Location> chunkLocations = new ArrayList<>();
        for (Chunk chunk : chunks) {
            chunkLocations.add(new Location(chunk.getWorld(), chunk.getX(), chunk.getZ(), 1));
        }
        map.put("ckunklocations", chunkLocations);
        map.put("plots", plots);

        return map;
    }

    public boolean inGuild(Location location) {
        if (location.getChunk() == mainChunk) {
            return true;
        }

        for (Chunk chunk : chunks) {
            if (location.getChunk() == chunk) {
                return true;
            }
        }

        return false;
    }

    public boolean inGuildWithoutPlots(Location location) {
        if (plots != null) {
            for (Plot plot : plots) {
                if (plot.getPlot().inRegion(location)) {
                    return false;
                }
            }
        }

        if (location.getChunk() == mainChunk) {
            return true;
        }

        for (Chunk chunk : chunks) {
            if (location.getChunk() == chunk) {
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getGuildHome() {
        return guildHome;
    }

    public void setGuildHome(Location guildHome) {
        this.guildHome = guildHome;
    }

    public Chunk getMainChunk() {
        return mainChunk;
    }

    public void setMainChunk(Chunk mainChunk) {
        this.mainChunk = mainChunk;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public List<Plot> getPlots() {
        return plots;
    }

    public void setPlots(List<Plot> plots) {
        this.plots = plots;
    }

    public Citizens getCitizens() {
        return citizens;
    }

    public void setCitizens(Citizens citizens) {
        this.citizens = citizens;
    }
}
