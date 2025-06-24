package net.cutecraft.waxedlogs.service;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigService {
    private final FileConfiguration config;

    public ConfigService(Plugin plugin) {
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public boolean isPermissionOnly() {
        return config.getBoolean("only-permission.enable");
    }

    public String getPermission() {
        return config.getString("only-permission.permission");
    }
}