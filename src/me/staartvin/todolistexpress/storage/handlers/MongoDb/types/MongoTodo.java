package me.staartvin.todolistexpress.storage.handlers.MongoDb.types;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Indexed;

import java.util.ArrayList;
import java.util.List;

@Embedded
public class MongoTodo {

    @Indexed
    private String name;
    private String description = "No description";
    // UUID of owner
    private String owner;
    // UUIDs of players that are assigned to this todo
    private List<String> assignedPlayers = new ArrayList<>();

    public MongoTodo() {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getAssignedPlayers() {
        return assignedPlayers;
    }

    public void setAssignedPlayers(List<String> assignedPlayers) {
        this.assignedPlayers = assignedPlayers;
    }
}
