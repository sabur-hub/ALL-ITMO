package app.commands;

import app.collection.City;
import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;


import java.util.TreeMap;

public class AddCommand extends Command {

    private Receiver receiver;


    public AddCommand(Receiver receiver) {
        setName("add");
        setDescription("добавить новый элемент в коллекцию");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager, City city) {
            receiver.add(repositoryOfCity,city);
    }
    public void execute(TreeMap<String,Command> commandTreeMap,RepositoryOfCity repositoryOfCity,
                        Invoker commandManager){
            receiver.add(repositoryOfCity);
    }
}
