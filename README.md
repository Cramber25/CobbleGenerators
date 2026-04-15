# CobbleGenerators

CobbleGenerators gives you full control over what spawns from cobblestone generators. It’s an essential utility for SkyBlock or EasyHC servers where you want players to farm ores without leaving their bases/islands.

You can configure it to globally override all vanilla lava/water generators, or give players a physical "Custom Generator" block they can place down.

## Features
* **Weight-Based Drops:** Set exact percentage chances for different ores and blocks to spawn.
* **Two Generation Modes:** `NATURAL`: Automatically applies your custom drops to every standard cobblestone generator in the world. `CUSTOM_BLOCK`: Leaves vanilla generators alone, and instead uses a custom placeable block that generates the ores.
* **Safe Breaking:** If you use the custom block mode, you can set a specific tool required to break the generator so players don't accidentally destroy it.
* **Live Reloading:** Tweak your drop weights and update them in-game using `/cobblegen reload` without restarting the server.
* **Custom Messages:** Fully translate the plugin using MiniMessage formatting.

## Commands & Permissions
* `/cobblegen give <player>` - Give a custom generator block to a player. *(Permission: `cobblegenerators.admin`)*
* `/cobblegen enable/disable` - Toggle the custom generators on or off. *(Permission: `cobblegenerators.admin`)*
* `/cobblegen reload` - Reload the config and drop weights. *(Permission: `cobblegenerators.admin`)*

## Configuration (config.yml)
```yaml
mode: "NATURAL" # can be "NATURAL" or "CUSTOM_BLOCK"

custom_block:
  material: "SPONGE"
  name: "<gold><bold>Cobble Generator</bold></gold>"
  breaktool: "GOLDEN_PICKAXE"

weights:
  COBBLESTONE: 50.0
  COAL_ORE: 20.0
  IRON_ORE: 15.0
  GOLD_ORE: 10.0
  REDSTONE_ORE: 5.0
  LAPIS_ORE: 5.0
  DIAMOND_ORE: 2.0
  EMERALD_ORE: 0.5

messages:
  given: "<green>Given a custom generator to %player%.</green>"
  received: "<green>You received a custom generator.</green>"
  placed: "<green>Custom generator placed.</green>"
  destroyed: "<red>Custom generator destroyed.</red>"
  no_permission: "<red>No permission.</red>"
  invalid_usage: "<red>Usage: /cobblegen give <player></red>"
  player_not_found: "<red>Player not found.</red>"
  not_enabled: "<red>Custom generators are currently disabled.</red>"
  reloaded: "<green>Configuration and weights reloaded successfully.</green>"
  enabled: "<green>Custom generators enabled.</green>"
  disabled: "<green>Custom generators disabled. Reverted to NATURAL mode.</green>"
```
