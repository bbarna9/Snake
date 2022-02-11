package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;
import model.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import org.tinylog.Logger;
import java.io.IOException;

@Data
public class SnakeController{
    private String username;
    private OperandSymbols operandSymbol;
    private Operation operation;
    private static final String STANDARD= "-fx-border-color: black;";

    private static final int BOARDWIDTH = 15;
    private static final int BOARDHEIGHT = 8;
    private static final int RECTSIZE = 37;
    private Game game;
    private SnakeBody snakeBody;

    private Stage primaryStage;
    private Timeline clock;

    @FXML
    private Button startButton;

    @FXML
    private Button button;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label operationLabel;

    private void play() {
        game = new Game(BOARDWIDTH, BOARDHEIGHT, operandSymbol);

        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            refreshBoard();
        }), new KeyFrame(Duration.seconds(0.8)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        Scene scene = gameGrid.getScene();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            game.changeDirecton(key);
        });

    }

    public void refreshBoard(){
        game.move(operandSymbol);

        OperandSymbols operandSymbols;

        operation = game.getOperation();
        operandSymbols=operation.getOperandsymbol();
        String operationText = operation.getOperationAsText(operandSymbols);
        operationLabel.setText(operationText);

        ObservableList<Node> childrens = gameGrid.getChildren();
        for(Node node : childrens) {
            if(node instanceof Rectangle) {
                //Rectangle rect= new Rectangle(node); // use what you want to remove
                gameGrid.getChildren().remove(node);
                break;
            }
        }

        for (int y = 0; y < BOARDHEIGHT; y++) {
            for (int x = 0; x < BOARDWIDTH; x++) {
                final int Y = y;
                final int X = x;

                Rectangle baseRect = new Rectangle(RECTSIZE, RECTSIZE);
                baseRect.setFill(Color.rgb(233,233,233));
                baseRect.setStroke(Color.BLACK);

                Rectangle foodRect = new Rectangle(RECTSIZE, RECTSIZE);
                foodRect.setFill(Color.rgb(215,87,87));
                foodRect.setStroke(Color.BLACK);

                Rectangle snakeRect = new Rectangle(RECTSIZE, RECTSIZE);
                snakeRect.setFill(Color.rgb(60,118,30));
                snakeRect.setStroke(Color.BLACK);

                GridPane.setRowIndex(baseRect, y);
                GridPane.setColumnIndex(baseRect, x);
                //System.out.print(gameGrid.getChildren().removeAll(baseRect, snakeRect, foodRect));
                gameGrid.getChildren().add(baseRect);

                game.getFoods().stream().forEach(food -> {
                    if(food.getFoodcoordinate().getY() == Y && food.getFoodcoordinate().getX() == X){
                        Text solution = new Text(Integer.toString(food.getValue()));
                        solution.setWrappingWidth(RECTSIZE);
                        solution.setTextAlignment(TextAlignment.CENTER);
                        GridPane.setRowIndex(foodRect, food.getFoodcoordinate().getY());
                        GridPane.setColumnIndex(foodRect, food.getFoodcoordinate().getX());
                        if(!gameGrid.getChildren().contains(foodRect)){
                            gameGrid.getChildren().add(foodRect);
                            gameGrid.add(solution, X, Y);
                            gameGrid.setStyle("-fx-font-size: 20px;" + "-fx-text-fill: black;" + "-fx-font-weight: bold;");
                        }
                        else
                            Logger.info("NEM GENERÁLÓDOTT HARMADIK KAJA------");
                    }
                });

                game.getSnakeCoords().stream().forEach(pair -> {
                    if(pair.getY() == Y && pair.getX() == X) {
                        if(!gameGrid.getChildren().contains(snakeRect)) {
                            GridPane.setRowIndex(snakeRect, pair.getY());
                            GridPane.setColumnIndex(snakeRect, pair.getX());
                            gameGrid.getChildren().add(snakeRect);
                        }
                        else
                            gameOver();
                    }
                });

                scoreLabel.setText(Integer.toString(game.getScore()));
            }
        }

        if (game.isOver()) {
            Logger.info("Vége a játéknak--------------------------");
            gameOver();
            return;
        }

    }

    private void gameOver() {
        clock.stop();
        try {
            Database.addUsertoDB(new User(username, game.getScore(), operandSymbol.name()));
            Logger.info("Feltöltjük az adatokat adatbázisba--------------------------");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameend.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }


    }

    public void gameStarted(){
        startButton.setVisible(false);
        startButton.setDisable(true);
        gameGrid.setVisible(true);
        gameGrid.setDisable(false);
        operationLabel.setVisible(true);
        scoreLabel.setVisible(true);
        play();
    }

    public void initData(String un, OperandSymbols os){
        this.username = un;
        this.operandSymbol = os;
    }
}
