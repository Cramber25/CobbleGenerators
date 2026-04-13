# Cobble Generators

This is a super simple plugin which modifies the properties of cobblestone generators, perfect for SkyBlock servers.

## Features:
- Modify the blocks that can generate from a cobble generator
- Optional setting to leave vanilla generators as they were, and create a custom generator block
- Destroy custom generators with a special tool
- Modify plugin messages

## Commands:
- /cobblegen give (`cobblegenerators.admin`)
- /cobblegen reload (`cobblegenerators.admin`)
- /cobblegen enable/disable (`cobblegenerators.admin`)

## Config:
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
