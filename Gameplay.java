package com.JoyKrishanDas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.RecursiveAction;


public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private final int movementSpeed = 20;

    private Timer timer; // Setting the time so that we can control how fast the ball can move
    private int delay = 8;

    private int playerX = 310;
    private final int playerY = 540;

    private int ballPosX;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;
    private Random rand;

    public Gameplay() {
        rand = new Random(42);
        ballPosX = rand.nextInt(300) + 120;
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);  // Why delay is used ??
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        //winner
        if (totalBricks == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("WINNER", 300, 300);
            g.drawString("Press Enter to Restart", 300, 400);
            ballXdir = 0;
            ballYdir = 0;
        }

        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 20);


        // Map Generator
        map.draw((Graphics2D) g);

        //border
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592); // left boundary
        g.fillRect(0, 0, 692, 3); // upper boundary
        g.fillRect(681, 0, 3, 592); // right boundary

        //paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, 100, 10);

        // the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);


        //GameOver
        if (ballPosY > 570) {
            play = false;
            ballYdir = 0;
            ballXdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("GameOver, Your Score: " + score, 240, 300);
            g.drawString("Press Enter to Restart", 250, 350);

        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        // Ball movement logic
        if (play == true) {
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if (ballPosX < 0) ballXdir = -ballXdir;
            if (ballPosY < 0) ballYdir = -ballYdir;
            if (ballPosX > 660) ballXdir = -ballXdir;
        }

        //for creating intersection
        for (int i = 0; i < map.getMap().length; i++) {
            for (int j = 0; j < map.getMap()[0].length; j++) {
                if (map.getMap()[i][j] > 0) { // Then detect the intersection
                    int brickX = j * map.getBrickWidth() + 80;
                    int brickY = i * map.getBrickHeight() + 50;
                    Rectangle rectA = new Rectangle(brickX, brickY, map.getBrickWidth(), map.getBrickHeight());
                    Rectangle rectB = new Rectangle(ballPosX, ballPosY, 20, 20);
                    if (rectA.intersects(rectB)) {
                        map.setBrickValue(0, i, j);
                        totalBricks--;
                        score += 5;
                        if (ballPosX + 19 <= rectA.x || ballPosX + 1 >= rectA.x + rectA.width)
                            ballXdir = -ballXdir;
                        else
                            ballYdir = -ballYdir;
                    }
                }

            }
        }


        // for the intersection we create two rectangles with the shape of ball and paddle and see if the intersect
        if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, playerY, 100, 10)))
            ballYdir = -ballYdir;

        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 581) playerX = 581;
            else moverRight();  // Method not created yet
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 3) playerX = 3;
            else moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Clicked");
            if (!play && totalBricks >0) {
                    playAgain();
                }
            else if (play && totalBricks ==0){
                playAgain();
            }
        }
    }


    public void playAgain() {
        play = true;
        ballPosX = rand.nextInt(300) + 120;
        ballPosY = 350;
        ballXdir = -1;
        ballYdir = -2;
        score = 0;
        totalBricks = 21;
        map = new MapGenerator(3, 7);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moverRight() {
        play = true;
        playerX += movementSpeed;
    }

    public void moveLeft() {
        play = true;
        playerX -= movementSpeed;
    }
}
