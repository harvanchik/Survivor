package me.harvanchik.survivor.tribe;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scoreboard.Team;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The tribe class.
 * @author harvanchik
 * @since 04-26-2023
 */
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true) @Builder
public class Tribe {

    @Include private UUID id; // tribe id (also used as team name)
    private String name; // tribe name
    private String color; // tribe color
    private Location spawn; // tribe's camp spawn
    @Default private Set<UUID> members = new HashSet<>(); // members of the tribe
    private Team team; // scoreboard team

    private UUID creator; // admin who created the tribe
    private Timestamp createdAt; // date when the tribe was created
    private Timestamp modifiedAt; // date when the tribe was last modified

    /**
     * Add a member to the tribe.
     * @param uuid the uuid of the member to add
     */
    public void addMember(UUID uuid) {
        // add the member to the set
        members.add(uuid);
        // add member to the team
        team.addPlayer(Bukkit.getOfflinePlayer(uuid));
    }

    /**
     * Remove a member from the tribe.
     * @param uuid the uuid of the member to remove
     */
    public void removeMember(UUID uuid) {
        // remove the member from the set
        members.remove(uuid);
        // remove member from the team
        team.removePlayer(Bukkit.getOfflinePlayer(uuid));
    }

    /**
     * Update the tribe team. This updates the team prefix.
     */
    public void updateTeam() {
        // set team prefix
        team.prefix(Component.text("[" + name + "] ", TextColor.fromHexString(color)));
    }
}
