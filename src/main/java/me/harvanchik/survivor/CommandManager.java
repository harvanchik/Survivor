package me.harvanchik.survivor;

import co.aikar.commands.CommandContexts;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.harvanchik.survivor.command.TribeCommand;
import me.harvanchik.survivor.tribe.Tribe;
import me.harvanchik.survivor.tribe.TribeManager;
import org.jetbrains.annotations.NotNull;

/**
 * Manage and register commands and command completions.
 * @author harvanchik
 * @since 04-28-2023
 */
public class CommandManager {

    @Getter private static PaperCommandManager cm; // the paper command manager

    /**
     * Initialize the command manager. Register the command contexts, commands, and completions.
     * @param instance the plugin instance
     */
    public static void init(@NotNull final Survivor instance) {
        // create the paper command manager
        cm = new PaperCommandManager(instance);
        // register command contexts
        registerContexts();
        // register command completions
        registerCompletions();
        // register commands
        registerCommands();
    }

    /**
     * Register all commands.
     * @param instance the plugin instance
     */
    public static void registerCommands() {
        // register the commands
        cm.registerCommand(new TribeCommand());
    }

    /**
     * Register all command contexts.
     */
    public static void registerContexts() {
        // get command contexts
        CommandContexts<?> contexts = cm.getCommandContexts();
        // register Tribe context (String -> Tribe)
        contexts.registerContext(Tribe.class, c -> TribeManager.getTribe(c.popFirstArg()));
    }

    /**
     * Register all command completions.
     */
    public static void registerCompletions() { }
}
