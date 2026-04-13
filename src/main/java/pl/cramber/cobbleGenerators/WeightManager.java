package pl.cramber.cobbleGenerators;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class WeightManager {
    private final NavigableMap<Double, Material> weights = new TreeMap<>();
    private double totalWeight = 0;

    public WeightManager(CobbleGenerators plugin) {
        ConfigurationSection weightSection = plugin.getConfig().getConfigurationSection("weights");

        if (weightSection != null) {
            for (String key : weightSection.getKeys(false)) {
                Material mat = Material.matchMaterial(key);
                if (mat != null) {
                    double weight = plugin.getConfig().getDouble("weights." + key);
                    if (weight > 0) {
                        totalWeight += weight;
                        weights.put(totalWeight, mat);
                    }
                }
            }
        }

        if (weights.isEmpty()) {
            totalWeight = 1.0;
            weights.put(1.0, Material.COBBLESTONE);
        }
    }

    public Material getRandomMaterial() {
        double random = ThreadLocalRandom.current().nextDouble() * totalWeight;
        return weights.higherEntry(random).getValue();
    }
}