import app.collection.building.CityBuilder;
import exceptions.ArgumentException;
import io.ConsoleReader;
import io.Message;

import java.io.IOException;
import java.util.Timer;
import java.util.regex.Pattern;

public class UserResponseValidator {

    private String commandName;
    private ConsoleReader consoleReader;
    private CityBuilder cityBuilder;

    public UserResponseValidator() throws IOException {
        consoleReader = new ConsoleReader();
        cityBuilder = new CityBuilder();
    }

    public String getCommandName() {
        return commandName;
    }

    public Message validate() throws IOException {


        while (true) {
            consoleReader.getDefaultConsoleSymbol();
            String line = consoleReader.getScanner().nextLine().trim();
            if (line.trim().equals("")) {
                break;
            }
            String[] request = line.split(" ");
            commandName = request[0].toLowerCase();
            switch (commandName) {
                case "add":
                    if (request.length < 2) {
                        return new Message(Commands.ADD.getCommandName(), new CityBuilder().create());
                    } else {
                        throw new ArgumentException();
                    }
                case "average_of_meters_above_sea_level":
                    System.out.println("Команда avarage_above_sea_level успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.AVARAGEABOVESEALVL.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "clear":
                    System.out.println("Команда clear успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.CLEAR.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "execute_script":
                    System.out.println("Команда execute_script успешно выбрана");
                    String regexArg = "\\/\\[w,W]+\\.*\\w*";
                    if (request.length == 2 ) {
                        if(Pattern.matches(commandName,regexArg)){
                            return new Message(Commands.EXECUTESCRIPT.getCommandName(), request[1].trim());
                        }else throw new ArgumentException();
                    } else {
                        throw new ArgumentException();
                        //  && Pattern.matches(regexArg,request[1].trim())
                    }
                case "exit":
                    System.out.println("Команда exit успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.EXIT.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "help":
                    System.out.println("Команда help успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.HELP.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "insert":
                    System.out.println("Команда insert успешно выбрана");
                    if (request.length == 2) {
                        try {
                            Integer arg = new Integer(request[1].trim());
                        }catch (NumberFormatException e){
                            System.out.println("У команда " + Commands.UPDATEID.getCommandName() + " аргумент - целое число!");
                            break;
                        }
                        return new Message(Commands.INSERT.getCommandName(), request[1], new CityBuilder().create());
                    } else {
                        throw new ArgumentException();
                    }
                case "min_by_climate":
                    System.out.println("Команда min_by_climate успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.MINBYCLIMATE.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "print_descending":
                    System.out.println("Команда print_descending успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.PRINTDESCENDING.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "remove_greater":
                    System.out.println("Команда insert успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.REMOVEGREATER.getCommandName(), new CityBuilder().create());
                    } else {
                        throw new ArgumentException();
                    }
                case "remove_lower":
                    System.out.println("Команда remove_lower успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.REMOVELOVER.getCommandName(), new CityBuilder().create());
                    } else {
                        throw new ArgumentException();
                    }
                case "replace_if_greater":
                    System.out.println("Команда replace_if_greater успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.REPLACEIFGERATER.getCommandName(), new CityBuilder().create());
                    } else {
                        throw new ArgumentException();
                    }
                case "show":
                    System.out.println("Команда show успешно выбрана");
                    if (request.length < 2) {
                        return new Message(Commands.SHOW.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "update_id":
                    System.out.println("Команда update_id успешно выбрана");
                    if (request.length == 2) {
                        try {
                            Integer arg = new Integer(request[1].trim());
                        }catch (NumberFormatException e){
                            System.out.println("У команда " + Commands.UPDATEID.getCommandName() + " аргумент - целое число!");
                            break;
                        }
                        return new Message(Commands.UPDATEID.getCommandName(), request[1].trim(), new CityBuilder().create());
                    } else {
                        throw new ArgumentException();
                    }
                case "info":
                    if (request.length < 2) {
                        return new Message(Commands.INFO.getCommandName());
                    } else {
                        throw new ArgumentException();
                    }
                case "^D":
                    System.exit(0);
                default:
                    System.out.println("Вы ввели непрвильную команду. Проверьте правильность введенной команды.");
                    break;
            }
        }
        return null;
    }
}
