package sample.model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Apple {
    String path = new File("").getAbsolutePath();
    public Rectangle appleModel = new Rectangle();
    public Group appleSub = new Group(appleModel);
    String pathApple;
    public double[] posApple = {0, 0};

    public void refreshApple(String PathName) {
        try {
            pathApple = PathName;
            Image imagePlayer = new Image(new FileInputStream(path + PathName));
            double x = Math.random() * 1700+20;
            double y = Math.random() * 1000+20;
            posApple[0] = x;
            posApple[1] = y;
            appleModel.setX(x);
            appleModel.setY(y);
            appleModel.setWidth(25);
            appleModel.setHeight(25);
            appleModel.setFill(new ImagePattern(imagePlayer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Apple(String PathName, double x, double y) {
        try {
            pathApple = PathName;
            Image imagePlayer = new Image(new FileInputStream(path + PathName));
            posApple[0] = x;
            posApple[1] = y;
            appleModel.setX(x);
            appleModel.setY(y);
            appleModel.setWidth(25);
            appleModel.setHeight(25);
            appleModel.setFill(new ImagePattern(imagePlayer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}