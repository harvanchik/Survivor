package me.harvanchik.survivor;

import org.bukkit.plugin.java.JavaPlugin;

public final class Survivor extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Survivor plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Survivor plugin disabled!");
    }
}
