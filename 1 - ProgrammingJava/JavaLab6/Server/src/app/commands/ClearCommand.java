package app.commands;

import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class ClearCommand extends Command {
    private Receiver receiver;

    public ClearCommand(Receiver receiver) {
        setName("clear");
        setDescription("очистить коллекцию");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> treemap, RepositoryOfCity repositoryOfCity, Invoker commandManager) {
            receiver.clear(repositoryOfCity);
    }
}
