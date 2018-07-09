package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import me.staartvin.todolistexpress.todolists.types.TodoList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This command allows a player to view info about a todo list
 */
public class InfoCommand extends TodoListCommand {

    public InfoCommand(TodoListExpress plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        // Check for permissions
        if (!this.hasPermission(getPermission(), sender)) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Unfortunately, only players can view info about a todo list.");
            return true;
        }

        UUID uuid = ((Player) sender).getUniqueId();

        // Check if the player has selected a todo list
        if (!plugin.getCommandsManager().hasSelectedTodoList(uuid)) {
            sender.sendMessage(ChatColor.RED + "You have not selected a todo list to view info about.");
            sender.sendMessage(ChatColor.YELLOW + "First select a todo list using /todolist select!");
            return true;
        }

        TodoList selectedTodoList = plugin.getCommandsManager().getSelectedTodoList(uuid).orElse(null);

        // If there already exists a todo list with the given name, abort the mission.
        if (selectedTodoList == null) {
            sender.sendMessage(ChatColor.RED +
                    "The todo list you have selected does not exist anymore!.");
            return true;
        }

        sender.sendMessage(ChatColor.GRAY + "------- [ " + ChatColor.GOLD + selectedTodoList.getName() + ChatColor
                .GRAY + " ] -------");
        sender.sendMessage(ChatColor.GREEN + "Description: " + ChatColor.DARK_AQUA + selectedTodoList.getDescription());
        sender.sendMessage(ChatColor.YELLOW + "Owner: " + ChatColor.GOLD + selectedTodoList.getOwner());

        return true;
    }

    @Override
    public String getDescription() {
        return "See info about a todo list";
    }

    @Override
    public String getPermission() {
        return "todolistexpress.info";
    }

    @Override
    public String getUsage() {
        return "/todolist info";
    }
}
