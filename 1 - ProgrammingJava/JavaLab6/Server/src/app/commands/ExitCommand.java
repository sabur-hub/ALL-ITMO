package app.commands;

import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class ExitCommand extends Command {

    private Receiver receiver;

    public ExitCommand(Receiver receiver) {
        this.receiver = receiver;
        setName("exit");
        setDescription("завершить программу (с сохранением)");

    }

    @Override
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager) {
            receiver.exit(repositoryOfCity);
    }
}
