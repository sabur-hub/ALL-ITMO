package app.commands;

import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class MinByClimateCommand extends Command {

    private Receiver receiver;


    public MinByClimateCommand(Receiver receiver) {
        setName("min_by_climate");
        setDescription("вывести объект из коллекции, значение поля climate которого является минимальным");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap, RepositoryOfCity repositoryOfCity,
                        Invoker commandManager) {
            receiver.minByClimate(repositoryOfCity);
    }
}
