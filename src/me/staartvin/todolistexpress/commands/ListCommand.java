package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import me.staartvin.todolistexpress.todolists.types.TodoList;
import me.staartvin.todolistexpress.todolists.types.TodoListType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command allows a player to see their lists or all public lists
 */
public class ListCommand extends TodoListCommand {

    public ListCommand(TodoListExpress plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        boolean ownLists = true;

        // Player specified that he wants to see their own lists
        if (args.length < 2) {

            // Check for permissions
            if (!this.hasPermission(getPermission(), sender)) {
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Unfortunately you do not have any lists!");
                return true;
            }

        } else if (args[1].equalsIgnoreCase("public")) {

            // Check for permissions
            if (!this.hasPermission("todolistexpress.list.other", sender)) {
                return true;
            }

            // Show all lists that are public
            ownLists = false;
        } else {
            sender.sendMessage(ChatColor.RED + "I'm not sure what you mean by that command. Please try again.");
            return true;
        }

        List<TodoList> todoLists = new ArrayList<>();
        String message = "";

        if (ownLists) {
            todoLists = plugin.getTodoListManager().getRelevantTodoLists(((Player) sender).getUniqueId());

            if (todoLists.isEmpty()) {
                message = ChatColor.RED + "You have no relevant todo lists.";
            } else {
                message = ChatColor.GREEN + "Your relevant todo lists:";
            }


        } else {
            todoLists = plugin.getTodoListManager().getAllTodoLists().stream().filter(todoList -> todoList.getType()
                    .equals(TodoListType.PUBLIC)).collect(Collectors.toList());

            if (todoLists.isEmpty()) {
                message = ChatColor.RED + "There are no public todo lists.";
            } else {
                message = ChatColor.GREEN + "All public todo lists:";
            }
        }

        // Send header message
        sender.sendMessage(message);

        // Send sender all lists that are relevant
        for (int i = 0; i < todoLists.size(); i++) {
            sender.sendMessage(ChatColor.GRAY + "" + (i + 1) + ". " + ChatColor.GOLD + todoLists.get(i).getName());
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Show all todo lists";
    }

    @Override
    public String getPermission() {
        return "todolistexpress.list.self";
    }

    @Override
    public String getUsage() {
        return "/todolist list (public)";
    }
}
