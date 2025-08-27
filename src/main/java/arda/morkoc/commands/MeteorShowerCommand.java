package arda.morkoc.commands;

import arda.morkoc.MeteorShower;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MeteorShowerCommand implements CommandExecutor {

    private final MeteorShower plugin;
    private final Logger log;

    public MeteorShowerCommand(MeteorShower plugin) {
        this.plugin = plugin;
        this.log = plugin.getLogger();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("meteorshower.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.no-permission")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.invalid-command")));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + "&cPlayer not found!"));
            return true;
        }

        long cooldownMinutes = plugin.getConfig().getLong("cooldown-minutes");
        long lastMeteorTime = plugin.getLastMeteorTime();
        long timeSinceLastMeteor = (System.currentTimeMillis() - lastMeteorTime);

        if (lastMeteorTime != 0 && timeSinceLastMeteor < TimeUnit.MINUTES.toMillis(cooldownMinutes)) {
            long remainingTime = TimeUnit.MINUTES.toMinutes(TimeUnit.MILLISECONDS.toMinutes(TimeUnit.MINUTES.toMillis(cooldownMinutes) - timeSinceLastMeteor));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.cooldown-active").replace("{time}", String.valueOf(remainingTime))));
            return true;
        }

        plugin.setLastMeteorTime(System.currentTimeMillis());

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.broadcast-message")));

        // Spawn meteor block
        Location playerLoc = targetPlayer.getLocation();
        Location meteorLoc = new Location(playerLoc.getWorld(), playerLoc.getX(), plugin.getConfig().getInt("meteor-height"), playerLoc.getZ());

        // Use XMaterial for 1.8.8 compatibility
        XMaterial material = XMaterial.valueOf(plugin.getConfig().getString("meteor-block"));
        if (material.isSupported()) {
            Block meteorBlock = meteorLoc.getBlock();
            meteorBlock.setType(material.parseMaterial());
            log.info("Meteor block placed at " + meteorLoc.toString());
        } else {
            log.warning("Invalid meteor block in config.yml! Using a default block.");
            meteorLoc.getBlock().setType(Material.STONE);
        }

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.meteor-summoned").replace("{player}", targetPlayer.getName())));
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.meteor-landed")));
        }, 20 * 5); // 5 seconds later

        return true;
    }
}

