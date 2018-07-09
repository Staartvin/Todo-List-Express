package me.staartvin.todolistexpress.todolists;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.todolists.types.TodoList;

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
    }

    /**
     * Load all todo lists that are stored into memory.
     */
    public void loadTodoLists() {

    }

    /**
     * Get the todo list matching the given name
     *
     * @param todoListName Name of the todo list
     */
    public Optional<TodoList> getTodoList(String todoListName) {
        return todoLists.stream().filter(todoList -> todoList.getName().equalsIgnoreCase(todoListName)).findFirst();
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
}
