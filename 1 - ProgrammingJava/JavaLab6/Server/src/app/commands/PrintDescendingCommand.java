package app.commands;

import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class PrintDescendingCommand extends Command {

    private Receiver receiver;


    public PrintDescendingCommand(Receiver receiver) {
        setName("print_descending");
        setDescription("вывести коллекцию в порядке убывания");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager) {
            receiver.printDescending(repositoryOfCity);
    }


}
