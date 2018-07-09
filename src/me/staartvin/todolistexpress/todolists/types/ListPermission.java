package me.staartvin.todolistexpress.todolists.types;

/**
 * This enum represents permissions that a player can have for a single list
 */
public enum ListPermission {

    /**
     * Add a todo to the list
     */
    ADD_TODO,

    /**
     * Remove a todo from the list
     */
    REMOVE_TODO,

    /**
     * Edit an existing todo in the list
     */
    EDIT_TODO,

    /**
     * Assign a player to the todo
     */
    ASSIGN_TODO,

    /**
     * Remove an assigned player from the todo
     */
    UNASSIGN_TODO,

    /**
     * Share a list with the player
     */
    SHARE_LIST,

    /**
     * Remove a player from the list that he is shared with
     */
    UNSHARE_LIST,

    /**
     * Delete the list
     */
    DELETE_LIST
}
