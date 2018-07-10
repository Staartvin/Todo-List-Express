package me.staartvin.todolistexpress.storage.handlers.MongoDb.types;

import me.staartvin.todolistexpress.todolists.types.TodoListType;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "Todo lists", noClassnameStored = true)
public class MongoTodoList {

    @Id
    private ObjectId id;
    @Indexed
    private String owner;
    @Indexed(options = @IndexOptions(unique = true))
    private String name;
    private String description = "No description";
    private TodoListType type = TodoListType.PRIVATE;
    @Embedded
    private List<MongoTodo> todos = new ArrayList<>();

    public MongoTodoList() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MongoTodo> getTodos() {
        return todos;
    }

    public void setTodos(List<MongoTodo> todos) {
        this.todos = todos;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public TodoListType getType() {
        return type;
    }

    public void setType(TodoListType type) {
        this.type = type;
    }
}
