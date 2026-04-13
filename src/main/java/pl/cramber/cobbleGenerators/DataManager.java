package pl.cramber.cobbleGenerators;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class DataManager {
    private final CobbleGenerators plugin;
    private File dataFile;
    private FileConfiguration dataConfig;
    private final Set<String> generators = ConcurrentHashMap.newKeySet();

    public DataManager(CobbleGenerators plugin) {
        this.plugin = plugin;
        setup();
    }

    public void setup() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create data.yml", e);
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        if (dataConfig.contains("locations")) {
            generators.addAll(dataConfig.getStringList("locations"));
        }
    }

    public void addGenerator(Block block) {
        generators.add(format(block));
        save();
    }

    public void removeGenerator(Block block) {
        generators.remove(format(block));
        save();
    }

    public boolean isGenerator(Block block) {
        return generators.contains(format(block));
    }

    private String format(Block block) {
        return block.getWorld().getName() + "," + block.getX() + "," + block.getY() + "," + block.getZ();
    }

    public void save() {
        dataConfig.set("locations", new ArrayList<>(generators));
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save data.yml", e);
        }
    }
}