package me.staartvin.todolistexpress.storage.handlers;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.todolists.types.TodoList;

import java.util.List;

public abstract class StorageHandler {

    private TodoListExpress plugin;

    public StorageHandler(TodoListExpress plugin) {
        this.plugin = plugin;
    }

    /**
     * Save a todo list to the storage handler.
     * This will also store all properties of this todo list
     *
     * @param todoList todo list to store
     */
    public abstract void saveTodoList(TodoList todoList);

    /**
     * Remove a todo list from the storage handler.
     *
     * @param todoList todo list to remove
     */
    public abstract void removeTodoList(TodoList todoList);

    /**
     * Get all todo lists that are stored in this storage handler.
     *
     * @return list of todo lists stored
     */
    public abstract List<TodoList> getAllTodoLists();

    /**
     * Load the storage handler so it is ready to store data.
     */
    public abstract void loadStorageHandler();

    /**
     * Check whether this storage handler is ready to store and retrieve data.
     *
     * @return true if it is ready, false if not.
     */
    public abstract boolean isReady();


}
