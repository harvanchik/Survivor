package me.harvanchik.survivor.tribe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * A class to manage tribes.
 * @author harvanchik
 * @since 04-27-2023
 */
public class TribeManager {

    private static final Set<Tribe> tribes = new HashSet<>(); // set of tribes

    // private static final TeamManager teamManager = TabAPI.getInstance().getTeamManager(); // tab team manager
    private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard(); // main scoreboard

    /**
     * Add a tribe to the set of tribes.
     * @param tribe the tribe to add
     */
    public static void addTribe(@NotNull final Tribe tribe) {
        // add the tribe to the set
        tribes.add(tribe);
        // create team
        Team team = scoreboard.registerNewTeam(tribe.getName());
        // set tribe's team
        tribe.setTeam(team);
        // set team prefix
        team.prefix(Component.text("[" + tribe.getName() + "] ", TextColor.fromHexString(tribe.getColor())));
    }

    /**
     * Remove a tribe from the set of tribes.
     * @param tribe the tribe to remove
     */
    public static void removeTribe(@NotNull final Tribe tribe) {
        // remove the team
        tribe.getTeam().unregister();
        // remove the tribe from the set
        tribes.remove(tribe);
        // TODO: tribe not being removed from the set, it's not found in the set
    }

    /**
     * Get a {@link Tribe} by name.
     * @param name the name of the tribe
     * @return the tribe
     */
    @Nullable
    public static Tribe getTribe(@NotNull final String name) {
        return tribes.stream().filter(tribe -> tribe.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
