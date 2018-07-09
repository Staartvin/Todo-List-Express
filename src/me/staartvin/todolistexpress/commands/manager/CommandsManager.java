package me.staartvin.todolistexpress.commands.manager;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.commands.CreateCommand;
import me.staartvin.todolistexpress.commands.HelpCommand;
import me.staartvin.todolistexpress.commands.SelectCommand;
import me.staartvin.todolistexpress.todolists.types.TodoList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class will manage all incoming command requests. Commands are not
 * performed here, they are only send to the correct place. A specific
 * {@linkplain TodoListCommand} class handles the task of performing the
 * command.
 *
 * <br>
 * <br>
 * Commands are stored in a hashmap. The key of this hashmap is a list of
 * strings. These strings represent what text you should enter to perform this
 * command.
 */
public class CommandsManager implements TabExecutor {

    private final TodoListExpress plugin;

    // Use linked hashmap so that input order is kept
    private final Map<List<String>, TodoListCommand> registeredCommands = new LinkedHashMap<>();

    // Track what list a player has selected
    private Map<UUID, String> selectedTodoList = new HashMap<>();


    /**
     * All command aliases are set up in here.
     */
    public CommandsManager(final TodoListExpress plugin) {
        this.plugin = plugin;

        // Register command classes
        registeredCommands.put(Arrays.asList("create"), new CreateCommand(plugin));
        registeredCommands.put(Arrays.asList("help"), new HelpCommand(plugin));
        registeredCommands.put(Arrays.asList("select"), new SelectCommand(plugin));

        plugin.getLogger().info("Loaded all commands");
    }

    /**
     * Find the closest suggestion for a given string and a given list of strings.
     *
     * @param input String to compare
     * @param list  List of strings to find the closest suggestion into it.
     * @return closest string to the input string in the given list.
     */
    public static String findClosestSuggestion(String input, Collection<String> list) {
        int lowestDistance = Integer.MAX_VALUE;
        String bestSuggestion = null;

        for (String possibility : list) {
            int dist = editDistance(input, possibility);

            if (dist < lowestDistance) {
                lowestDistance = dist;
                bestSuggestion = possibility;
            }
        }

        return bestSuggestion + ";" + lowestDistance;
    }

    /**
     * Calculates the edit distance of two strings (Levenshtein distance)
     *
     * @param a First string to compare
     * @param b Second string to compate
     * @return Levenshtein distance of two strings.
     */
    private static int editDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    /**
     * Create a string that shows all elements of the given list <br>
     * The end divider is the last word used for the second last element. <br>
     * Example: a list with {1,2,3,4,5,6,7,8,9,0} and end divider 'or'. <br>
     * Would show: 1, 2, 3, 4, 5, 6, 7, 8, 9 or 0.
     *
     * @param c          Array to get the elements from.
     * @param endDivider Last word used for dividing the second last and last word.
     * @return string with all elements.
     */
    public static String seperateList(final Collection<?> c, final String endDivider) {
        final Object[] array = c.toArray();
        if (array.length == 1)
            return array[0].toString();

        if (array.length == 0)
            return null;

        final StringBuilder string = new StringBuilder();

        for (int i = 0; i < array.length; i++) {

            if (i == (array.length - 1)) {
                string.append(array[i]);
            } else if (i == (array.length - 2)) {
                // Second last
                string.append(array[i]).append(" ").append(endDivider).append(" ");
            } else {
                string.append(array[i] + ", ");
            }
        }

        return string.toString();
    }

    /**
     * Get a hashmap of commands that are used. For more info, see
     * {@link CommandsManager}.
     *
     * @return a hashmap of commands
     */
    public Map<List<String>, TodoListCommand> getRegisteredCommands() {
        return registeredCommands;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.
     * CommandSender, org.bukkit.command.Command, java.lang.String,
     * java.lang.String[])
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.BLUE + "-----------------------------------------------------");
            sender.sendMessage(
                    ChatColor.GOLD + "Developed by: " + ChatColor.GRAY + plugin.getDescription().getAuthors());
            sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GRAY + plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.YELLOW + "Type /todolist help for a list of commands.");
            return true;
        }

        final String action = args[0];

        List<String> suggestions = new ArrayList<>();
        List<String> bestSuggestions = new ArrayList<>();

        // Go through every list and check if that action is in there.
        for (final Entry<List<String>, TodoListCommand> entry : registeredCommands.entrySet()) {

            String suggestion = findClosestSuggestion(action, entry.getKey());

            if (suggestion != null) {
                suggestions.add(suggestion);
            }

            for (final String actionString : entry.getKey()) {

                if (actionString.equalsIgnoreCase(action)) {
                    return entry.getValue().onCommand(sender, cmd, label, args);
                }
            }
        }

        // Search for suggestions if argument was not found
        for (String suggestion : suggestions) {
            String[] split = suggestion.split(";");

            int editDistance = Integer.parseInt(split[1]);

            // Only give suggestion if edit distance is small
            if (editDistance <= 2) {
                bestSuggestions.add(split[0]);
            }
        }

        sender.sendMessage(ChatColor.RED + "Command not recognised!");

        if (!bestSuggestions.isEmpty()) {

            sender.sendMessage(ChatColor.DARK_AQUA + "Did you perhaps mean " + ChatColor.GREEN + "/todolist "
                    + seperateList(bestSuggestions, "or") + ChatColor.DARK_AQUA + "?");

        }

        sender.sendMessage(ChatColor.YELLOW + "Use '/todolist help' for a list of commands.");
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.bukkit.command.TabCompleter#onTabComplete(org.bukkit.command.
     * CommandSender, org.bukkit.command.Command, java.lang.String,
     * java.lang.String[])
     */
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String commandLabel,
                                      final String[] args) {

        // Show a list of commands that match the characters already typed (if any).
        if (args.length <= 1) {
            final List<String> commands = new ArrayList<String>();

            for (final Entry<List<String>, TodoListCommand> entry : registeredCommands.entrySet()) {
                final List<String> list = entry.getKey();

                commands.addAll(list);
            }

            return findSuggestedCommands(commands, args[0]);
        }

        final String subCommand = args[0].trim();

        // Return on tab complete of sub command
        for (final Entry<List<String>, TodoListCommand> entry : registeredCommands.entrySet()) {

            for (final String alias : entry.getKey()) {
                if (subCommand.trim().equalsIgnoreCase(alias)) {
                    return entry.getValue().onTabComplete(sender, cmd, commandLabel, args);
                }
            }

        }

        return null;
    }

    /***
     * Returns a sublist from a given list containing items that start with the given string if string is not empty
     * @param list The list to process
     * @param string The typed string
     * @return Sublist if string is not empty
     */

    private List<String> findSuggestedCommands(List<String> list, String string) {
        if (string.equals("")) return list;

        List<String> returnList = new ArrayList<>();
        for (String item : list) {
            if (item.toLowerCase().startsWith(string.toLowerCase())) {
                returnList.add(item);
            }
        }
        return returnList;
    }

    /**
     * Get the todo list that a player has selected. Will return null if the player has not selected a todo list or
     * if the list does not exist anymore.
     *
     * @param uuid UUID of the player
     * @return the selected {@link TodoList} object.
     */
    public Optional<TodoList> getSelectedTodoList(UUID uuid) {
        if (!this.selectedTodoList.containsKey(uuid)) {
            return Optional.empty();
        }

        String selectedTodoListName = this.selectedTodoList.get(uuid);

        if (selectedTodoListName == null) {
            return Optional.empty();
        }

        return plugin.getTodoListManager().getTodoList(selectedTodoListName);
    }

    /**
     * Set the todo list that a player has selected.
     *
     * @param uuid UUID of the player
     * @param name Name of the todo list the player has selected
     */
    public void setSelectedTodoList(UUID uuid, String name) {
        this.selectedTodoList.put(uuid, name);
    }

    /**
     * Check whether a player has selected a todo list.
     *
     * @param uuid UUID of the player
     * @return true if the player has selected a valid todo list, false otherwise.
     */
    public boolean hasSelectedTodoList(UUID uuid) {
        return this.getSelectedTodoList(uuid).isPresent();
    }

}
