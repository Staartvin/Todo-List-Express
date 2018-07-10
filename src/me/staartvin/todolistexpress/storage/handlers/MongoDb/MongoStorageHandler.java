package me.staartvin.todolistexpress.storage.handlers.MongoDb;

import com.mongodb.MongoClient;
import me.staartvin.todolistexpress.TodoListExpress;
import me.staartvin.todolistexpress.storage.handlers.MongoDb.types.MongoPlayerPermissions;
import me.staartvin.todolistexpress.storage.handlers.MongoDb.types.MongoTodo;
import me.staartvin.todolistexpress.storage.handlers.MongoDb.types.MongoTodoList;
import me.staartvin.todolistexpress.storage.handlers.StorageHandler;
import me.staartvin.todolistexpress.todolists.types.Todo;
import me.staartvin.todolistexpress.todolists.types.TodoList;
import me.staartvin.todolistexpress.todolists.types.TodoListPlayer;
import org.bukkit.Bukkit;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MongoStorageHandler extends StorageHandler {

    private MongoClient mongoClient;
    private Morphia morphia;

    private Datastore datastore;


    public MongoStorageHandler(TodoListExpress instance) {
        super(instance);
    }

    @Override
    public void saveTodoList(TodoList todoList) {

        if (!isReady()) {
            return;
        }

        List<MongoTodoList> matchingLists = datastore.find(MongoTodoList.class).field("name").equalIgnoreCase(todoList
                .getName()).asList();

        MongoTodoList mongoTodoList;

        // If there is no old record, set new record to converted todo list.
        if (matchingLists == null || matchingLists.isEmpty()) {
            mongoTodoList = this.convertToMongoFormat(todoList);
        } else {
            // If there is a matching record, delete that first.

            // There is a matching todo list.
            mongoTodoList = matchingLists.get(0);

            // Delete old record
            datastore.delete(mongoTodoList);

            mongoTodoList = this.convertToMongoFormat(todoList);
        }

        // Save new record
        datastore.save(mongoTodoList);
    }

    @Override
    public void removeTodoList(TodoList todoList) {

        if (!isReady()) {
            return;
        }


    }

    @Override
    public List<TodoList> getAllTodoLists() {

        if (!isReady()) {
            return new ArrayList<>();
        }

        List<MongoTodoList> mongoTodoLists = datastore.find(MongoTodoList.class).asList();

        if (mongoTodoLists == null) {
            return new ArrayList<>();
        }

        return mongoTodoLists.stream().map(this::convertFromMongoFormat).collect(Collectors.toList());
    }

    @Override
    public void loadStorageHandler() {
//        ServerAddress addr = new ServerAddress("localhost", 27017);
//        List<MongoCredential> credentials = new ArrayList<>();
//        credentials.add(MongoCredential.createCredential("staartvin", "database", "samplePassword".toCharArray()));
        mongoClient = new MongoClient();
        morphia = new Morphia();

        // Notify Morphia what classes we are going to use in the database.
        morphia.map(MongoTodoList.class);
        morphia.map(MongoTodo.class);
        morphia.map(MongoPlayerPermissions.class);

        datastore = morphia.createDatastore(mongoClient, "Todo-List-Express");
        datastore.ensureIndexes();
    }

    @Override
    public boolean isReady() {
        return mongoClient != null && morphia != null && datastore != null;
    }

    private MongoTodoList convertToMongoFormat(TodoList todoList) {
        MongoTodoList mongoTodoList = new MongoTodoList();

        mongoTodoList.setOwner(todoList.getOwner().getUUID().toString());
        mongoTodoList.setDescription(todoList.getDescription());
        mongoTodoList.setName(todoList.getName());
        mongoTodoList.setType(todoList.getType());

        if (todoList.getTodos().isEmpty()) {
            mongoTodoList.setTodos(new ArrayList<>());
        } else {
            mongoTodoList.setTodos(todoList.getTodos().stream().map(this::convertToMongoFormat).collect(Collectors
                    .toList()));
        }

        return mongoTodoList;
    }

    private MongoTodo convertToMongoFormat(Todo todo) {
        MongoTodo mongoTodo = new MongoTodo();

        mongoTodo.setName(todo.getName());
        mongoTodo.setDescription(todo.getDescription());
        mongoTodo.setOwner(todo.getOwner().getUUID().toString());

        if (todo.getAssignedPlayers().isEmpty()) {
            mongoTodo.setAssignedPlayers(new ArrayList<>());
        } else {
            mongoTodo.setAssignedPlayers(todo.getAssignedPlayers().stream().map(player -> player.getUUID().toString()
            ).collect(Collectors.toList()));
        }

        return mongoTodo;
    }

    private Todo convertFromMongoFormat(MongoTodo mongoTodo) {
        Todo todo = new Todo();

        todo.setOwner(this.convertFromUUID(UUID.fromString(mongoTodo.getOwner())));
        todo.setName(mongoTodo.getName());
        todo.setDescription(mongoTodo.getDescription());
        todo.setAssignedPlayers(mongoTodo.getAssignedPlayers().stream().map(uuid -> this.convertFromUUID(UUID
                .fromString(uuid))).collect(Collectors.toList()));

        return todo;
    }

    private TodoList convertFromMongoFormat(MongoTodoList mongoTodoList) {
        TodoList todoList = new TodoList();

        todoList.setOwner(this.convertFromUUID(UUID.fromString(mongoTodoList.getOwner())));
        todoList.setDescription(mongoTodoList.getDescription());
        todoList.setName(mongoTodoList.getName());
        todoList.setTodos(mongoTodoList.getTodos().stream().map(this::convertFromMongoFormat).collect(Collectors
                .toList()));
        todoList.setType(mongoTodoList.getType());

        return todoList;
    }

    private TodoListPlayer convertFromUUID(UUID uuid) {
        String playerName = Bukkit.getOfflinePlayer(uuid).getName();

        return new TodoListPlayer(uuid, playerName);
    }
}
