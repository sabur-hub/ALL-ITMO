package app.commands;

import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.io.File;
import java.util.HashSet;
import java.util.TreeMap;

public class ExecuteScriptCommand extends Command {


    private Receiver receiver;
    HashSet<String> files = new HashSet<>();


    public ExecuteScriptCommand(Receiver receiver) {
        setName("execute_script");
        setDescription("[file name] - выполнить файл с командами - [key] - абсолютный путь файла");
        this.receiver = receiver;
    }


    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager, String args) {
        File file = new File(args);
        receiver.executeScript(file, commandManager, repositoryOfCity);
    }

}
