package app.collectionManager;

import app.collection.City;
import app.collection.ComparatorClimate;
import app.collection.building.CityBuilder;
import app.collection.ui.IdGenerator;
import app.network.Network;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс, который хранит в себе коллекцию объектов и хранит реализациюю некоторых команд, связанных с коллекцией напрямую
 */

public class RepositoryOfCity {


    private final TreeMap<Integer, City> citiesCollection = new TreeMap<Integer, City>();
    private CityBuilder cityBuilder;
    private City city;
    private LocalDateTime birthdayOfTreemap;

    public RepositoryOfCity() {
        try {
            this.cityBuilder = new CityBuilder();
            this.birthdayOfTreemap = LocalDateTime.now();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Реализация добавления объекта в коллекцию
     */


    public void add(City city) {
        try {
            city.initialisationsAutoGene();
            getCitiesCollection().put(city.getId(), city);
            Network.write("Объект добавлен");
        } catch (StackOverflowError e) {
            Network.write("Коллекция переполнена!");
        }
    }

    public void add() {
        try {
            city = cityBuilder.create();
            city.initialisationsAutoGene();
            getCitiesCollection().put(city.getId(), city);
        } catch (StackOverflowError e) {
            Network.write("Коллекция переполнена!");
        }
    }

    public void setCityBuilderPath(Scanner inputStream) {
        cityBuilder.setInputPath(inputStream);
    }

    /**
     * Реализация получение объекта из коллекции по id
     *
     * @param id значение id объекта, который хотим получить
     * @return возвращает объект, если в коллекции он присутстывует; иначе - сообщение об его отсутствии
     */


    public City get(int id) {
        if (getCitiesCollection().get(id) != null) {
            return getCitiesCollection().get(id);
        } else {
            Network.write("В коллекции нет объекта с заданным ключем!");
            return null;
        }

    }

    /**
     * Реализация удаления объекта из коллекции по заданному id
     *
     * @param id значение id элемента, который хотим удалить
     */


    public void remove(int id) {

        if (!getCitiesCollection().isEmpty()) {
            if (getCitiesCollection().get(id) != null) {
                Network.write("Объект " + getCitiesCollection().get(id).getName() + " удален");
                IdGenerator.remove(id);
                getCitiesCollection().remove(id);
            } else {
                Network.write("Не найден объект с данным id!");
            }
        } else {
            Network.write("Коллекция пустая. Удалить объект невозможно!");
        }
    }

    /**
     * Метод реализует вывод количества элементов, находящихся в коллекции в данный момент
     */

    public void size() {
        Network.write("В коллекции сейчас " + getCitiesCollection().size() + " объекта/ов");
    }

    /**
     * Реализация отчистки коллекции от всех объектов
     */

    public void clear() {
        if (!getCitiesCollection().isEmpty()) {
            getCitiesCollection().clear();
            IdGenerator.clear();
            Network.write("Коллекция успешно очищена!");
        } else {
            Network.write("Коллекция пустая!");
        }
    }

    /**
     * Реализация вывода всех объектов коллекции
     */

    public void show() {
        char codeDow = 2039;
        if (getCitiesCollection().isEmpty()) {
            Network.write("Коллекция пустая!");
        } else {
            for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
                Integer k = pair.getKey();
                City v = pair.getValue();
                Network.write("|" + codeDow + "|" + System.lineSeparator() + v.toString());

            }

        }

    }

    public void removeGreater(City city) {
        if (!getCitiesCollection().isEmpty()) {
            city.initialisationsAutoGene();
            for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
                City v = pair.getValue();
                Integer k = pair.getKey();
                if (v.compareTo(city.getPopulation()) > 0) {
                    remove(k);

                }
            }
            Network.write("Удалены все элементы, большие чем введенный город: " + city.getName() + "с численностью населения:" + city.getPopulation());
        } else {
            Network.write("Коллекция пустая!");
        }
    }

    public void removeGreater() {
        if (!getCitiesCollection().isEmpty()) {
            city = cityBuilder.create();
            city.initialisationsAutoGene();
            for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
                City v = pair.getValue();
                Integer k = pair.getKey();
                if (v.compareTo(city.getPopulation()) > 0) {
                    remove(k);

                }
            }
            System.out.println("Удалены все элементы, большие чем введенный город: " + city.getName() + "с численностью населения:" + city.getPopulation());
        } else {
            System.out.println("Коллекция пустая!");
        }
    }

    /**
     * Реализация команды удаления всех элементов, меньших чем заданный
     */

    public void removeLower(City city) {
        if (!getCitiesCollection().isEmpty()) {
            city.initialisationsAutoGene();
            for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
                City v = pair.getValue();
                Integer k = pair.getKey();
                if (v.compareTo(city.getPopulation()) < 0) {
                    remove(k);

                }
            }
            Network.write("Удалены все элементы, меньшие чем введенный город: " + city.getName() + "с численностью населения:" + city.getPopulation());
        } else {
            Network.write("Коллекция пустая!");
        }

    }

    public void removeLower() {
        if (!getCitiesCollection().isEmpty()) {
            city = cityBuilder.create();
            city.initialisationsAutoGene();
            for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
                City v = pair.getValue();
                Integer k = pair.getKey();
                if (v.compareTo(city.getPopulation()) < 0) {
                    remove(k);

                }
            }
            System.out.println("Удалены все элементы, меньшие чем введенный город: " + city.getName() + "с численностью населения:" + city.getPopulation());
        } else {
            System.out.println("Коллекция пустая!");
        }

    }

    public TreeMap<Integer, City> getCitiesCollection() {
        return citiesCollection;
    }

    /**
     * Реализация команды вывода информации о коллекции обхектов
     */
    public void info() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String collectionCreationDate = getBirthdayOfTreemap().format(formatter);
        Network.write("Информация о коллекции: " + System.lineSeparator() +
                "- время инициализации коллекции: " + collectionCreationDate + System.lineSeparator() +
                "- типа коллекции: " + getCitiesCollection().getClass().getTypeName() + System.lineSeparator() +
                "- количество элементов коллекции: " + getCitiesCollection().size());
    }


    public LocalDateTime getBirthdayOfTreemap() {
        return birthdayOfTreemap;
    }

    /**
     * Реализация команды обновления элемента по заданному id
     *
     * @param id id элменета, который хотим обновить
     */


    public void updateId(City city, Integer id) {
        if (!getCitiesCollection().isEmpty()) {
            if (getCitiesCollection().get(id) == null) {
                Network.write("Объекта с заданным id не существует" + System.lineSeparator() + "Обновление объекта невозможно!");
            } else {
                getCitiesCollection().remove(id, get(id));
                city.setId(id);
                city.setCreationDate();
                getCitiesCollection().put(id, city);
                Network.write("Поле успешно обновлено!");
            }
        } else {
            Network.write("Коллекция пустая!");
        }

    }

    public void updateId(Integer id) {
        if (!getCitiesCollection().isEmpty()) {
            if (getCitiesCollection().get(id) == null) {
                System.out.println("Объекта с заданным id не существует" + System.lineSeparator() + "Обновление объекта невозможно!");
            } else {
                getCitiesCollection().remove(id, get(id));
                city = cityBuilder.create();
                city.setId(id);
                city.setCreationDate();
                getCitiesCollection().put(id, city);
                System.out.println("Поле успешно обновлено!");
            }
        } else {
            System.out.println("Коллекция пустая!");
        }

    }

    /**
     * Реализация метода, который выводит среднее значение поля высоты над уровнем моря всех объектов
     */

    public void averageMetersAboveSeaLevel() {
        long citymeterAboveSeaLevel = 0L;
        for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
            City v = pair.getValue();
            citymeterAboveSeaLevel = citymeterAboveSeaLevel + v.getMetersAboveSeaLevel();
        }
        try {
            citymeterAboveSeaLevel /= getCitiesCollection().size();
            Network.write("Среднее значение поля 'высота над уровнем моря' элементов коллекции = " + citymeterAboveSeaLevel);
        } catch (ArithmeticException e) {
            Network.write("Т.к. в коллекции нет элементов - среднеее значение = 0");
        }
    }

    /**
     * Реализация вывода элемента коллекции с минимальным значением поля Климат
     */


    public void minByClimate() {
        Comparator<Map.Entry<Integer, City>> comparatorClimate = new ComparatorClimate();
        if (!getCitiesCollection().isEmpty()) {
            Network.write(citiesCollection.entrySet().stream().min(comparatorClimate).get().getValue().getName());
        } else {
            Network.write("Коллекция пустая!");
        }

    }


    public void printDescending() {
        if (!getCitiesCollection().isEmpty()) {
            ArrayList<City> citiesArray = new ArrayList<>();
            for (Map.Entry<Integer, City> pair : getCitiesCollection().entrySet()) {
                City v = pair.getValue();
                citiesArray.add(v);
            }
            citiesArray.sort((o1, o2) -> -o1.compareTo(o2.getPopulation()));
            for (City city : citiesArray) {
                Network.write(city.toString());
            }
        } else {
            Network.write("Коллекция пустая!");
        }
    }
    //new ComparatorPopulation().reversed()

    /**
     * Реализация команды добавления элемента по заданному id
     *
     * @param key сам id, с которым мы хотим создать элемент
     */


    public void insert(City city, int key) {

        if (getCitiesCollection().get(key) == null) {
            city.setId(key);
            city.setCreationDate();
            getCitiesCollection().put(city.getId(), city);
            Network.write(city.getName() + "добавлен в коллекцию!");
        } else {
            Network.write("По данному адресу ячейка уже занята!");
        }
    }

    public void insert(int key) {

        if (getCitiesCollection().get(key) == null) {
            city = cityBuilder.create();
            city.setId(key);
            city.setCreationDate();
            getCitiesCollection().put(city.getId(), city);
            System.out.println(city.getName() + "добавлен в коллекцию!");
        } else {
            System.out.println("По данному адресу ячейка уже занята!");
        }
    }

    /**
     * Реализация команды замены элемента по заданному id, если он больше, чем старый
     *
     * @param key id, по которому хотим произвести замену
     */

    public void replaceIfGreater(City city, int key) {
        if (!getCitiesCollection().isEmpty()) {
            city.setId(key);
            city.setCreationDate();
            if (city.getPopulation() > get(key).getPopulation()) {
                getCitiesCollection().put(key, city);
                Network.write("Элемент успешно обновлен");
            } else {
                Network.write("Значение нового элемента меньше старого!");
            }
        } else {
            Network.write("Коллекция пустая!");

        }


    }

    public void replaceIfGreater(int key) {
        if (!getCitiesCollection().isEmpty()) {
            city = cityBuilder.create();
            city.setId(key);
            city.setCreationDate();
            if (city.getPopulation() > get(key).getPopulation()) {
                getCitiesCollection().put(key, city);
                System.out.println("Элемент успешно обновлен");
            } else {
                System.out.println("Значение нового элемента меньше старого!");
            }
        } else {
            System.out.println("Коллекция пустая!");

        }


    }
}

