package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import me.staartvin.todolistexpress.todolists.types.TodoListPlayer;
import me.staartvin.todolistexpress.util.TodoListUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

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

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Unfortunately, only players can create todo lists.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "You must provide a name for the todo list.");
            return true;
        }

        // Get the name of the list
        String name = TodoListUtils.getStringFromArgs(args, 1);

        // If there already exists a todo list with the given name, abort the mission.
        if (plugin.getTodoListManager().getTodoList(name).isPresent()) {
            sender.sendMessage(ChatColor.RED +
                    "There already exists a todo list with the given name.");
            return true;
        }

        UUID uuid = ((Player) sender).getUniqueId();

        // Try to create a todo list
        boolean creationResult = plugin.getTodoListManager().createTodoList(new TodoListPlayer(uuid, sender.getName()
        ), name);

        // Show message to the player indicating success or failure.
        if (!creationResult) {
            sender.sendMessage(ChatColor.RED + "Could not create todo list with the given name. Try again!");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Todo list '" + ChatColor.GOLD + name + ChatColor.GREEN + "' " +
                    "successfully created.");

            // Select the todo list after creation, so that the player does not have to select it manually.
            plugin.getCommandsManager().setSelectedTodoList(uuid, name);
        }

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
