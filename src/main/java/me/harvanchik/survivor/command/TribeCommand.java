package me.harvanchik.survivor.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
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
            .creator(creator)
            .name(name)
            .color(color != null ? color : "#ffffff")
            .spawn(spawn)
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
    public void disband(CommandSender sender, Tribe tribe) {
        // remove the tribe from the tribe manager
        TribeManager.removeTribe(tribe);
        sender.sendMessage("disbanded tribe " + tribe.getName());
    }

    @Subcommand("join")
    @Description("Add a member to a tribe.")
    public void add(CommandSender sender, Tribe tribe, @Optional @Single Player target) {
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
    public void leave(CommandSender sender, Tribe tribe, @Optional @Single Player target) {
        // if command is targeting sender (as player)
        if (sender instanceof Player player && target == null) target = player;
        // if sender is console and no target is specified, stop here
        if (target == null) return;
        // add player to tribe
        tribe.removeMember(target.getUniqueId());
        sender.sendMessage("removed from tribe " + tribe.getName());
    }

    @Subcommand("modify name")
    @Description("Modify a tribe's name.")
    public void modifyName(CommandSender sender, Tribe tribe, String name) {
        // check if tribe name is taken
        if (TribeManager.getTribe(name) != null) {
            sender.sendMessage("A tribe with that name already exists.");
            return;
        }
        // set the tribe name
        tribe.setName(name);
        // update tribe team
        tribe.updateTeam();
        sender.sendMessage("set tribe name to " + name);
    }

    @Subcommand("modify color")
    @Description("Modify a tribe's color.")
    public void modifyColor(CommandSender sender, Tribe tribe, String color) {
        // set the tribe color
        tribe.setColor(color);
        // update tribe team
        tribe.updateTeam();
        sender.sendMessage("set tribe color to " + color);
    }
}
