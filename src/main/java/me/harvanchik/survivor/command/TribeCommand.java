package me.harvanchik.survivor.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.harvanchik.survivor.CommandManager;
import me.harvanchik.survivor.tribe.Tribe;
import me.harvanchik.survivor.tribe.TribeManager;
import me.harvanchik.survivor.util.Util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * The command to manage tribes, members, etc.
 * @author harvanchik
 * @since 04-28-2023
 */
@CommandAlias("tribe")
public class TribeCommand extends BaseCommand {

    public TribeCommand() {
        // register tribe command completions
        CommandManager.getCm().getCommandCompletions().registerAsyncCompletion("tribes", c -> TribeManager.getTribes().stream().map(Tribe::getName).toList());
    }

    @Subcommand("create")
    @Description("Create a new tribe.")
    public void create(CommandSender sender, String name, @Optional @Single String color) {
        // check if tribe name is taken
        if (TribeManager.getTribe(name) != null) {
            sender.sendMessage("A tribe with that name already exists.");
            return;
        }
        // get creator uuid
        UUID creator = sender instanceof Player player ? player.getUniqueId() : null;
        // get the tribe spawn location
        Location spawn = sender instanceof Player player ? player.getLocation() : null;
        // get current timestamp
        Timestamp now = Util.now();
        // build the tribe
        Tribe tribe = Tribe.builder()
            .id(Util.uuid())
            .name(name)
            .color(color != null ? color : "#ffffff")
            .spawn(spawn)
            .creator(creator)
            .createdAt(now)
            .modifiedAt(now)
            .build();
        // add the tribe to the tribe manager
        TribeManager.addTribe(tribe);
        sender.sendMessage("created tribe " + name);
        // if no spawn set, prompt sender to set spawn with modify command
        if (spawn == null) {
            sender.sendMessage("You must set the tribe spawn with /tribe modify spawn <tribe> [x] [y] [z] [yaw] [pitch]");
        }
    }

    @Subcommand("disband")
    @Description("Remove a tribe.")
    @CommandCompletion("@tribes")
    public void disband(CommandSender sender, Tribe tribe) {
        // remove the tribe from the tribe manager
        TribeManager.removeTribe(tribe);
        sender.sendMessage("disbanded tribe " + tribe.getName());
    }

    @Subcommand("join")
    @Description("Add a member to a tribe.")
    @CommandCompletion("@tribes")
    public void join(CommandSender sender, Tribe tribe, @Optional @Single Player target) {
        // if command is targeting sender (as player)
        if (sender instanceof Player player && target == null) target = player;
        // if sender is console and no target is specified, stop here
        if (target == null) return;
        // add player to tribe
        tribe.addMember(target.getUniqueId());
        sender.sendMessage("added to tribe " + tribe.getName());
    }

    @Subcommand("leave")
    @Description("Remove a member from a tribe.")
    @CommandCompletion("@tribes")
    public void leave(CommandSender sender, @Optional Tribe tribe, @Optional @Single Player target) {
        // if command is targeting sender (as player)
        if (sender instanceof Player player && target == null) target = player;
        // if sender is console and no target is specified, stop here
        if (target == null) return;
        // if tribe is not specified
        if (tribe == null) {
            // get the tribe the player is in
            tribe = TribeManager.getTribe(target.getUniqueId());
            // if player is not in a tribe
            if (tribe == null) {
                // send error message
                sender.sendMessage("You are not in a tribe."); return;
            }
        }
        // remove player from tribe
        tribe.removeMember(target.getUniqueId());
        sender.sendMessage("removed from tribe " + tribe.getName());
    }

    @Subcommand("modify name")
    @Description("Modify a tribe's name.")
    @CommandCompletion("@tribes @nothing")
    public void modifyName(CommandSender sender, Tribe tribe, @Single String name) {
        // check if tribe name has not changed
        if (tribe.getName().equals(name)) {
            sender.sendMessage("Tribe name is already " + name);
            return;
        }
        // check if tribe name is taken
        if (TribeManager.getTribe(name) != null) {
            sender.sendMessage("A tribe with that name already exists.");
            return;
        }
        // set the tribe name
        tribe.setName(name);
        // update tribe team
        tribe.update(sender instanceof Player player ? player.getUniqueId() : null);
        sender.sendMessage("set tribe name to " + name);
    }

    @Subcommand("modify color")
    @Description("Modify a tribe's color.")
    @CommandCompletion("@tribes @nothing")
    public void modifyColor(CommandSender sender, Tribe tribe, @Single String color) {
        // check if tribe color has not changed
        if (tribe.getColor().equalsIgnoreCase(color)) {
            sender.sendMessage("Tribe color is already " + color);
            return;
        }
        // set the tribe color
        tribe.setColor(color);
        // update tribe team
        tribe.update(sender instanceof Player player ? player.getUniqueId() : null);
        sender.sendMessage("set tribe color to " + color);
    }
}
