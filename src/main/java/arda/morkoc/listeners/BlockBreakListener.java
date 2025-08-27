package arda.morkoc.listeners;

import arda.morkoc.MeteorShower;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final MeteorShower plugin;

    public BlockBreakListener(MeteorShower plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Check if the broken block is the meteor block
        String configBlockName = plugin.getConfig().getString("meteor-block");
        XMaterial material = XMaterial.valueOf(configBlockName);

        if (material.isSupported() && block.getType() == material.parseMaterial()) {

            // Give a random reward
            List<String> rewards = plugin.getConfig().getStringList("rewards");
            if (!rewards.isEmpty()) {
                Random random = new Random();
                String randomReward = rewards.get(random.nextInt(rewards.size()));

                // Execute command from console
                String commandToExecute = randomReward.replace("{player}", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToExecute);

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.reward-message").replace("{reward}", randomReward)));
            }
        }
    }
}
