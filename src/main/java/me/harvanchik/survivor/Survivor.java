package me.harvanchik.survivor;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Survivor extends JavaPlugin {

    @Getter static Survivor instance; // the plugin instance

    @Override
    public void onEnable() {
        // set the plugin instance
        instance = this;
        // register commands, contexts, and completions
        CommandManager.init(this);
        // log that plugin is enabled
        getLogger().info("Survivor plugin enabled!");
    }
}
