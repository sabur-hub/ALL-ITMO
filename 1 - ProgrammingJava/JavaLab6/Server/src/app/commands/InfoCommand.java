package app.commands;

import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class InfoCommand extends Command {


    private Receiver receiver;


    public InfoCommand(Receiver receiver) {
        setName("info");
        setDescription("вывести информацию о коллекции");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager) {
            receiver.info(repositoryOfCity);
    }
}
