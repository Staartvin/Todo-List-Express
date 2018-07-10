package me.staartvin.todolistexpress.todolists.types;

import java.util.*;

public class TodoList {

    // Store all the todos of this list
    private List<Todo> todos = new ArrayList<>();

    // Player that created the list
    private TodoListPlayer owner;

    // Players that are in some way related to this list.
    // We store a list of permissions each player is able to perform on this list.
    private Map<TodoListPlayer, List<ListPermission>> relatedPlayers = new HashMap<>();

    // Name of the todo list
    private String name;

    // Description of the todo list
    private String description = "No description";

    // The accessibility type of this todo list.
    // By default it is private.
    private TodoListType type = TodoListType.PRIVATE;

    public TodoList() {
    }

    public TodoList(TodoListPlayer player, String name) {
        this.setName(name);
        this.setOwner(player);
    }

    public TodoList(TodoListPlayer player, String name, String description) {
        this(player, name);

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
     * @return {@link TodoListPlayer} of the creator
     */
    public TodoListPlayer getOwner() {
        return owner;
    }

    /**
     * Set the player that created this list
     *
     * @param owner {@link TodoListPlayer} of the creator
     */
    public void setOwner(TodoListPlayer owner) {
        this.owner = owner;
    }

    /**
     * Get a list of players that are related to this list
     *
     * @return list of {@link TodoListPlayer} objects
     */
    public List<TodoListPlayer> getRelatedPlayers() {
        return new ArrayList<>(relatedPlayers.keySet());
    }

    /**
     * Add a permission to a player that is related to this list
     *
     * @param player     {@link TodoListPlayer} Player
     * @param permission Permission to add
     */
    public void addPlayerPermission(TodoListPlayer player, ListPermission permission) {
        List<ListPermission> permissions = getPlayerPermissions(player.getUUID());

        // Check if the permission is already defined for the player
        if (permissions.contains(permission)) {
            return;
        }

        // If not, add the permission to the list
        permissions.add(permission);

        // Update the list
        this.relatedPlayers.put(player, permissions);
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
        if (this.getOwner().getUUID().equals(uuid)) {
            permissions.addAll(Arrays.asList(ListPermission.values()));
        }

        // If the hashmap contains the uuid, return the permissions of the player.
        else if (this.relatedPlayers.containsKey(new TodoListPlayer(uuid))) {
            permissions = this.relatedPlayers.get(new TodoListPlayer(uuid));
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

    /**
     * Get the type of this todo list
     *
     * @return type of todo list
     */
    public TodoListType getType() {
        return type;
    }

    /**
     * Set the type of this todo list.
     *
     * @param type Type to set it to.
     */
    public void setType(TodoListType type) {
        this.type = type;
    }
}
