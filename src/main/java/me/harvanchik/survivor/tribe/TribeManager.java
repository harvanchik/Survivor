package me.harvanchik.survivor.tribe;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A class to manage tribes.
 * @author harvanchik
 * @since 04-27-2023
 */
public class TribeManager {

    @Getter private static final Set<Tribe> tribes = new HashSet<>(); // set of tribes

    private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard(); // main scoreboard

    /**
     * Add a tribe to the set of tribes.
     * @param tribe the tribe to add
     */
    public static void addTribe(@NotNull final Tribe tribe) {
        // add the tribe to the set
        tribes.add(tribe);
        // create team
        Team team = scoreboard.registerNewTeam(tribe.getId().toString());
        // set tribe's team
        tribe.setTeam(team);
        // update the tribe (sets the prefix)
        tribe.update(tribe.getCreator());
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

    /**
     * Get the {@link Tribe} of a player.
     * @param uuid the uuid of the player
     * @return the tribe
     */
    @Nullable
    public static Tribe getTribe(@NotNull final UUID uuid) {
        return tribes.stream().filter(tribe -> tribe.getMembers().contains(uuid)).findAny().orElse(null);
    }
}
