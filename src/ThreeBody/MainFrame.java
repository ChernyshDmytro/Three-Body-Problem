package ThreeBody;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainFrame extends JPanel{
    static final int FRAME_WIDTH = 900;
    static final int FRAME_HEIGHT = 900;
    static final int FPS = 100;
    Star star1;
    Star star2;
    Star star3;
    Star star4;
    Star[] stars;
    static final int starRadius = 12;
    private static final double G = 6.674e-11;
    double[] com;

    public static void main(String[] args){
        JFrame frame = new JFrame("Three body problem");
        MainFrame mainFrame = new MainFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.add(mainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mainFrame.startSimulation();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if (star1 != null) {
            g.setColor(star1.getColor());
            g.fillOval((int) star1.getX(), (int) star1.getY(), starRadius, starRadius);
        }
        if (star2 != null) {
            g.setColor(star2.getColor());
            g.fillOval((int) star2.getX(), (int) star2.getY(), starRadius, starRadius);
        }
        if (star3 != null) {
            g.setColor(star3.getColor());
            g.fillOval((int) star3.getX(), (int) star3.getY(), starRadius, starRadius);
        }
        if (star4 != null) {
            g.setColor(star4.getColor());
            g.fillOval((int) star4.getX(), (int) star4.getY(), 5, 5);
        }

    }
    public double randomiseCoordinate(){
        Random random = new Random();
        return random.nextDouble(300, 600);
    }
    public int randomiseVelocity(){
        Random random = new Random();
        return random.nextInt(-20, 20);
    }
    public void startSimulation(){

        star1 = new Star(randomiseCoordinate(), randomiseCoordinate(), randomiseVelocity(), randomiseVelocity());
        star2 = new Star( randomiseCoordinate(), randomiseCoordinate(), randomiseVelocity(), randomiseVelocity());
        star3 = new Star(randomiseCoordinate(), randomiseCoordinate(), randomiseVelocity(), randomiseVelocity());
        star4 = new Star(randomiseCoordinate(), randomiseCoordinate(), randomiseVelocity(), randomiseVelocity());
        star4.setMass(0.01);
        stars = new Star[] {star1, star2, star3, star4};
        com = calculateCenterOfMass();

        Long lastUpdateTime = System.nanoTime();
        double deltaTime;
        while (true){
            long currentTime = System.nanoTime();
            deltaTime = (currentTime - lastUpdateTime) / 1e9;

            update(deltaTime);
            repaint();

            try{
                Thread.sleep((long) (1000/FPS));
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            lastUpdateTime = currentTime;
        }
    }
    public void update(double deltaTime) {
        // Update velocities of the stars based on gravitational attraction between pairs of stars
        for (int i = 0; i < stars.length; i++) {
            for (int j = i + 1; j < stars.length; j++) {
                Star star1 = stars[i];
                Star star2 = stars[j];

                // Calculate the distance and direction between the stars
                double dx = star2.getX() - star1.getX();
                double dy = star2.getY() - star1.getY();
                double distanceSquared = dx * dx + dy * dy;
                double distance = Math.sqrt(distanceSquared);

                // Apply gravitational attraction between the stars
                double forceMagnitude = star1.getMass() * star2.getMass() / distanceSquared;
                double accelerationX1 = forceMagnitude * dx / distance / star1.getMass();
                double accelerationY1 = forceMagnitude * dy / distance / star1.getMass();
                double accelerationX2 = -forceMagnitude * dx / distance / star2.getMass();
                double accelerationY2 = -forceMagnitude * dy / distance / star2.getMass();

                // Update velocities based on acceleration
                star1.setXv(star1.getXv() + accelerationX1 * deltaTime);
                star1.setYv(star1.getYv() + accelerationY1 * deltaTime);
                star2.setXv(star2.getXv() + accelerationX2 * deltaTime);
                star2.setYv(star2.getYv() + accelerationY2 * deltaTime);
            }
        }

        // Update positions of the stars based on their velocities
        for (Star star : stars) {
            star.setX(star.getX() + star.getXv() * deltaTime);
            star.setY(star.getY() + star.getYv() * deltaTime);
        }
        com = calculateCenterOfMass();
    }


    public double[] calculateCenterOfMass() {
        double totalMass = 0.0;
        double comX = 0.0;
        double comY = 0.0;

        for(Star star : stars){
            double mass = star.getMass();
            totalMass += mass;
            comX += star.getX() * mass;
            comY += star.getY() * mass;
        }
        if (totalMass != 0){
            comX /= totalMass;
            comY /= totalMass;
        }
        return new double[]{comX, comY};
    }
}
