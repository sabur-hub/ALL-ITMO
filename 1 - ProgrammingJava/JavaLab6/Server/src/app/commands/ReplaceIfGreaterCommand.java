package app.commands;

import app.collection.City;
import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class ReplaceIfGreaterCommand extends Command {


    private Receiver receiver;


    public ReplaceIfGreaterCommand(Receiver receiver) {
        setName("replace_if_greater");
        setDescription("заменить значение по ключу, если новое значение больше старого - [key]");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager,City city, String... args) {
            int key = Integer.parseInt(args[0]);
            receiver.replaceIfGreater(repositoryOfCity,city, key);
    }
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager, String... args) {
        try {
            int key = Integer.parseInt(args[0]);
            receiver.replaceIfGreater(repositoryOfCity, key);
        }catch (NumberFormatException e){
            System.out.println("Аргумент имеет недопустимое значение.");
        }

    }

}
