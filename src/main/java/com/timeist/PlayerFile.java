package com.timeist;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerFile {
    private String path;
    private UUID uuid;
    private File file;
    private FileConfiguration config;

    public PlayerFile(UUID uuid) {
        this.path = TimeistsDecos.getInstance().getDataFolder() + File.separator + "Player Data";
        this.uuid = uuid;
        this.file = new File(this.path, uuid.toString().replaceAll("-", "") + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.config.addDefault("prefix", "");
        this.config.addDefault("Nickname", "");
        this.config.addDefault("suffix", "");
        this.config.addDefault("markercolor", "&7");
        this.config.addDefault("quote", "");
        this.config.addDefault("color", "&f");
        this.config.options().copyDefaults(true);
        this.save();
    }

    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }

        return this.config;
    }

    private void reloadConfig() {
        if (this.file == null) {
            this.file = new File(this.path, this.uuid.toString().replaceAll("-", "") + ".yml");
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        } catch (IOException var2) {
            Util.logException(var2, this.uuid, "&cYour player file could not be saved. Contact an administrator.", "An IOException occurred while trying to save the player file of the following UUID: " + this.uuid);
            return false;
        }
    }
}