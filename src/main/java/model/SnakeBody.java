package model;

import org.tinylog.Logger;

import java.util.ArrayList;

/**
 * {@code SnakeBody} A kígyó testét megvalósító objektum.
 */
public class SnakeBody {

    private boolean isDead;
    private ArrayList<Coordinate> nodes;

    /**
     * Osztály konstruktora.
     * @param head A kígyó fejrésze.
     */
    public SnakeBody(Coordinate head){
        nodes = new ArrayList<>(1);
        nodes.add(head);
    }

    /**
     * Kígyó növelése.
     * @param newTail Új farok.
     */
    public void snakeGrow(Coordinate newTail){
        nodes.add(newTail);
    }

    /**
     *
     * A kígyó mérete.
     * @return Integer, ami a kihgyó mérete.
     */
    public Integer size(){
        return nodes.size();
    }

    /**
     * Fejének lekérdezése
     * @return Visszaadja a fej koordinátáit.
     */

    public Coordinate getHead(){
        return nodes.get(0);
    }

    /**
     * Farok lekérdezése.
     * @return Farok koordinátái.
     */
    public Coordinate getTail(){
        return nodes.get(size()-1);
    }

    /**
     * Ütközött e a kígyó
     * @param maxX A pálya egyik széle
     * @param maxY A pálya másik széle.
     * @return Boolean, ami igazat ad, ha ütközött, egyébként hamisat.
     */
    public boolean collision(int maxX, int maxY){
        Logger.info("Meghalt a kígyó?");
        isDead |= isSelfCollision() || collidesWithWall(maxX, maxY);
        return isDead;
    }

    /**
     * Magába ütközött-e a kígyó.
     * @return Igaz ha magába ütközött, egyébként hamis.
     */
    public boolean isSelfCollision() {
        Logger.info("Magába ütközött a kígyó?");
        Coordinate head = nodes.get(0);
        boolean collides = false;
        for (int i = 1; i < size(); i++) {
            if (head.isEqual(nodes.get(i)))
                collides = true;
        }
        return collides;
    }

    /**
     * Fallba ütközött-e a kígyó.
     * @param maxX A pálya egyik széle.
     * @param maxY A pálya másik széle.
     * @return Igaz, ha falba ütközött, egyébként hamis.
     */
    private Boolean collidesWithWall(int maxX , int maxY){
        Logger.info("Falba ütközött a kígyó?");
        Coordinate head = nodes.get(0);
        return (head.getX() < 0 || head.getX() >= maxX || head.getY() < 0 || head.getY() >= maxY);
    }

    /**
     * Koordinátái a kígyó testének.
     * @return Koordinták listája.
     */
    public ArrayList<Coordinate> getNodes(){
        return nodes;
    }

    /**
     *
     * @return A kígyó X koordinátáit gyűjti ki, és tér vele vissza.
     */
    public ArrayList<Integer> getXs(){
        ArrayList<Integer> xOrds = new ArrayList<>(nodes.size());
        for (Coordinate node: getNodes()) {
            xOrds.add(node.getX());
        }
        return xOrds;
    }
    /**
     *
     * @return A kígyó Y koordinátáit gyűjti ki, és tér vele vissza.
     */
    public ArrayList<Integer> getYs(){
        ArrayList<Integer> yOrds = new ArrayList<>(nodes.size());
        for (Coordinate node: getNodes()) {
            yOrds.add(node.getY());
        }
        return yOrds;
    }
}
