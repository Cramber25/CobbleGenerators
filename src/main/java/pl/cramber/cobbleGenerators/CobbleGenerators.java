package pl.cramber.cobbleGenerators;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CobbleGenerators extends JavaPlugin {
    private DataManager dataManager;
    private WeightManager weightManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        // bStats
        int pluginId = 30800;
        Metrics metrics = new Metrics(this, pluginId);

        dataManager = new DataManager(this);
        weightManager = new WeightManager(this);

        getServer().getPluginManager().registerEvents(new GeneratorListener(this), this);

        PluginCommand cmd = getCommand("cobblegen");
        if (cmd != null) {
            cmd.setExecutor(new GeneratorCommand(this));
        }
    }

    @Override
    public void onDisable() {
        if (dataManager != null) {
            dataManager.save();
        }
    }

    public void reloadPlugin() {
        reloadConfig();
        weightManager = new WeightManager(this);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public WeightManager getWeightManager() {
        return weightManager;
    }
}