package me.staartvin.todolistexpress.todolists.types;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Todo {

    private String name;

    private String description;

    private UUID creator;

    private List<UUID> assignedPlayers = new ArrayList<>();

    /**
     * Get the name of this todo
     *
     * @return name of todo
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this todo
     *
     * @param name Name of the todo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the full description of this todo
     *
     * @return description of todo
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this todo
     *
     * @param description String description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the player that created the todo
     *
     * @return UUID of the creator
     */
    public UUID getCreator() {
        return creator;
    }

    /**
     * Set the player that created the todo
     *
     * @param creator UUID of the creator
     */
    public void setCreator(UUID creator) {
        this.creator = creator;
    }

    /**
     * Get all players that are assigned to this todo
     *
     * @return a list of UUIDs representing the assigned players
     */
    public List<UUID> getAssignedPlayers() {
        return assignedPlayers;
    }

    /**
     * Set all players that are assigned to this todo
     *
     * @param assignedPlayers a list of UUIDs.
     */
    public void setAssignedPlayers(List<UUID> assignedPlayers) {
        this.assignedPlayers = assignedPlayers;
    }

    /**
     * Set a player as assigned to this todo
     *
     * @param assignedPlayer UUID of the player
     */
    public void addAssignedPlayer(UUID assignedPlayer) {

        if (assignedPlayers.contains(assignedPlayer)) {
            return;
        }

        this.assignedPlayers.add(assignedPlayer);
    }

    /**
     * Remove an assigned player from this todo.
     *
     * @param assignedPlayer UUID of the player
     */
    public void removeAssignedPlayer(UUID assignedPlayer) {
        this.assignedPlayers.remove(assignedPlayer);
    }
}
