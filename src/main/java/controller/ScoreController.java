package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import model.Database;
import model.User;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;

/**
 * {@code ScoreController} Itt törtnik az adatbázis megjelenítése a képernyőn.
 */
public class ScoreController {

    /**
     * {@code STANDARD} Végleges változó a gomb tulajdonságának beállításához.
     */
    private static final String STANDARD= "-fx-border-color: black;";


    /**
     * {@code quitButton,lastgame,topgame,id,username,score,topid,topusername,topscore} A képernyőn megjelenítésre kerülő elemek példányosítása.
     */
    @FXML
    private Button quitButton;
    @FXML
    private Button restartButton;

    @FXML
    private TableView<User> lastgame;
    @FXML
    private TableView<User> topgame;

    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, Integer> score;
    @FXML
    private TableColumn<User, Integer> gamemode;
    @FXML
    private TableColumn<User, Integer> topid;
    @FXML
    private TableColumn<User, String> topusername;
    @FXML
    private TableColumn<User, Integer> topscore;
    @FXML
    private TableColumn<User, Integer> topgamemode;

    /**
     * {@code quitGame()} A kilépéshez használt függvény.
     * @param actionEvent Esemény következtében fut le.
     * @throws IOException IO kivételt dobhat.
     */

    @FXML
    public void quitGame(ActionEvent actionEvent) throws IOException{
        Logger.info("Bezáródik a Quiz.");
        Platform.exit();

    }

    /**
     * {@code writeStandardData()} A standard adatbázis kiíratását oldjuk meg vele.
     */
    public void writeStandardData(){
        Logger.info("Standart adatbázis betöltődik.");
        List<User>userlist = Database.getUsers();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        gamemode.setCellValueFactory(new PropertyValueFactory<>("gamemode"));
        ObservableList<User> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(userlist);

        lastgame.setItems(observableResult);

    }

    /**
     * {@code writeTopTenData()} A Top10-es adatbázis kiíratását oldjuk meg vele.
     */
    public void writeTopTenData(){
        Logger.info("Top10-es adatbázis betöltődik.");
        List<User>userlist = Database.getTopTen();

        topid.setCellValueFactory(new PropertyValueFactory<>("id"));
        topusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        topscore.setCellValueFactory(new PropertyValueFactory<>("score"));
        topgamemode.setCellValueFactory(new PropertyValueFactory<>("gamemode"));
        ObservableList<User> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(userlist);

        topgame.setItems(observableResult);

    }

    /**
     * {@code restartAction()}Egy gomb megnyomásának hatására lefut a metódus amiben betöltjük az új fxml-t.
     * @param actionEvent A gombhoz tartozó esemény.
     * @throws IOException A lefutása közben adódó Input-Output kivétel.
     */
    public void restartAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gamemode.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Betöltődik a játékmódválasztó felület.");


    }
    /**
     * {@code initialize()}Ez a metódus fut le először, a konstruktorhoz nagyon hasonló, azonban innen elérjük az fxml fájl tagjait.
     */
    @FXML
    public void initialize(){
        quitButton.setStyle(STANDARD);
        restartButton.setStyle(STANDARD);
        Logger.info("Button-ok vizuális beállítása.");
        writeStandardData();
        Logger.info("Standart adatbázis betöltődött.");
        writeTopTenData();
        Logger.info("Top10-es adatbázis betöltődött.");






    }

}
