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
 * {@code EndController}A screen-en megjelenő vizuális eszközökkel történő műveletek, események itt történnek kezelésre(eventek), továbbá
 * itt tudunk módosítani a képernyő tartalmán.
 *
 */


public class EndController {
    /**
     * {@code STANDARDRD}Alapvető érték definiálása a gombok számára
     */
    private static final String STANDARD= "-fx-border-color: black;";

    /**
     * {@code restartButton, leaderboardButton,quitButton} A képernyőn látható vizuális eszköz példányosítása, az fxml-ből érjük el id alapján
     */


    @FXML
    private Button restartButton;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Button quitButton;


    /**
     * {@code restartAction()}Egy gomb megnyomásának hatására lefut a metódus amiben betöltjük az új fxml-t.
     * @param actionEvent A gombhoz tartozó esemény.
     * @throws IOException A lefutása közben adódó Input-Output kivétel.
     */

    public void restartAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Betöltődik a felhasználónév beviteli felület.");


    }

    public void leaderboardAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/score.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Betöltődik a eredményjelző felület.");


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

        restartButton.setStyle(STANDARD);
        leaderboardButton.setStyle(STANDARD);
        quitButton.setStyle(STANDARD);
        Logger.info("Betöltődik az EndController initialize() függvényben szereplő Button-ok vizuális beállítása.");


    }

}
