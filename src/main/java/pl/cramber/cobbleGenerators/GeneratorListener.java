package pl.cramber.cobbleGenerators;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class GeneratorListener implements Listener {
    private final CobbleGenerators plugin;

    public GeneratorListener(CobbleGenerators plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        if (!plugin.getConfig().getString("mode", "NATURAL").equalsIgnoreCase("NATURAL")) {
            return;
        }

        if (event.getNewState().getType() == Material.COBBLESTONE) {
            Material nextMat = plugin.getWeightManager().getRandomMaterial();
            event.getNewState().setType(nextMat);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!plugin.getConfig().getString("mode", "NATURAL").equalsIgnoreCase("CUSTOM_BLOCK")) {
            return;
        }

        ItemStack item = event.getItemInHand();
        if (item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "is_generator"), PersistentDataType.BYTE)) {
            plugin.getDataManager().addGenerator(event.getBlock());

            Material nextMat = plugin.getWeightManager().getRandomMaterial();

            Bukkit.getRegionScheduler().runDelayed(plugin, event.getBlock().getLocation(), task -> {
                event.getBlock().setType(nextMat);
                event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.5f, 2.0f);
            }, 10L);

            String msg = plugin.getConfig().getString("messages.placed", "<green>Custom generator placed.</green>");
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(msg));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!plugin.getConfig().getString("mode", "NATURAL").equalsIgnoreCase("CUSTOM_BLOCK")) {
            return;
        }

        Block block = event.getBlock();
        if (plugin.getDataManager().isGenerator(block)) {
            Player player = event.getPlayer();

            String breakToolStr = plugin.getConfig().getString("custom_block.breaktool", "GOLDEN_PICKAXE")
                    .toUpperCase().replace(" ", "_");

            if (breakToolStr.equals("GOLD_PICKAXE")) {
                breakToolStr = "GOLDEN_PICKAXE";
            } else if (breakToolStr.equals("WOOD_PICKAXE")) {
                breakToolStr = "WOODEN_PICKAXE";
            }

            Material breakTool = Material.matchMaterial(breakToolStr);
            boolean isUsingBreakTool = breakTool != null && player.getInventory().getItemInMainHand().getType() == breakTool;

            if (isUsingBreakTool) {
                plugin.getDataManager().removeGenerator(block);
                event.setDropItems(false);

                GeneratorCommand cmd = new GeneratorCommand(plugin);
                block.getWorld().dropItemNaturally(block.getLocation(), cmd.getGeneratorItem());

                String msg = plugin.getConfig().getString("messages.destroyed", "<red>Custom generator destroyed.</red>");
                player.sendMessage(MiniMessage.miniMessage().deserialize(msg));
                return;
            }

            Material nextMat = plugin.getWeightManager().getRandomMaterial();

            Bukkit.getRegionScheduler().runDelayed(plugin, block.getLocation(), task -> {
                block.setType(nextMat);
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.5f, 2.0f);
            }, 10L);
        }
    }
}