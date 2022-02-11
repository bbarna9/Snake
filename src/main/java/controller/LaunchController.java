package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
/**
 *
 * {@code LaunchController}A screen-en megjelenő vizuális eszközökkel történő műveletek, események itt történnek kezelésre(eventek), továbbá
 * itt tudunk módosítani a képernyő tartalmán.
 *
 */


public class LaunchController {
    /**
     * {@code STANDARDRD}Alapvető érték definiálása a gombok számára
     */
    private static final String STANDARD= "-fx-border-color: black;";

    /**
     * {@code startButton} A képernyőn látható vizuális eszköz példányosítása, az fxml-ből érjük el id alapján
     */


    @FXML
    private Button startButton;

    /**
     * {@code quitButton} Kilépés a játékból
     */

    @FXML
    private Button quitButton;


    /**
     * {@code startAction()}Egy gomb megnyomásának hatására lefut a metódus amiben betöltjük az új fxml-t.
     * @param actionEvent A gombhoz tartozó esemény.
     * @throws IOException A lefutása közben adódó Input-Output kivétel.
     */

    public void startAction(ActionEvent actionEvent) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            Logger.info("Betöltődik a Játék felület.");


    }

    public void quitGame(ActionEvent actionEvent) throws IOException{
        Logger.info("Bezáródik a Snake játék.");
        Platform.exit();

    }

    /**
     * {@code initialize()}Ez a metódus fut le először, a konstruktorhoz nagyon hasonló, azonban innen elérjük az fxml fájl tagjait.
     */
    @FXML
    public void initialize(){

        startButton.setStyle(STANDARD);
        quitButton.setStyle(STANDARD);
        Logger.info("Betöltődik az LaunchController initialize() függvényben szereplő Button és Textfield vizuális beállítása.");


    }

}
