import connection.Network;
import exceptions.ArgumentException;
import io.Message;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

public class Main {
    private static String address = "localhost";
    private static Integer port;


    public static void main(String[] args) {
        try {
            port = new Integer(args[0]);
            if (port < 1023 || port > 65535) {
                System.out.println("Вы выбрали несуществующий или занятый системой порт. Выберите другой и попробуйте заново");
                System.exit(0);
            }
            welcomeMessage();
            UserResponseValidator userResponseValidator;
            Network network;
            do {
                try {
                    userResponseValidator = new UserResponseValidator();
                    Message message = userResponseValidator.validate();
                    if (userResponseValidator.getCommandName() != null) {
                        network = new Network(address, port);
                        network.write(message);
                        ArrayList<String> resopnse = (ArrayList) network.read();
                        if (resopnse.contains("00101110010000010011001000111101")) {
                            farewellMessage();
                            System.exit(0);
                        }
                        resopnse.forEach(System.out::println);
                        network.closeStream();
                    } else {
                    }

                } catch (EOFException | ConnectException e) {
                    System.out.println("Сервер недоступен в данный момент.. Проверьте правильность введенного адреса и порта подключения и попробуйте заново");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace(System.out);
                } catch (ArgumentException e) {
                    System.out.println(e.getMessage());
                    ;
                } catch (NumberFormatException e) {
                    System.out.println("Введенный вами порт имееет неприавльный формат!");
                    System.exit(0);
                }
            } while (true);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Порт подключения не указан!");
            System.exit(0);
        }
    }


        public static void welcomeMessage () {
            System.out.println("Добрый День! Добро пожаловать в консольное приложение 'CityBuilder'!" + System.lineSeparator() +
                    "by Дзюбак Антон и Митрофанов Егор - P3114 " + System.lineSeparator() +
                    System.lineSeparator() + "Пожалуйста, введи 'help' в консоль, чтобы ознакомиться с функционалом приложения" + System.lineSeparator());
        }
        public static void farewellMessage () {
            System.out.println("          ／＞　 フ \n" +
                    "　　　　　| 　о　 _| \n" +
                    "　 　　　／`ミ _x 彡    | Пока - пока:3 |\n" +
                    "　　 　 /　　　 　 | \n" +
                    "　　　 /　 ヽ　　 ﾉ \n" +
                    "　／￣|　　 |　|　| \n" +
                    "　| (￣ヽ＿_ヽ_)_) \n" +
                    "　＼二つ");
            System.exit(0);
        }
    }
