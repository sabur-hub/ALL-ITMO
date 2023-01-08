package app.csv;

import app.ApplicationManager;
import app.collection.City;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

/**
 * Класс, который занимается своеобразной сериализацией объектов в файл.
 * Поля объектов записываются в строчку в формате CSV
 */

public class CSVWriter {
    public void write(TreeMap<Integer, City> treeMap) throws IOException {
        File file = new File(ApplicationManager.path);
        if (file.isDirectory()) {
            System.out.println("Необходим обязательный аргумент: Полное имя файла данных, не директория");
        }
        if (!file.exists()) {
            System.out.println("Файл не найден");
        }
        if (!file.canRead() && !file.canWrite()) {
            System.out.println("Ошибка доступа на чтение и на запись");
        }
        if (!file.canRead()) {
            System.out.println("Ошибка доступа на чтение");
        }
        if (!file.canWrite()) {
            System.out.println("Ошибка доступа на запись");
        } else {
            FileWriter fileWriter = new FileWriter(file);
            for (Map.Entry<Integer, City> pair : treeMap.entrySet()) {
                City v = pair.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String cityCreationDate = v.getCreationDate().format(formatter);
                if (v.getClimate() == null) {
                    if (v.getGovernment() == null) {
                        if (v.getGovernor() == null) {
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + "null" +
                                    " , " + "null" + " , " + v.getStandardOfLiving().getRussianName() + " , " + "null" + System.lineSeparator());
                        } else {
                            String governorBirthday = v.getGovernor().getDateOfBirthday().format(formatter);
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + "null" +
                                    " , " + "null" + " , " + v.getStandardOfLiving().getRussianName() + " , " + v.getGovernor().getAge() + " , " + governorBirthday + System.lineSeparator());
                        }

                    } else {
                        if (v.getGovernor() == null) {
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + "null" +
                                    " , " + v.getGovernment().getRussianName() + " , " + v.getStandardOfLiving().getRussianName() + " , " + "null" + System.lineSeparator());
                        } else {
                            String governorBirthday = v.getGovernor().getDateOfBirthday().format(formatter);
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + "null" +
                                    " , " + v.getGovernment().getRussianName() + " , " + v.getStandardOfLiving().getRussianName() + " , " + v.getGovernor().getAge() + " , " + governorBirthday + System.lineSeparator());
                        }
                    }
                } else {
                    if (v.getGovernment() == null) {
                        if (v.getGovernor() == null) {
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + v.getClimate().getRussianName() +
                                    " , " + "null" + " , " + v.getStandardOfLiving().getRussianName() + " , " + "null" + System.lineSeparator());
                        } else {
                            String governorBirthday = v.getGovernor().getDateOfBirthday().format(formatter);
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + v.getClimate().getRussianName() +
                                    " , " + "null" + " , " + v.getStandardOfLiving().getRussianName() + " , " + v.getGovernor().getAge() + " , " + governorBirthday + System.lineSeparator());
                        }

                    } else {
                        if (v.getGovernor() == null) {
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + v.getClimate().getRussianName() +
                                    " , " + v.getGovernment().getRussianName() + " , " + v.getStandardOfLiving().getRussianName() + " , " + "null" + System.lineSeparator());
                        } else {
                            String governorBirthday = v.getGovernor().getDateOfBirthday().format(formatter);
                            fileWriter.write(v.getId() + " , " + v.getName() + " , " + v.getCoordinates().getX() + " , " + v.getCoordinates().getY() + " , " +
                                    cityCreationDate + " , " + v.getArea() + " , " + v.getPopulation() + " , " + v.getMetersAboveSeaLevel() + " , " + v.getClimate().getRussianName() +
                                    " , " + v.getGovernment().getRussianName() + " , " + v.getStandardOfLiving().getRussianName() + " , " + v.getGovernor().getAge() + " , " + governorBirthday + System.lineSeparator());
                        }
                    }
                }

            }
            fileWriter.close();
            System.out.println("Коллекция успешно записана!");
        }

    }
}


