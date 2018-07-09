package me.staartvin.todolistexpress.util;

public class TodoListUtils {

    /**
     * Create a string based on an array of arguments.
     *
     * @param args     Arguments
     * @param startArg The start index of the arguments array to start at.
     * @return a single string containing all words in the arguments, joined by spaces.
     */
    public static String getStringFromArgs(final String[] args, final int startArg) {
        final StringBuilder string = new StringBuilder();

        for (int i = startArg; i < args.length; i++) {

            if (i == startArg) {
                string.append(args[i]);
            } else {
                string.append(" ").append(args[i]);
            }
        }

        return string.toString();
    }
}
