package me.staartvin.todolistexpress.storage;

import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.storage.handlers.StorageHandler;

public class StorageManager {

    private TodoListExpress plugin;

    // Handler that stores the actual data.
    private StorageHandler storageHandler;

    public StorageManager(TodoListExpress plugin) {
        this.plugin = plugin;
    }

    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    public void setStorageHandler(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }
}
