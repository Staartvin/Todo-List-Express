package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import me.staartvin.todolistexpress.todolists.types.TodoList;
import me.staartvin.todolistexpress.util.TodoListUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This command allows a player to select a todo list.
 */
public class SelectCommand extends TodoListCommand {

    public SelectCommand(TodoListExpress plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        // Check for permissions
        if (!this.hasPermission(getPermission(), sender)) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Unfortunately, only players can select todo lists.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "You must provide a name for the todo list.");
            return true;
        }

        // Get the name of the list
        String name = TodoListUtils.getStringFromArgs(args, 1);

        TodoList selectedTodoList = plugin.getTodoListManager().getTodoList(name, true).orElse(null);

        // If there already exists a todo list with the given name, abort the mission.
        if (selectedTodoList == null) {
            sender.sendMessage(ChatColor.RED +
                    "There is no todo list named '" + ChatColor.GOLD + name + ChatColor.RED + "'.");
            return true;
        }

        UUID uuid = ((Player) sender).getUniqueId();


        // Check if the player is related to this todo list
        if (!selectedTodoList.isPlayerRelated(uuid)) {
            sender.sendMessage(ChatColor.RED + "You are not allowed to select this todo list because you are not " +
                    "related to this todo list.");
            return true;
        }

        // Set this todo list as selected.
        this.plugin.getCommandsManager().setSelectedTodoList(uuid, selectedTodoList.getName());

        sender.sendMessage(ChatColor.GREEN + "Selected '" + ChatColor.GOLD + selectedTodoList
                .getName() + ChatColor.GREEN + "'.");

        return true;
    }

    @Override
    public String getDescription() {
        return "Select a todo list";
    }

    @Override
    public String getPermission() {
        return "todolistexpress.select";
    }

    @Override
    public String getUsage() {
        return "/todolist select <name>";
    }
}
