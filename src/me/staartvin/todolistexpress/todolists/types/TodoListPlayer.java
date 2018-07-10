package me.staartvin.todolistexpress.todolists.types;

import java.util.UUID;

public class TodoListPlayer {

    private UUID uuid;

    private String name;

    public TodoListPlayer(UUID uuid, String name) {
        this(uuid);
        this.setName(name);
    }

    public TodoListPlayer(UUID uuid) {
        this.setUUID(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TodoListPlayer)) {
            return false;
        }

        return ((TodoListPlayer) obj).getUUID().equals(this.uuid);
    }
}
