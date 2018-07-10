package me.staartvin.todolistexpress.todolists;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.storage.handlers.StorageHandler;
import me.staartvin.todolistexpress.todolists.types.TodoList;
import me.staartvin.todolistexpress.todolists.types.TodoListPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TodoListManager {

    private List<TodoList> todoLists = new ArrayList<>();

    private TodoListExpress plugin;

    public TodoListManager(TodoListExpress plugin) {
        this.plugin = plugin;

        plugin.getLogger().info("Loaded todo list manager");
    }

    /**
     * Load all todo lists into memory from the given storage handler
     *
     * @param storageHandler Storage handler to load the todo lists from.
     */
    public void loadTodoLists(StorageHandler storageHandler) {
        // We can't load anything from a null object.
        if (storageHandler == null) {
            return;
        }

        List<TodoList> storedLists = storageHandler.getAllTodoLists();

        if (storedLists != null) {
            // Load todo lists from storage.
            this.todoLists = storedLists;
        }
    }

    /**
     * Get the todo list matching the given name.
     *
     * @param todoListName Name of the todo list
     */
    public Optional<TodoList> getTodoList(String todoListName) {
        return getTodoList(todoListName, false);
    }

    /**
     * Get the todo list matching the given name. If the match beginning parameter is set to true, you don't need to
     * provide the full name, as it will try to guess the rest of the name.
     *
     * @param todoListName   Name to match
     * @param matchBeginning whether to search for a substring or not
     * @return the todo list matching the given name
     */
    public Optional<TodoList> getTodoList(String todoListName, boolean matchBeginning) {

        if (matchBeginning) {
            // Find first todo list that starts with the given name
            return todoLists.stream().filter(todoList -> todoList.getName().toLowerCase().startsWith(todoListName
                    .toLowerCase())).findFirst();
        } else {
            // Find first todo list that matches the exact name.
            return todoLists.stream().filter(todoList -> todoList.getName().equalsIgnoreCase(todoListName)).findFirst();
        }
    }

    /**
     * Get all todo lists that are relevant to the given player.
     * A todo list is relevant to a player when he is either the owner or has permissions set for the list.
     *
     * @param uuid UUID of the player
     * @return a list of {@link TodoList} objects
     */
    public List<TodoList> getRelevantTodoLists(UUID uuid) {
        return todoLists.stream().filter(todoList -> todoList.isPlayerRelated(uuid)).collect(Collectors.toList());
    }

    /**
     * Create a new todo list created by the given player.
     *
     * @param owner Player that created the list
     * @param name Name of the list
     * @return true if the todo list was succesfully created. False otherwise.
     */
    public boolean createTodoList(TodoListPlayer owner, String name) {
        // Don't create a new todo list if it already exists.
        if (getTodoList(name).isPresent()) {
            return false;
        }

        // Create new todo list
        TodoList newTodoList = new TodoList(owner, name);

        // Add it to the lists of todo lists
        todoLists.add(newTodoList);

        // Store the new todo list in storage
        plugin.getStorageManager().getStorageHandler().saveTodoList(newTodoList);

        return true;
    }

    /**
     * Remove all todo lists that match the given name.
     *
     * @param name Name of the todo list to remove
     * @return true if at least one todo list has been removed. False otherwise.
     */
    public boolean removeTodoList(String name) {
        // If there is no todo list with the given name, abort the procedure prematurely.
        if (!getTodoList(name).isPresent()) {
            return false;
        }

        // Remove all todo lists that match the given name.
        todoLists.removeIf(todoList -> todoList.getName().equalsIgnoreCase(name));

        return true;
    }

    /**
     * Get all todo lists that are registered in memory.
     *
     * @return a list of {@link TodoList} objects.
     */
    public List<TodoList> getAllTodoLists() {
        return new ArrayList<>(todoLists);
    }
}
