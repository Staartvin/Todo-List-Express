package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * This command allows a player to create a todo list.
 */
public class CreateCommand extends TodoListCommand {

    public CreateCommand(TodoListExpress plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        // Check for permissions
        if (!this.hasPermission(getPermission(), sender)) {
            return true;
        }

        sender.sendMessage("Creating a new list!");

        return true;
    }

    @Override
    public String getDescription() {
        return "Create a new todo list";
    }

    @Override
    public String getPermission() {
        return "todolistexpress.create";
    }

    @Override
    public String getUsage() {
        return "/todolist create <name>";
    }
}
