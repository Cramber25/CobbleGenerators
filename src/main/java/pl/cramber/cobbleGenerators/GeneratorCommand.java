package pl.cramber.cobbleGenerators;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class GeneratorCommand implements CommandExecutor {
    private final CobbleGenerators plugin;

    public GeneratorCommand(CobbleGenerators plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("cobblegenerators.admin")) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.no_permission", "<red>No permission.</red>")));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.invalid_usage", "<red>Usage: /cobblegen <give|reload|enable|disable> [player]</red>")));
            return true;
        }

        String subCmd = args[0].toLowerCase();

        switch (subCmd) {
            case "reload" -> {
                plugin.reloadPlugin();
                sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.reloaded", "<green>Configuration and weights reloaded successfully.</green>")));
                return true;
            }
            case "enable" -> {
                plugin.getConfig().set("mode", "CUSTOM_BLOCK");
                plugin.saveConfig();
                sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.enabled", "<green>Custom generators enabled.</green>")));
                return true;
            }
            case "disable" -> {
                plugin.getConfig().set("mode", "NATURAL");
                plugin.saveConfig();
                sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.disabled", "<green>Custom generators disabled. Reverted to NATURAL mode.</green>")));
                return true;
            }
        }

        if (subCmd.equals("give") && args.length == 2) {
            if (!plugin.getConfig().getString("mode", "NATURAL").equalsIgnoreCase("CUSTOM_BLOCK")) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.not_enabled", "<red>Custom generators are currently disabled.</red>")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.player_not_found", "<red>Player not found.</red>")));
                return true;
            }

            target.getInventory().addItem(getGeneratorItem());

            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.given", "<green>Given a custom generator to %player%.</green>").replace("%player%", target.getName())));
            target.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.received", "<green>You received a custom generator.</green>")));
            return true;
        }

        sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("messages.invalid_usage", "<red>Usage: /cobblegen <give|reload|enable|disable> [player]</red>")));
        return true;
    }

    public ItemStack getGeneratorItem() {
        Material mat = Material.matchMaterial(plugin.getConfig().getString("custom_block.material", "SPONGE"));
        ItemStack item = new ItemStack(mat != null ? mat : Material.SPONGE);

        item.editMeta(meta -> {
            meta.displayName(MiniMessage.miniMessage().deserialize(plugin.getConfig().getString("custom_block.name", "<gold><bold>Cobble Generator</bold></gold>")));
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "is_generator"), PersistentDataType.BYTE, (byte) 1);
        });

        return item;
    }
}