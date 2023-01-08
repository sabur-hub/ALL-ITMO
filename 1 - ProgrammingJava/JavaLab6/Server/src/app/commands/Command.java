package app.commands;


import app.collection.City;
import app.collectionManager.Invoker;
import app.collectionManager.RepositoryOfCity;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Абсрактный класс Команды. Содержит в себе название, описание команды и ее аргументы и выполение
 */

public abstract class Command {

    private String name;
    private String description;
    private String args = "";

    public void execute(TreeMap<String, Command> hashMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager, String args) throws IOException {
    }
    public void execute(TreeMap<String, Command> hashMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager){

    }
    public void execute(TreeMap<String, Command> hashMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager, City city){

    }
    public void execute(TreeMap<String, Command> hashMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager, City city, String... args) throws IOException {

    }

    public String getArgs() {
        return args;
    }

    public String getDescription() {
        return description;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
