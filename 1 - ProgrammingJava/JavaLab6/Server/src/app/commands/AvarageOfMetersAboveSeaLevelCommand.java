package app.commands;

import app.collectionManager.Invoker;
import app.collectionManager.Receiver;
import app.collectionManager.RepositoryOfCity;

import java.util.TreeMap;

public class AvarageOfMetersAboveSeaLevelCommand extends Command {
    private Receiver receiver;

    public AvarageOfMetersAboveSeaLevelCommand(Receiver receiver) {
        setName("average_of_meters_above_sea_level");
        setDescription("вывести среднее значение поля metersAboveSeaLevel для всех элементов коллекции");
        this.receiver = receiver;
    }

    @Override
    public void execute(TreeMap<String, Command> treeMap, RepositoryOfCity repositoryOfCity, Invoker commandManager) {
        receiver.averageMetersAboveSeaLevel(repositoryOfCity);
    }
}
