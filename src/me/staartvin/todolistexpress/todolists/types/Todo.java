package me.staartvin.todolistexpress.todolists.types;

import java.util.ArrayList;
import java.util.List;

public class Todo {

    private String name;

    private String description;

    private TodoListPlayer owner;

    private List<TodoListPlayer> assignedPlayers = new ArrayList<>();

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
     * @return TodoListPlayer Owner
     */
    public TodoListPlayer getOwner() {
        return owner;
    }

    /**
     * Set the player that created the todo
     *
     * @param owner TodoListPlayer Owner
     */
    public void setOwner(TodoListPlayer owner) {
        this.owner = owner;
    }

    /**
     * Get all players that are assigned to this todo
     *
     * @return a list of {@link TodoListPlayer} representing the assigned players
     */
    public List<TodoListPlayer> getAssignedPlayers() {
        return assignedPlayers;
    }

    /**
     * Set all players that are assigned to this todo
     *
     * @param assignedPlayers a list of {@link TodoListPlayer}.
     */
    public void setAssignedPlayers(List<TodoListPlayer> assignedPlayers) {
        this.assignedPlayers = assignedPlayers;
    }

    /**
     * Set a player as assigned to this todo
     *
     * @param assignedPlayer {@link TodoListPlayer} of the player
     */
    public void addAssignedPlayer(TodoListPlayer assignedPlayer) {

        if (assignedPlayers.contains(assignedPlayer)) {
            return;
        }

        this.assignedPlayers.add(assignedPlayer);
    }

    /**
     * Remove an assigned player from this todo.
     *
     * @param assignedPlayer {@link TodoListPlayer} of the player
     */
    public void removeAssignedPlayer(TodoListPlayer assignedPlayer) {
        this.assignedPlayers.remove(assignedPlayer);
    }
}
