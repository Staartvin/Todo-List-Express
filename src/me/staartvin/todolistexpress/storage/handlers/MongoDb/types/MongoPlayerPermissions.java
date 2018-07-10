package me.staartvin.todolistexpress.storage.handlers.MongoDb.types;

import me.staartvin.todolistexpress.todolists.types.ListPermission;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "Player permissions", noClassnameStored = true)
public class MongoPlayerPermissions {

    @Id
    private ObjectId id;
    @Indexed
    private String player;
    @Indexed
    @Property("todo list")
    private String todoListName;
    private List<ListPermission> permissions = new ArrayList<>();

    public MongoPlayerPermissions() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public void setTodoListName(String todoListName) {
        this.todoListName = todoListName;
    }

    public List<ListPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ListPermission> permissions) {
        this.permissions = permissions;
    }
}
