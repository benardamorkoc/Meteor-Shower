package arda.morkoc;

import arda.morkoc.commands.MeteorShowerCommand;
import arda.morkoc.listeners.BlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class MeteorShower extends JavaPlugin {

    private static MeteorShower instance;
    private long lastMeteorTime = 0;
    private final Logger log = this.getLogger();

    @Override
    public void onEnable() {
        instance = this;
        this.log.info("MeteorShower plugin is enabling...");

        // Save default config if not exists
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        // Register commands and listeners
        this.getCommand("meteorshower").setExecutor(new MeteorShowerCommand(this));
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        this.log.info("MeteorShower plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        this.log.info("MeteorShower plugin is disabling...");
        instance = null;
        this.log.info("MeteorShower plugin disabled!");
    }

    public static MeteorShower getInstance() {
        return instance;
    }

    public long getLastMeteorTime() {
        return lastMeteorTime;
    }

    public void setLastMeteorTime(long time) {
        this.lastMeteorTime = time;
    }
}

