package app.collectionManager;


import app.collection.City;
import app.commands.Command;
import app.csv.CSVWriter;
import exceptions.ArgumentException;
import exceptions.MessageErrors;
import io.ConsoleReader;
import app.network.Network;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

;


/**
 * Класс, который обеспечивает выполнение команд
 */

public class Receiver implements MessageErrors {

    ConsoleReader input;
    CSVWriter csvWriter;
    Set<String> fileNames = new HashSet<>();


    public Receiver() {
        input = new ConsoleReader();
        csvWriter = new CSVWriter();
    }


    /**
     * Реализация команды help, выводит все справку по командам консоли
     *
     * @param commandTreeMap карта, хранящая все команды
     */
    public void help(TreeMap<String, Command> commandTreeMap) {
        Network.write(System.lineSeparator() + "СПРАВКА ПО КОМАНДАМ:" + System.lineSeparator());
        for (Map.Entry<String, Command> entry : commandTreeMap.entrySet()) {
            Network.write(entry.getKey() + " -" + entry.getValue().getArgs() + " " + entry.getValue().getDescription());
        }
        Network.write(System.lineSeparator() + "*: если в описании команды имеется '[key]', значит у неё есть аругмент. " + System.lineSeparator() +
                "*: команда 'execute_script' имеет аргумент [file name] - название файла.");
    }

    /**
     * Обеспечивает добавление города в коллекцию
     *
     * @param repositoryOfCity коллекция городов
     * @see RepositoryOfCity#() здесь хранится ее реализация
     */
    public void add(RepositoryOfCity repositoryOfCity, City city) {
        repositoryOfCity.add(city);
    }

    public void add(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.add();
    }

    /**
     * Обеспечивает удаление города из коллекции
     *
     * @param repositoryOfCity коллекция городов
     * @param id               уникальный индификатор города
     * @see RepositoryOfCity#remove(int) реализация команды
     */
    public void remove(RepositoryOfCity repositoryOfCity, Integer id) {
        repositoryOfCity.remove(id);
    }

    /**
     * Обеспечивает вывод информации о коллекции
     *
     * @param repositoryOfCity коллекция, котораях хранит в себе города
     * @see RepositoryOfCity#info() реализация команды
     */
    public void info(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.info();
    }

    /**
     * Обеспечивает вывод всех объектов коллекции
     *
     * @param repositoryOfCity коллекция, котораях хранит в себе города
     * @see RepositoryOfCity#show() реализация команды
     */
    public void show(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.show();
    }

    /**
     * Обеспечивает очистку коллекции объектов
     *
     * @param repositoryOfCity коллекция, котораях хранит в себе города
     * @see RepositoryOfCity#clear() реализация команды
     */
    public void clear(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.clear();
    }

    /**
     * Обеспечивает вывод объекта с минимальным полем Climate
     *
     * @param repositoryOfCity коллекция, котораях хранит в себе города
     * @see RepositoryOfCity#minByClimate() реализация команды
     */
    public void minByClimate(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.minByClimate();
    }

    /**
     * Обеспечивает удаление всех элементов, больших чем заданный
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @see RepositoryOfCity реализация команды
     */
    public void removeGreater(RepositoryOfCity repositoryOfCity, City city) {
        repositoryOfCity.removeGreater(city);
    }

    public void removeGreater(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.removeGreater();
    }


    /**
     * Обеспечивает удаление всех элементов, меньших чем заданный
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @see RepositoryOfCity реализация команды
     */
    public void removeLower(RepositoryOfCity repositoryOfCity, City city) {
        repositoryOfCity.removeLower(city);
    }

    public void removeLower(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.removeLower();
    }

    /**
     * Обеспечивает вывод коллекции в порядке убывания
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @see RepositoryOfCity#printDescending()  реализация команды
     */
    public void printDescending(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.printDescending();
    }

    /**
     * Обеспечивает обновление элемента по заданному id
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @param id               уникальный номер элемента коллекции
     * @see RepositoryOfCity реализация команды
     */
    public void updateId(RepositoryOfCity repositoryOfCity, City city, Integer id) {
        repositoryOfCity.updateId(city, id);
    }

    public void updateId(RepositoryOfCity repositoryOfCity, Integer id) {
        repositoryOfCity.updateId(id);
    }

    /**
     * Обеспечивает вывод среднего значения поля климат всех объектов
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @see RepositoryOfCity#averageMetersAboveSeaLevel() реализация команды
     */
    public void averageMetersAboveSeaLevel(RepositoryOfCity repositoryOfCity) {
        repositoryOfCity.averageMetersAboveSeaLevel();
    }

    /**
     * Обеспечивает замену элемента на новый, если новый больше старого
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @param id               уникальный номер элемента коллекции, который хотим проверить
     */
    public void replaceIfGreater(RepositoryOfCity repositoryOfCity, City city, int id) {
        repositoryOfCity.replaceIfGreater(city, id);
    }

    public void replaceIfGreater(RepositoryOfCity repositoryOfCity, int id) {
        repositoryOfCity.replaceIfGreater(id);
    }


    /**
     * Обеспечивает завершение приложения
     */
    public void exit(RepositoryOfCity repositoryOfCity) {
        final String EXITWORD = "00101110010000010011001000111101";
        try {
            csvWriter.write(repositoryOfCity.getCitiesCollection());
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        Network.write(EXITWORD);
    }

    /**
     * Обеспечивает добавление элемента с заданным id
     *
     * @param repositoryOfCity коллекция, которая хранит города
     * @param key              ключ,уникальный id, по по которому будет добавлен элемент
     *                         * @see RepositoryOfCity#insert(int)
     */
    public void insert(RepositoryOfCity repositoryOfCity, City city, Integer key) {
        repositoryOfCity.insert(city, key);
    }

    public void insert(RepositoryOfCity repositoryOfCity, Integer key) {
        repositoryOfCity.insert(key);
    }

    /**
     * Обеспечиание сохранение коллекции в файл
     *
     * @param repositoryOfCity коллекция, которая хранит в себе города
     * @throws IOException возможная ошибка вывода в файл
     * @see CSVWriter#write(TreeMap)
     */


    /**
     * Занимается выполнением исполняемого файла
     *
     * @param invoker          инвокер, который хранит в себе все команды
     * @param repositoryOfCity коллекция городов
     */
    public void executeScript(File file, Invoker invoker, RepositoryOfCity repositoryOfCity) {

        try {
            InputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream);
            repositoryOfCity.setCityBuilderPath(scanner);


            String line = "";
            int lineCounter = 0;
            String regex = "\\s*\\w*\\_*\\s*\\w*\\s*\\.*\\w*\\s*";
            if (file.isDirectory()) {
                Network.write("Необходим обязательный аргумент: Полное имя файла данных, не директория");
            }
            if (!file.exists()) {
                Network.write("Исполняемый файл не найден");
            }
            if (!file.canRead() && !file.canWrite()) {
                Network.write("Ошибка доступа к исполняемому файлу: нет прав на чтение и запись");
            }
            if (!file.canRead()) {
                Network.write("Ошибка доступа к исполняемому файлу: нет прав на чтение");
            }
            if (!file.canWrite()) {
                Network.write("Ошибка доступа к исполняемому файлу: нет прав на запись");
            }
            if (fileNames.contains(file.getName())) {
                Network.write(System.lineSeparator() + "Ошибка рекурсии:" + System.lineSeparator() + "Не нужно меня ломать:(");
            } else {
                fileNames.add(file.getName());
                while (!line.trim().equals("exit") && scanner.hasNextLine()) {
                    lineCounter += 1;
                    if (Pattern.matches(regex.trim(), line)) {
                        try {
                            line = scanner.nextLine();
                            if (line.trim().equals("")) {
                                break;
                            } else {
                                String[] lines = line.split("\\s*\\s{1}\\s*");
                                String command = lines[0];
                                if (lines.length > 1) {
                                    String arg = lines[1];

                                    try {
                                        invoker.execute(command, invoker.getCommandMap(), repositoryOfCity, arg);
                                    } catch (IllegalStateException e) {
                                        Network.write("Команда не найдена. Проверьте правильность её написания. " + "Строка №" + lineCounter);
                                    } catch (ArgumentException | IOException e) {
                                        Network.write(e.getMessage());
                                    }
                                } else {
                                    try {
                                        invoker.execute(command, invoker.getCommandMap(), repositoryOfCity);
                                    } catch (IllegalStateException e) {
                                        Network.write("Команда не найдена. Проверьте правильность её написания. " +
                                                System.lineSeparator() + "Строка №" + lineCounter);
                                    } catch (ArgumentException | IOException e) {
                                        Network.write(e.getMessage());
                                    } catch (NoSuchElementException e) {
                                        Network.write("Неккоректное измените значение в файле и попробуйте заново!");
                                    }
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException | StackOverflowError e) {
                            Network.write(e.getMessage());
                        }
                    } else {
                        scanner.nextLine();
                        Network.write("Строка №" + lineCounter + " имеет неправильный формат." + System.lineSeparator() +
                                "Испрвьте ошибку и попробуйте еще раз.");
                    }
                }

            }
            fileNames.clear();

        } catch (FileNotFoundException e) {
            Network.write(file.getPath());
            Network.write("Файл для выполнения не найден");
        } catch (NoSuchElementException ignored) {
        }
    }


}
