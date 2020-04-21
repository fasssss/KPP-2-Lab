package sample.model;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;


public class Player {
    public int velocity = 10;
    final int UP = 1;
    final int DOWN = 2;
    final int RIGHT = 3;
    final int LEFT = 4;
    String path = new File("").getAbsolutePath();
    Rectangle playerModel = new Rectangle();
    Rectangle taleModelTop = new Rectangle();
    List<Rectangle> taleModel = new ArrayList<Rectangle>();
    public Group tlTop = new Group(taleModelTop);
    public Group playerSub = new Group(tlTop, playerModel);
    String pathModel;
    public double[] posMem = {0, 0};
    public double[] tale = new double[10000];
    public int n = 0;
    public double score;
    private double tempScore = 0;
    private int multiTaleInc = 0;

    public boolean borders() {
        if (posMem[0] < 0 || posMem[0] > 1900 + 50 || posMem[1] < 0 || posMem[1] > 1000 + 50) {
            score = -100;
            return true;
        }
        return false;
    }

    public boolean bite() {
        for (int i = 20; i <= n * 2; i += 2) {
            if (posMem[0] > tale[i] - 25 && posMem[0] < tale[i] + 50 && posMem[1] > tale[i + 1] - 25 && posMem[1] < tale[i + 1] + 50) {
                score = -100;
                return true;
            }
        }
        return false;
    }

    public void incTale() {
        if (n < 50) {
            taleModel.add(n, new Rectangle());
            tlTop.getChildren().addAll(taleModel.get(n));
            taleModel.get(n).setX(50);
            taleModel.get(n).setY(50);
            taleModel.get(n).setWidth(50);
            taleModel.get(n).setHeight(50);
            taleModel.get(n).setFill(Color.GREEN);
            n++;
            tempScore = score;
        }
        if (tempScore != score) {
            if (multiTaleInc < 6) {
                taleModel.add(n, new Rectangle());
                tlTop.getChildren().addAll(taleModel.get(n));
                taleModel.get(n).setX(tale[20]);
                taleModel.get(n).setY(tale[21]);
                taleModel.get(n).setWidth(50);
                taleModel.get(n).setHeight(50);
                taleModel.get(n).setFill(Color.GREEN);
                n++;
                multiTaleInc++;
            } else {
                multiTaleInc = 0;
                tempScore = score;
            }
        }
    }

    public void stalkingTale() {
        for (int i = 0; i < n; i++) {
            tale[i * 2 + 2] = taleModel.get(i).getX();
            tale[i * 2 + 3] = taleModel.get(i).getY();
            taleModel.get(i).setX(tale[i * 2]);
            taleModel.get(i).setY(tale[i * 2 + 1]);
        }
    }

    public void newImage(String pathName) {
        try {
            pathModel = pathName;
            taleModelTop.setX(50);
            taleModelTop.setY(50);
            taleModelTop.setWidth(50);
            taleModelTop.setHeight(50);
            taleModelTop.setFill(Color.GREEN);
            Image imagePlayer = new Image(new FileInputStream(path + pathName));
            playerModel.setWidth(50);
            playerModel.setHeight(50);
            playerModel.setFill(new ImagePattern(imagePlayer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player(String PathName, double x, double y) {
        try {
            taleModelTop.setWidth(50);
            taleModelTop.setHeight(50);
            score = 0;
            pathModel = PathName;
            Image imagePlayer = new Image(new FileInputStream(path + PathName));
            playerModel.setX(x);
            playerModel.setY(y);
            playerModel.setWidth(50);
            playerModel.setHeight(50);
            playerModel.setFill(new ImagePattern(imagePlayer));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                move();
                changeDir();
                incTale();
                stalkingTale();
            }
        }.start();
    }

    double delay = 0;

    public void move() {
        tale[1] = posMem[1];
        tale[0] = posMem[0];
        if (pressed == RIGHT) {
            newImage("/out/production/SnakeAreYouOK/sprites/Head.jpg");
            posMem[0] += velocity;
            playerModel.setX(posMem[0]);
            taleModelTop.setX(tale[0]);
            taleModelTop.setY(tale[1]);
        } else {
            if (pressed == LEFT) {
                newImage("/out/production/SnakeAreYouOK/sprites/HeadA.png");
                posMem[0] -= velocity;
                playerModel.setX(posMem[0]);
                taleModelTop.setX(tale[0]);
                taleModelTop.setY(tale[1]);
            } else {
                if (pressed == UP) {
                    newImage("/out/production/SnakeAreYouOK/sprites/HeadW.png");
                    posMem[1] -= velocity;
                    playerModel.setY(posMem[1]);
                    taleModelTop.setX(tale[0]);
                    taleModelTop.setY(tale[1]);
                } else {
                    if (pressed == DOWN) {
                        newImage("/out/production/SnakeAreYouOK/sprites/HeadS.png");
                        posMem[1] += velocity;
                        playerModel.setY(posMem[1]);
                        taleModelTop.setX(tale[0]);
                        taleModelTop.setY(tale[1]);
                    }
                }
            }
        }

    }

    public void changeDir(){
        if (pressedMem == RIGHT && block != RIGHT && Math.abs(delay - posMem[1]) > 50) {
            pressed = RIGHT;
            block = LEFT;
            delay = posMem[0];
            pressedMem = 0;
        } else {
            if (pressedMem == LEFT && block != LEFT && Math.abs(delay - posMem[1]) > 50) {
                pressed = LEFT;
                block = RIGHT;
                delay = posMem[0];
                pressedMem = 0;
            } else {
                if (pressedMem == UP && block != UP && Math.abs(delay - posMem[0]) > 50) {
                    pressed = UP;
                    block = DOWN;
                    delay = posMem[1];
                } else {
                    if (pressedMem == DOWN && block != DOWN && Math.abs(delay - posMem[0]) > 50) {
                        pressed = DOWN;
                        block = UP;
                        delay = posMem[1];
                        pressedMem = 0;
                    }
                }
            }
        }
    }

    int pressed = RIGHT, block = LEFT, pressedMem = 0;
    public void movementH(KeyCode key) {
        if(key == KeyCode.D){
            pressedMem = RIGHT;
        }else{
            if(key == KeyCode.A){
                pressedMem = LEFT;
            }else{
                if(key == KeyCode.W){
                    pressedMem = UP;
                }else{
                    if(key == KeyCode.S){
                        pressedMem = DOWN;
                    }
                }
            }
        }

    }

}
