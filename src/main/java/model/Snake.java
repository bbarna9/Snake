package model;


import org.tinylog.Logger;

import java.util.ArrayList;

/**
 * {@code Snake} magát a kígyót tartalmazó objektum.
 */
public class Snake {
    private SnakeBody body;
    private Directions direction;

    private final int boardHeight;
    private final int boardWidth;

    /**
     * A {@link Snake} osztály kontruktora.
     * @param boardWidth A pálya hossza.
     * @param boardHeight A pálya magassága.
     */
    public Snake(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        int middleX = boardWidth / 2;
        int middleY = boardHeight / 2;
        body = new SnakeBody(new Coordinate(middleY, middleX));
        direction = Directions.RIGHT;
        Logger.info("Létrejön a kígyó");
    }

    /**
     * {@code moveDirection} Az irányváltoztatást valósítja meg.
     */
    public void moveDirection(){
        switch (direction){
            case UP:
                incrimentBody(-1,0);
                break;
            case DOWN:
                incrimentBody(1, 0);
                break;
            case LEFT:
                incrimentBody(0, -1);
                break;
            case RIGHT:
                incrimentBody(0, 1);
                break;
            default: break;
        }

    }

    /**
     * {code incrimentBody} A kígyó hosszának növelése.
     * @param yIncriment Y koordináta.
     * @param xIncriment X koordináta.
     */
    private void incrimentBody(int yIncriment, int xIncriment){
        ArrayList<Coordinate> nodes = body.getNodes();
        for (int i = body.size()-1; i > 0; i--) {
            nodes.set(i, nodes.get(i-1).clone());
        }
        Coordinate head = body.getHead();
        head.setY(head.getY()+yIncriment);
        head.setX(head.getX()+xIncriment);
        Logger.info("Nőtt a kígyó hossza.");
    }
    /**
     {code checkIfSnakeCrashed} Megnézzük, hogy összeütközött-e a kígyó.
     **/
    public Boolean checkIfSnakeCrashed(){
        if(body.collision(boardWidth, boardHeight) || body.isSelfCollision())
            return true;
        else
            return false;
    }

    /**
     * Getter metódus
     * @return Kígyó testével(Objektum) tér vissza.
     */
    public SnakeBody getBodyAsNodes(){
        return body;
    }

    /**
     *
     * {code setDirection, getBodyAsCoordinates,getHeadCoordinates,getBodyXs, getBodyYs}
     * Setter, illetve getter metódusok.
     */
    public void setDirection(Directions direction){this.direction = direction;}

    public ArrayList<Coordinate> getBodyAsCoordinates(){return body.getNodes();}

    public  Coordinate getHeadCoordinates() {return body.getHead(); }

    public ArrayList<Integer> getBodyYs(){return body.getYs();}

    public ArrayList<Integer> getBodyXs() { return body.getXs();}


}