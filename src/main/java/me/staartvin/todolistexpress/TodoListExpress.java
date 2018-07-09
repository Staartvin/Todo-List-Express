package main.java.me.staartvin.todolistexpress;

import org.bukkit.plugin.java.JavaPlugin;

public class TodoListExpress extends JavaPlugin {

    public void onEnable() {
        this.getLogger().info(this.getDescription().getFullName() + " has been enabled!");
    }

    public void onDisable() {
        this.getLogger().info(this.getDescription().getFullName() + " has been disabled!");
    }
}
