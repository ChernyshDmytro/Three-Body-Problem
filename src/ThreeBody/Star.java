package ThreeBody;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Star {
    private final int MAX_RGB_VALUE = 256;
    private double x;
    private double y;
    private double xv;
    private double yv;
    private Color color;
    private double mass = 40000.0;


    public Star(double x, double y, double xv, double yv){
        this.x = x;
        this.y = y;
        this.xv = xv;
        this.yv = yv;
        Random random = new Random();
        int red = random.nextInt(MAX_RGB_VALUE);
        int green = random.nextInt(MAX_RGB_VALUE);
        int blue = random.nextInt(MAX_RGB_VALUE);
        color = new Color(red, green, blue);
    }

    public Color getColor() {
        return color;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getXv() {
        return xv;
    }

    public double getYv() {
        return yv;
    }
    public void setX(double newX){
        x = newX;
    }
    public void setY(double newY){
        y = newY;
    }
    public void setXv(double newXv) {
        xv = newXv;
    }

    public void setYv(double newXy) {
        yv = newXy;
    }
    public double getMass(){
        return mass;
    }
    public void setMass(double newMass){
        mass = newMass;
    }
}
