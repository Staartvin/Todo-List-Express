package me.staartvin.todolistexpress.commands;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.manager.TodoListCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command shows all commands a player can perform.
 */
public class HelpCommand extends TodoListCommand {

    public HelpCommand(final TodoListExpress instance) {
        super(instance);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (args.length == 1) {
            showHelpPages(sender, 1);
        } else {
            int page;
            try {
                page = Integer.parseInt(args[1]);
            } catch (final Exception e) {
                sender.sendMessage(ChatColor.RED + args[1] + " is an invalid number.");
                return true;
            }
            showHelpPages(sender, page);
        }
        return true;
    }

    private void showHelpPages(final CommandSender sender, int page) {
        List<TodoListCommand> commands = new ArrayList<TodoListCommand>(
                plugin.getCommandsManager().getRegisteredCommands().values());

        // If the sender is not op, only show the commands that player is able to perform.
        if (!sender.isOp()) {
            commands = commands.stream().filter(command -> command.hasPermission(command.getPermission(), sender))
                    .collect(Collectors.toList());
        }

        final int listSize = commands.size();

        // Don't show more than 6 commands per page
        // (Does she want the D?)
        final int maxPages = (int) Math.ceil(listSize / 6D);

        if (page > maxPages || page == 0)
            page = maxPages;

        int start = 0;
        int end = 6;

        if (page != 1) {
            final int pageDifference = page - 1;

            // Because we need 7, not 6.
            start += 1;

            start += (6 * pageDifference);
            end = start + 6;
        }

        sender.sendMessage(ChatColor.GREEN + "-- Todolist Express Commands --");

        for (int i = start; i < end; i++) {
            // Can't go any further
            if (i >= listSize)
                break;

            final TodoListCommand command = commands.get(i);

            sender.sendMessage(ChatColor.AQUA + command.getUsage() + ChatColor.GRAY + " - " + command.getDescription());
        }

        sender.sendMessage(ChatColor.BLUE + "Page " + page + " of " + maxPages);
    }

    @Override
    public String getDescription() {
        return "Show a list of commands.";
    }

    @Override
    public String getPermission() {
        return "todolistexpress.help";
    }

    @Override
    public String getUsage() {
        return "/todolist help <page>";
    }
}
