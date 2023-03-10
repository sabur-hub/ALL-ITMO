package ClientFullPack.Screens_Controllers;

import ClientFullPack.RunClient;
import ClientFullPack.connection.Network;
import io.Message;
import io.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Languages;

    @FXML
    private TextField LoginAuth;

    @FXML
    private TextField PswrdAuth;

    @FXML
    private Button Auth;

    @FXML
    private Button Register;

    @FXML
    void initialize() {
        ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);
        Languages.setValue("Русский");

        Register.setOnAction(event->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("registerScene.fxml")); //загрузка экрана регистрации

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        Auth.setOnAction(event->{
            String login = LoginAuth.getText().trim();
            String pass = PswrdAuth.getText().trim();
            if(!login.isEmpty() && login.length() == login.replaceAll("[^A-Za-z0-9]", "").length()) {
                if (!pass.isEmpty() && pass.length() == pass.replaceAll("[^A-Za-z0-9]", "").length()) {
                    try {
                        Network network = new Network(RunClient.ip_adress, RunClient.port);

                        User user = new User(login,pass);

                        Message message = new Message("AUTHORIZATION",user,false);

                        network.write(message);
                        String outServer = network.read().toString();

                        if(outServer.equals(RunClient.AUTHORIZATION_ISSUCCESS)){
                            RunClient.login = login;
                            RunClient.pass = pass;
                            Auth.getScene().getWindow().hide();
                            Stage stage = new Stage();
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setTitle("Login");
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            LoginAuth.setText("");
                            PswrdAuth.setText("");
                            Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                            alert.setTitle("Error");
                            alert.setHeaderText("Ошибка входа");
                            alert.setContentText("Логин или пароль неверны");
                            alert.showAndWait().ifPresent(rs -> {
                            });
                        }



                    }catch (IOException | ClassNotFoundException e) {
                        LoginAuth.setText("");
                        PswrdAuth.setText("");
                        Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                        alert.setTitle("Error");
                        alert.setHeaderText("Ошибка подключения");
                        alert.setContentText("Ошибка подключения!!!\nМожно пойти перекурить)");
                        alert.showAndWait().ifPresent(rs -> {
                        });
                    }
                }else{
                    LoginAuth.setText("");
                    PswrdAuth.setText("");
                    Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                    alert.setTitle("Error");
                    alert.setHeaderText("Ошибка ввода Адреса/Порта");
                    alert.setContentText("Проверьте правильность ввода пароля!");
                    alert.showAndWait().ifPresent(rs -> {
                    });
                }
            }else{
                LoginAuth.setText("");
                PswrdAuth.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                alert.setTitle("Error");
                alert.setHeaderText("Ошибка ввода Адреса/Порта");
                alert.setContentText("Проверьте правильность ввода логина!");
                alert.showAndWait().ifPresent(rs -> {
                });
            }
        });
    }
}
