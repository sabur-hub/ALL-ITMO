package app;


import java.io.StreamCorruptedException;
import java.net.BindException;

public class Start {

    private static int PORT;


    public static void main(String[] args) {
        try {
            PORT = new Integer(args[0]);
            ApplicationManager applicationManager = new ApplicationManager();
            applicationManager.initialisation();
        } catch (BindException e) {
            System.out.println("Этот порт уже занят! Попробуй занять другой!");
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println("Номер порта имеет неправильный формат, введите порт корректно.");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Порт подключения не указан!");
            System.exit(0);
        } catch (StreamCorruptedException | NullPointerException e) {
            System.out.println("Неизветное серверу подключение.Попробуйте заново!");
        } catch (Exception e) {
            System.out.println("Возникла ошибка. Возможно ты пытаешься подключиться к занятом порту. Измени порт и попробуй заново.");
            System.exit(0);
        }
    }

    public static int getPORT() {
        return PORT;
    }
}
