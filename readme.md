# MeteorShower

A fun and customizable Minecraft plugin that adds a meteor shower event to your server. Players can find and mine fallen meteors to receive special rewards!

## Features

- **Customizable Event:** Easily configure the meteor's block type, height, and cooldown through the `config.yml`.
- **Random Rewards:** Set up a list of commands in the configuration to be executed when a player mines the meteor. This allows for flexible rewards like items, money, or custom messages.
- **Broadcast Messages:** Announce the event and its status to all players on the server. All messages are fully translatable and configurable.
- **Lightweight:** Designed to be a simple and efficient plugin that doesn't consume many server resources.
- **Version Compatibility:** Built with the `1.8.8` API and uses **XMaterial** to ensure compatibility with various Minecraft versions for item and sound handling.

## Installation

1.  Download the latest version of the plugin from the [Releases page](https://github.com/benardamorkoc/Meteor-Shower/releases/tag/v1.0.0).
2.  Place the `MeteorShower.jar` file into your server's `/plugins/` folder.
3.  Restart or reload your server.
4.  A `MeteorShower` folder will be created in your `/plugins/` directory, containing the default `config.yml`. Customize it to your liking.
5.  Enjoy the meteor shower event!

## Commands

| Command | Description | Permission |
| :--- | :--- | :--- |
| `/meteorshower <player>` | Triggers a meteor shower event at the specified player's location. | `meteorshower.admin` |

## Configuration

The `config.yml` file is automatically generated and contains all the settings you need to customize the plugin.

```yaml
#
# Meteor Shower Plugin Configuration
#
# Fully customizable settings and messages for the meteor shower event.
#

# General settings
meteor-block: 'DIAMOND_BLOCK' # The block that will fall as a meteor. Use XMaterial names.
meteor-height: 100 # The height from which the meteor will fall.
cooldown-minutes: 60 # Cooldown for the event in minutes.

# Rewards settings
rewards:
  - 'give {player} diamond 1'
  - 'eco give {player} 1000'
  - 'give {player} experience_bottle 3'

# Messages
messages:
  prefix: '&6[&bMeteorShower&6] '
  no-permission: '&cYou do not have permission to use this command.'
  invalid-command: '&cInvalid command usage! Use /meteorshower <player>.'
  meteor-summoned: '&aA meteor is about to strike at the location of &e{player}&a!'
  meteor-landed: '&6A meteor has landed! Find and mine it for a special reward!'
  cooldown-active: '&cA meteor shower has already been started recently. Please wait {time} more minutes.'
  reward-message: '&aYou have found a reward! You received: &e{reward}&a.'
  broadcast-message: '&6A meteor shower is starting! Be prepared!'
