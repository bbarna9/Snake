package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class UserController {
    /**
     * {@code STANDARD} Végleges változó a gomb tulajdonságának beállításához.
     */
    private static final String STANDARD= "-fx-border-color: black;";
    /**
     * {@code continueButton,userName,errorLabel} Az fxml elemeinek pédányositása.
     */
    @FXML
    private Button continueButton;

    @FXML
    private TextField userName;

    @FXML
    private Label errorLabel;

    /**
     *
     * @param actionEvent Egy gomb megnyomásának hatására lefut a metódus amiben betöltjük az új fxml-t.
     * @throws IOException Input-Output kivételt  dobhat a metódus.
     */

    public void continueAction(ActionEvent actionEvent) throws IOException {
        if (!userName.getText().isEmpty()){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gamemode.fxml"));
            Parent root = fxmlLoader.load();
            GameModeController controller = fxmlLoader.getController();
            controller.setUserName(userName.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            Logger.info("Betölődik a játékmód kiválasztásáért felelős felület.");

        }else{

            errorLabel.setText("Játékos neve üres");
            Logger.warn("Játékos neve üres");

        }
    }

    /**
     * {@code initialize()}Ez a metódus fut le először, a konstruktorhoz nagyon hasonló, azonban innen elérjük az fxml fájl tagjait.
     */
    public void initialize(){

        continueButton.setStyle(STANDARD);
        Logger.info("Betöltődik az LaunchController initialize() függvényben szereplő Button és Textfield vizuális beállítása.");


    }

}
