package me.staartvin.todolistexpress;

import me.staartvin.todolistexpress.commands.manager.CommandsManager;
import me.staartvin.todolistexpress.storage.StorageManager;
import me.staartvin.todolistexpress.todolists.TodoListManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TodoListExpress extends JavaPlugin {

    private CommandsManager commandsManager;
    private TodoListManager todoListManager;
    private StorageManager storageManager;

    public void onEnable() {

        // Create a commands manager to handle commands.
        this.setCommandsManager(new CommandsManager(this));

        // Set executor of /todolist to commands manager so it handles all commands
        this.getCommand("todolist").setExecutor(getCommandsManager());

        // Create a todo manager that keeps track of all lists
        this.setTodoListManager(new TodoListManager(this));

        // Create a storage manager that is used to store the lists
        this.setStorageManager(new StorageManager(this));

        // TODO: Set storage handler of storage manager

        this.getLogger().info(this.getDescription().getFullName() + " has been enabled!");
    }

    public void onDisable() {
        this.getLogger().info(this.getDescription().getFullName() + " has been disabled!");
    }

    public CommandsManager getCommandsManager() {
        return commandsManager;
    }

    public void setCommandsManager(CommandsManager commandsManager) {
        this.commandsManager = commandsManager;
    }

    public TodoListManager getTodoListManager() {
        return todoListManager;
    }

    public void setTodoListManager(TodoListManager todoListManager) {
        this.todoListManager = todoListManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public void setStorageManager(StorageManager storageManager) {
        this.storageManager = storageManager;
    }
}
