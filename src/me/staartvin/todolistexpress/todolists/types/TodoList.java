package me.staartvin.todolistexpress.todolists.types;

import java.util.*;

public class TodoList {

    // Store all the todos of this list
    private List<Todo> todos = new ArrayList<>();

    // Player that created the list
    private UUID owner;

    // Players that are in some way related to this list.
    // We store a list of permissions each player is able to perform on this list.
    private Map<UUID, List<ListPermission>> relatedPlayers = new HashMap<>();

    // Name of the todo list
    private String name;

    // Description of the todo list
    private String description = "No description";

    public TodoList() {
    }

    public TodoList(UUID uuid, String name) {
        this.setName(name);
        this.setOwner(uuid);
    }

    public TodoList(UUID uuid, String name, String description) {
        this(uuid, name);

        this.setDescription(description);
    }

    /**
     * Get the todos in this list
     *
     * @return list of todos
     */
    public List<Todo> getTodos() {
        return todos;
    }

    /**
     * Set the todos in this list
     *
     * @param todos list of todos
     */
    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    /**
     * Add a todo to this list
     *
     * @param todo Todo to add
     */
    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }

    /**
     * Remove a todo from this list
     *
     * @param todo Todo to remove
     */
    public void removeTodo(Todo todo) {
        this.todos.remove(todo);
    }

    /**
     * Get the player that created this list
     *
     * @return UUID of the creator
     */
    public UUID getOwner() {
        return owner;
    }

    /**
     * Set the player that created this list
     *
     * @param owner UUID of the creator
     */
    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    /**
     * Get a list of players that are related to this list
     *
     * @return list of UUIDs
     */
    public List<UUID> getRelatedPlayers() {
        return new ArrayList<>(relatedPlayers.keySet());
    }

    /**
     * Add a permission to a player that is related to this list
     *
     * @param uuid       UUID of the player
     * @param permission Permission to add
     */
    public void addPlayerPermission(UUID uuid, ListPermission permission) {
        List<ListPermission> permissions = getPlayerPermissions(uuid);

        // Check if the permission is already defined for the player
        if (permissions.contains(permission)) {
            return;
        }

        // If not, add the permission to the list
        permissions.add(permission);

        // Update the list
        this.relatedPlayers.put(uuid, permissions);
    }

    /**
     * Get the permissions that a player is able to perform on this list.
     *
     * @param uuid UUID of the player
     * @return List of permissions the player can perform
     */
    public List<ListPermission> getPlayerPermissions(UUID uuid) {
        List<ListPermission> permissions = new ArrayList<>();


        // If the player is the owner of the list, we will give him all permissions.
        if (this.getOwner().equals(uuid)) {
            permissions.addAll(Arrays.asList(ListPermission.values()));
        }

        // If the hashmap contains the uuid, return the permissions of the player.
        else if (this.relatedPlayers.containsKey(uuid)) {
            permissions = this.relatedPlayers.get(uuid);
        }

        // Otherwise, return an empty list.
        return permissions;
    }

    /**
     * Get the name of this todo list
     *
     * @return name of todo list
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this todo list
     *
     * @param name name of todo list
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of this todo list
     *
     * @return description of todo list
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this todo list
     *
     * @param description description of todo list
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Check if a player is related to this list
     *
     * @param uuid UUID of the player to check
     * @return true if the player is related to this list, false otherwise.
     */
    public boolean isPlayerRelated(UUID uuid) {
        return !this.getPlayerPermissions(uuid).isEmpty();
    }
}
