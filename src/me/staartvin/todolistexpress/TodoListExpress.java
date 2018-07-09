package me.staartvin.todolistexpress;

import me.staartvin.todolistexpress.commands.manager.CommandsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TodoListExpress extends JavaPlugin {

    private CommandsManager commandsManager;

    public void onEnable() {

        // Create a commands manager to handle commands.
        this.setCommandsManager(new CommandsManager(this));

        // Set executor of /todolist to commands manager so it handles all commands
        this.getCommand("todolist").setExecutor(getCommandsManager());

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
}
