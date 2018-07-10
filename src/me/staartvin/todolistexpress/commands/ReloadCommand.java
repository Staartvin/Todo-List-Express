package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * This command allows a player to reload the data from the storage
 */
public class ReloadCommand extends TodoListCommand {

    public ReloadCommand(TodoListExpress plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        // Check for permissions
        if (!this.hasPermission(getPermission(), sender)) {
            return true;
        }

        // Reload todo lists
        plugin.getTodoListManager().loadTodoLists(plugin.getStorageManager().getStorageHandler());

        sender.sendMessage(ChatColor.GREEN + "Reloaded todo lists from storage.");

        return true;
    }

    @Override
    public String getDescription() {
        return "Reload todo lists from storage";
    }

    @Override
    public String getPermission() {
        return "todolistexpress.reload";
    }

    @Override
    public String getUsage() {
        return "/todolist reload";
    }
}
