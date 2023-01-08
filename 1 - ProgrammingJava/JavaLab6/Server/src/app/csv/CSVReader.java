package app.csv;

import app.ApplicationManager;
import app.collection.*;
import app.collection.ui.IdGenerator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;



/**
 * Класс занимается объектов из файла, записанных в формате CSV, и их последующей инициализацией в коллекцию.
 * Проверка валидности файла производится с помощью регулярных выражений
 */


public class CSVReader {
    City city;
    Coordinates coordinates;
    Human governor;
    Climate climate;
    Government government;
    StandardOfLiving standardOfLiving;
    LocalDateTime dateOfBirthday;
    File file;




    public TreeMap<Integer, City> parseCSV() {
        TreeMap<Integer, City> collection = new TreeMap<>();
        int linesCoiner = 1;
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(new File(ApplicationManager.path)) ))) {
            if (accessCheck(ApplicationManager.path) == 0){
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    String regex1 =
                            "\\d+\\s*\\d*\\s*[,;]\\s*[\\w\\W]+\\s*[;,]\\s*\\d+\\s*[;,]\\s*\\d+\\.\\d*\\s*[,;]\\s*\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}\\s*[,;]\\s*\\d+\\s*[;,]\\s*\\d+\\s*[;,]\\s*\\d+\\s*[;,]\\s*[\\w\\W]+\\s*[\\W\\w]*\\s*[,;]\\s*[\\w\\W]+\\s*[,;]\\s*[\\w\\W]+\\s*[\\W\\w]*\\s*[,;]\\s*\\d+\\s*[,;]\\s*\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}";
                    String regex2 = "\\d+\\s*\\d*\\s*[,;]\\s*[\\w\\W]+\\s*[;,]\\s*\\d+\\s*[;,]\\s*\\d+\\.\\d*\\s*[,;]\\s*\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}\\s*[,;]\\s*\\d+\\s*[;,]\\s*\\d+\\s*[;,]\\s*\\d+\\s*[;,]\\s*[\\w\\W]+\\s*[\\W\\w]*\\s*[,;]\\s*[\\w\\W]+\\s*[,;]\\s*[\\w\\W]+\\s*[\\W\\w]*\\s*[,;]\\s*\\w{4}\\s*";
                    if (Pattern.matches(regex1.trim(), line)) {
                        String[] lines = line.split("\\s*[;,]\\s*");
                        try {
                            Integer id = Integer.parseInt(lines[0].trim());
                            if(!IdGenerator.getIdSet().contains(id)){
                                IdGenerator.getIdSet().add(id);
                            }else{
                                System.out.println("Ошибка! Объект не добавлен! Неуникальное значение поля 'id' в строке №" + linesCoiner);
                                continue;
                            }
                            String name = lines[1].trim();
                            int x = Integer.parseInt(lines[2].trim());
                            float y = Float.parseFloat(lines[3].trim());
                            coordinates = new Coordinates(x, y);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            LocalDateTime date = LocalDateTime.parse(lines[4].trim(), formatter);
                            long area = Long.parseLong(lines[5].trim());
                            int population = Integer.parseInt(lines[6].trim());
                            Long metersAboveSeaLevel = Long.parseLong(lines[7].trim());
                            switch (lines[8].trim()) {
                                case "Тропическая саванна":
                                    climate = Climate.TROPICAL_SAVANNA;
                                    break;
                                case "Средиземноморье":
                                    climate = Climate.MEDITERRANEAN;
                                    break;
                                case "Полярный ледник":
                                    climate = Climate.POLAR_ICECAP;
                                    break;
                                case "null":
                                    climate = null;
                                    break;
                                default:
                                    System.out.println("Ошибка! Объект " + name + " не добавлен! Неверное значение поля 'Климат' в строке №" + linesCoiner);
                                    continue;
                            }
                            switch (lines[9].trim()) {
                                case "Критархия":
                                    government = Government.KRITARCHY;
                                    break;
                                case "Олигархия":
                                    government = Government.OLIGARCHY;
                                    break;
                                case "Тимократия":
                                    government = Government.TIMOCRACY;
                                    break;
                                case "Тоталитаризм":
                                    government = Government.TOTALITARIANISM;
                                    break;
                                case "null":
                                    government = null;
                                    break;
                                default:
                                    System.out.println("Ошибка! Объект " + name + " не добавлен! Неверное значение поля 'Тип правления' в строке №" + linesCoiner);
                                    continue;
                            }
                            switch (lines[10].trim()) {
                                case "Очень высокий":
                                    standardOfLiving = StandardOfLiving.ULTRA_HIGH;
                                    break;
                                case "Средний":
                                    standardOfLiving = StandardOfLiving.MEDIUM;
                                    break;
                                case "Низкий":
                                    standardOfLiving = StandardOfLiving.VERY_LOW;
                                    break;
                                case "Кошмарный":
                                    standardOfLiving = StandardOfLiving.NIGHTMARE;
                                default:
                                    System.out.println("Ошибка! Объект " + name + " не добавлен! Неверное значение поля 'Уровень жизни' в строке №" + linesCoiner);
                                    continue;
                            }
                            int age = Integer.parseInt(lines[11].trim());
                            dateOfBirthday = LocalDateTime.parse(lines[12].trim(), formatter);
                            governor = new Human(age, dateOfBirthday);
                            city = new City(id, name, coordinates, date, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
                            collection.put(city.getId(), city);
                            linesCoiner += 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Объект не может быть инициализирован, так как числовое поле в №" + linesCoiner + " допущена ошикбка..");
                        } catch (StackOverflowError e) {
                            System.out.println("Объект не может быть инициализирован, так как коллекция объектов переполнена!");
                        } catch (DateTimeParseException e) {
                            System.out.println("Ошибка! Объект не добавлен! Неккоректное значение поля 'Дата рождения[города/губернатора]' в строке №" + linesCoiner);
                        }

                    } else if (Pattern.matches(regex2.trim(), line)) {
                        String[] lines = line.split("\\s*[;,]\\s*");
                        try {
                            Integer id = Integer.parseInt(lines[0].trim());
                            if(!IdGenerator.getIdSet().contains(id)){
                                IdGenerator.getIdSet().add(id);
                            }else{
                                System.out.println("Ошибка! Объект не добавлен! Неуникальное значение поля 'id' в строке №" + linesCoiner);
                                continue;
                            }
                            String name = lines[1].trim();
                            int x = Integer.parseInt(lines[2].trim());
                            float y = Float.parseFloat(lines[3].trim());
                            coordinates = new Coordinates(x, y);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            LocalDateTime date = LocalDateTime.parse(lines[4].trim(), formatter);
                            long area = Long.parseLong(lines[5].trim());
                            int population = Integer.parseInt(lines[6].trim());
                            Long metersAboveSeaLevel = Long.parseLong(lines[7].trim());
                            switch (lines[8].trim()) {
                                case "Тропическая саванна":
                                    climate = Climate.TROPICAL_SAVANNA;
                                    break;
                                case "Средиземноморье":
                                    climate = Climate.MEDITERRANEAN;
                                    break;
                                case "Полярный ледник":
                                    climate = Climate.POLAR_ICECAP;
                                    break;
                                case "null":
                                    climate = null;
                                    break;
                                default:
                                    System.out.println("Ошибка! Объект " + name + " не добавлен! Неверное значение поля 'Климат' в строке №" + linesCoiner);
                                    continue;
                            }
                            switch (lines[9].trim()) {
                                case "Критархия":
                                    government = Government.KRITARCHY;
                                    break;
                                case "Олигархия":
                                    government = Government.OLIGARCHY;
                                    break;
                                case "Тимократия":
                                    government = Government.TIMOCRACY;
                                    break;
                                case "Тоталитаризм":
                                    government = Government.TOTALITARIANISM;
                                    break;
                                case "null":
                                    government = null;
                                    break;
                                default:
                                    System.out.println("Ошибка! Объект " + name + " не добавлен! Неверное значение поля 'Тип правления' в строке №" + linesCoiner);
                                    continue;
                            }
                            switch (lines[10].trim()) {
                                case "Очень высокий":
                                    standardOfLiving = StandardOfLiving.ULTRA_HIGH;
                                    break;
                                case "Средний":
                                    standardOfLiving = StandardOfLiving.MEDIUM;
                                    break;
                                case "Низкий":
                                    standardOfLiving = StandardOfLiving.VERY_LOW;
                                    break;
                                case "Кошмарный":
                                    standardOfLiving = StandardOfLiving.NIGHTMARE;
                                    break;
                                default:
                                    System.out.println("Ошибка! Объект " + name + " не добавлен! Неверное значение поля 'Уровень жизни' в строке №" + linesCoiner);
                                    continue;
                            }
                            if (lines[11].trim().equals("null".trim())) {
                                governor = null;
                            } else {
                                System.out.println("Ошибка поля 'Губернатор' в строке №" + linesCoiner + "");
                            }
                            city = new City(id, name, coordinates, date, area, population, metersAboveSeaLevel, climate, government, standardOfLiving, governor);
                            collection.put(city.getId(), city);
                            linesCoiner += 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Объект не может быть инициализирован, так как числовое поле в №" + linesCoiner + " допущена ошикбка..");
                        } catch (StackOverflowError e) {
                            System.out.println("Объект не может быть инициализирован, так как коллекция объектов переполнена!");
                        } catch (DateTimeParseException e) {
                            System.out.println("Ошибка! Объект не добавлен! Неккоректное значение поля 'Дата рождения губернатора' в строке №" + linesCoiner);
                        }

                    } else {
                        System.out.println(System.lineSeparator() + "Ошибка файла:" + System.lineSeparator() + "Строке №" + linesCoiner + " имеет неправильный формат.."
                                + " Объект не может быть инициализироан." + System.lineSeparator() + "Пожалуйста, исправьте ошибку и попробуйте снова." + System.lineSeparator());
                    }

                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Ошибка! Файл не найден. Проверьте, инициализирована ли переменная окружения 'INPUT_LAB', которая хранит расположение файла.");

        }
        return collection;
    }



    int accessCheck(String path){
        file = new File(path);
        if (file.isDirectory()) {
            System.out.println("Необходим обязательный аргумент: Полное имя файла данных, не директория");
            return 1;
        }
        if (!file.exists()) {
            System.out.println("Файл не найден");
            return 1;
        }
        if (!file.canRead() && !file.canWrite()) {
            System.out.println("Ошибка доступа на чтение и на запись");
            return 1;
        }
        if (!file.canRead()) {
            System.out.println("Ошибка доступа на чтение");
            return 1;
        }
        if (!file.canWrite()) {
            System.out.println("Ошибка доступа на запись");
            return 1;
        }
        return 0;

    }
}



