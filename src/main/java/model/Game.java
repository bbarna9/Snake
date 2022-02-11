package model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Magáért a játékért felelős osztály. Itt használjuk a többi osztályt, amit létrehoztunk.
 */
public class Game {
    /**
     * {@code snake,foods, randomGenerator,score,boardHeight,boardWidth}
     */
    private Snake snake;
    private ArrayList<Food> foods;
    private Random randomGenerator;
    private Operation operation;
    private int score;

    private final int boardHeight;
    private final int boardWidth;

    /**
     * Az osztály konstruktora.
     * @param boardWidth Szélesség.
     * @param boardHeight Magasság.
     * @param operandSymbol Operátor.
     */
    public Game(int boardWidth, int boardHeight, OperandSymbols operandSymbol){
        operation = null;
        snake = new Snake(boardWidth, boardHeight);
        randomGenerator = new Random(0);
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.foods = generateFood(operandSymbol);
    }

    /**
     * Operátor-t vár ami alapján előáálítja a kajákat.
     * @param operandSymbol
     */
    private ArrayList<Food> generateFood(OperandSymbols operandSymbol){
        ArrayList<Food> foods1 = new ArrayList<>();
        Logger.info("Kaja generálódik");
        operation = new Operation(operandSymbol);
        System.out.print(operation.getOperandsymbol());
        ArrayList<Coordinate> availablecoordinates = new ArrayList<>();
        for (int y = 0; y <  boardHeight- 1; y++) {
            for (int x = 0; x < boardWidth- 1 ; x++) {
                if(!snake.getBodyXs().contains(x) && !snake.getBodyYs().contains(y)){
                    availablecoordinates.add(new Coordinate(x,y));
                }
            }
        }
        Food result = new Food(operation.getResult(),availablecoordinates,true);
        result = makeDifferentValue(operation, result, availablecoordinates, foods1);
        foods1.add(result);
        System.out.print("EZ A TÖRLÉS EREDMÉNYE: " + availablecoordinates.remove(result.getFoodcoordinate()));
        for(int i =0; i< 2; i++){
            Food food = new Food(operation.getResult(), availablecoordinates, false);
            System.out.print("A MÉRETE: "+food+"             ");
            food = makeDifferentValue(operation, food, availablecoordinates, foods1);
            foods1.add(food);
            System.out.print("EZ A MÁSIK TÖRLÉS EREDMÉNYE: " + availablecoordinates.remove(food.getFoodcoordinate()));
        }
        foods = foods1;
        System.out.print(foods1);
        return foods1;
    }


    /**
     * Mozgásért felelős metódus ami szintén egy operátort vár paraméterül a kajagenerálás miatt.
     * @param operandSymbol
     */
    public void move(OperandSymbols operandSymbol){

        if(snake.getHeadCoordinates().isEqual(foods.get(0).getFoodcoordinate()
        )) {
            score +=10;
            snake.moveDirection();
            generateFood(operandSymbol);
        }
        else if(snake.getHeadCoordinates().isEqual(foods.get(1).getFoodcoordinate())||snake.getHeadCoordinates().isEqual(foods.get(2).getFoodcoordinate())){
            Coordinate lastBody = snake.getBodyAsNodes().getTail().clone();
            snake.getBodyAsNodes().snakeGrow(lastBody);
            snake.moveDirection();
            generateFood(operandSymbol);
        }

        else {
            snake.moveDirection();
        }
    }

    /**
     * Iránybváltoztatást végző metódus.
     * @param keyEvent Billentyűlenyoomást vár.
     */
    public void changeDirecton(KeyEvent keyEvent){
        KeyCode keyCode = keyEvent.getCode();
        Logger.info("Menetirányt változtatott a kígyó");
        switch (keyCode){
            case UP:
                snake.setDirection(Directions.UP);
                break;

            case RIGHT:
                snake.setDirection(Directions.RIGHT);
                break;

            case DOWN:
                snake.setDirection(Directions.DOWN);
                break;

            case LEFT:
                snake.setDirection(Directions.LEFT);
                break;

            default:
                break;
        }
    }

    /**
     * Lehetővé teszi, hogy ne generálódjon azonos értékű kaja.
     * @param operation Művelet.
     * @param food Kaja.
     * @param availablecoordinates Elérhető koordináták halmaza.
     * @return  Kajával tér vissza.
     */
    public Food makeDifferentValue(Operation operation, Food food, ArrayList<Coordinate> availablecoordinates, ArrayList<Food> foods){
        boolean differentvalue = true;
        for(int i = 0; i< foods.size(); i++ ){
            if(foods.get(i).getValue() == food.getValue()){
                differentvalue = false;
                break;
            }
            
        }
        if(differentvalue == false){
            food = new Food(operation.getResult(), availablecoordinates, false);
            food = makeDifferentValue(operation,food, availablecoordinates, foods);

        }

        return food;
    }

    /**
     *
     * @return Visszatér a kígyó koordinátáinak halmazával.
     */
    public ArrayList<Coordinate> getSnakeCoords(){
        return snake.getBodyAsCoordinates();
    }

    /**
     * Vége-e a játéknak?
     * @return Igazzal tér vissza ha vége, egyébként hamissal.
     */
    public Boolean isOver() {
        Logger.info("Vége-e a játéknak?");
        return snake.checkIfSnakeCrashed();
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public Operation getOperation() { return operation; }

    public int getScore() {
        return score;
    }
}
