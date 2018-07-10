package me.staartvin.todolistexpress;

import me.staartvin.todolistexpress.commands.manager.CommandsManager;
import me.staartvin.todolistexpress.storage.StorageManager;
import me.staartvin.todolistexpress.storage.handlers.MongoDb.MongoStorageHandler;
import me.staartvin.todolistexpress.storage.tasks.UpdateStorageHandlerTask;
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

        // Set MongoDb handler as storage handler
        this.getStorageManager().setStorageHandler(new MongoStorageHandler(this));

        // Load storage handler so it establishes a connection.
        this.getStorageManager().getStorageHandler().loadStorageHandler();

        // If storage manager is ready, load todo list manager
        if (this.getStorageManager().getStorageHandler().isReady()) {
            this.getTodoListManager().loadTodoLists(this.getStorageManager().getStorageHandler());
            this.getLogger().info("Loaded todo lists that are stored.");
        } else {
            this.getLogger().severe("Could not connect to storage handler! Something is severely wrong!");
        }

        // Run a task that updates the storage with what's in memory every minute.
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, new UpdateStorageHandlerTask(this), 20 * 60,
                20 * 60);

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
