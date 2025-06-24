package net.cutecraft.waxedlogs;

import lombok.Generated;
import lombok.Getter;
import net.cutecraft.waxedlogs.listener.BlockDataListener;
import net.cutecraft.waxedlogs.listener.InteractListener;
import net.cutecraft.waxedlogs.listener.LogListener;
import net.cutecraft.waxedlogs.service.ConfigService;
import net.cutecraft.waxedlogs.update.ComparableVersion;
import net.cutecraft.waxedlogs.update.VersionCheckTask;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class WaxedLogsPlugin extends JavaPlugin {

    @Getter private static WaxedLogsPlugin instance;
    private final Logger logger = getLogger();

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        ConfigService configService = new ConfigService(this);
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BlockDataListener(), this);
        pluginManager.registerEvents(new InteractListener(configService), this);
        pluginManager.registerEvents(new LogListener(), this);
        checkForUpdates();
    }

    private void checkForUpdates() {
        new VersionCheckTask(latestVersion -> {
            final ComparableVersion currentVersion = new ComparableVersion(getDescription().getVersion());
            if (currentVersion.compareTo(latestVersion) < 0) {
                logger.warning("A new version of WaxedLogs is available: " + latestVersion.getRawVersion());
                logger.info("Download the latest version at: " + VersionCheckTask.RESOURCE);
            }
        }).runTaskAsynchronously(this);
    }
}