package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import me.staartvin.todolistexpress.todolists.types.ListPermission;
import me.staartvin.todolistexpress.todolists.types.Todo;
import me.staartvin.todolistexpress.todolists.types.TodoList;
import me.staartvin.todolistexpress.todolists.types.TodoListPlayer;
import me.staartvin.todolistexpress.util.TodoListUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This command allows a player to add a todo to a todo list
 */
public class AddCommand extends TodoListCommand {

    public AddCommand(TodoListExpress plugin) {
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

        // Check if this player can add todo's to this list.
        if (!selectedTodoList.hasPermission(uuid, ListPermission.ADD_TODO)) {
            sender.sendMessage(ChatColor.RED + "You are not allowed to add a todo to this list.");
            return true;
        }

        // Get the name of the todo
        String todoDescription = TodoListUtils.getStringFromArgs(args, 1);

        // Check if the todo is not empty
        if (todoDescription.trim().length() == 0) {
            sender.sendMessage(ChatColor.RED + "That todo seems a little empty, doesn't it?");
            return true;
        }

        // Check if the given todo already exists.
        if (selectedTodoList.getTodos().stream().anyMatch(todo -> todo.getName().equalsIgnoreCase(todoDescription))) {
            sender.sendMessage(ChatColor.RED + "There already is a todo for that!");
            return true;
        }

        // Create todo
        Todo todo = new Todo();
        // And initialise it
        todo.setName(todoDescription);
        todo.setDescription(todoDescription);
        todo.setOwner(new TodoListPlayer(uuid, sender.getName()));

        // Add todo to the list
        selectedTodoList.addTodo(todo);

        sender.sendMessage(ChatColor.GREEN + "Added todo '" + ChatColor.GOLD + todoDescription + ChatColor.GREEN + "'" +
                ".");

        return true;
    }

    @Override
    public String getDescription() {
        return "Add a todo to a todo list";
    }

    @Override
    public String getPermission() {
        return "todolistexpress.add";
    }

    @Override
    public String getUsage() {
        return "/todolist add <description>";
    }
}
