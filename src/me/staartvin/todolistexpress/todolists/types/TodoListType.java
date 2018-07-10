package me.staartvin.todolistexpress.todolists.types;

public enum TodoListType {

    /**
     * A private todo list can only be viewed by the creator and those whom the list is shared with
     */
    PRIVATE,

    /**
     * A public todo list can be viewed by everyone, but only people that the list is shared with can add todos.
     */
    PUBLIC
}
