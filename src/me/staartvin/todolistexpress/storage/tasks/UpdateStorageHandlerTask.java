package me.staartvin.todolistexpress.storage.tasks;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.todolists.types.TodoList;

public class UpdateStorageHandlerTask implements Runnable {

    private TodoListExpress plugin;

    public UpdateStorageHandlerTask(TodoListExpress instance) {
        this.plugin = instance;
    }

    @Override
    public void run() {
        // For each todo list, save it in the database.
        for (TodoList todoList : plugin.getTodoListManager().getAllTodoLists()) {
            plugin.getLogger().info("Saving todo list " + todoList.getName());
            plugin.getStorageManager().getStorageHandler().saveTodoList(todoList);
        }
    }
}
