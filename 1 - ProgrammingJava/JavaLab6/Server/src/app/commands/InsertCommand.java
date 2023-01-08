package app.commands;

import app.collection.City;
import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class InsertCommand extends Command {


    private Receiver receiver;


    public InsertCommand(Receiver receiver) {
        setName("insert");
        setDescription("добавить новый элемент с заданным ключом - [key]");
        this.receiver = receiver;
    }

    @Override
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager, City city,String... args) {
                int key = Integer.parseInt(args[0]);
                receiver.insert(repositoryOfCity,city, key);
    }
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager,String args){
        try {
            int key = Integer.parseInt(args);
            receiver.insert(repositoryOfCity,key);
        }catch (NumberFormatException e){
            System.out.println("Аргумент имеет недопустимое значение.");
        }

    }
}

