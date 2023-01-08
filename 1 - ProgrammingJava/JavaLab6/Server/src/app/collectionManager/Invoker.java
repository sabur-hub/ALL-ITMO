package app.collectionManager;


import app.collection.City;
import app.commands.Command;

import java.io.IOException;
import java.util.TreeMap;

public class Invoker {


    private final TreeMap<String, Command> commandMap = new TreeMap<>();

    public void setCommandMap(String commandName, Command command) {
        commandMap.put(commandName, command);

    }

    /**
     * Метод выполняет команду, с заданным ему ее названием commandName
     *
     * @param commandName      название команды
     * @param commandMap       карта команды
     * @param repositoryOfCity коллекция, которая хранит в себе объекты класса City
     * @param args             аргументы команды
     * @throws IOException ошибка ввода
     */

    public void execute(String commandName, TreeMap<String, Command> commandMap, RepositoryOfCity
            repositoryOfCity, String args) throws IOException {
        Command command = commandMap.get(commandName);
        if (command == null) throw new IllegalStateException("Команды с названием " + commandName + " не существует");
        command.execute(commandMap, repositoryOfCity, this, args);
    }

    public void execute(String commandName, TreeMap<String, Command> commandMap, RepositoryOfCity
            repositoryOfCity, City city) {
        Command command = commandMap.get(commandName);
        command.execute(commandMap, repositoryOfCity, this, city);
    }

    public void execute(String commandName, TreeMap<String, Command> commandMap, RepositoryOfCity
            repositoryOfCity) throws IOException {
        Command command = commandMap.get(commandName);
        if (command == null) throw new IllegalStateException("Команды с названием " + commandName + " не существует");
        command.execute(commandMap, repositoryOfCity, this);
    }

    public void execute(String commandName, TreeMap<String, Command> commandMap, RepositoryOfCity
            repositoryOfCity, City city, String... args) throws IOException {
        Command command = commandMap.get(commandName);
        command.execute(commandMap, repositoryOfCity, this, city, args);
    }


    public TreeMap<String, Command> getCommandMap() {
        return commandMap;
    }


    @Override
    public String toString() {
        return "Switch{" +
                "commandMap=" + commandMap +
                '}';
    }
}

