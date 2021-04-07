package com.JoyKrishanDas;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame obj = new JFrame(); // A class that builds the external window for visualization
        Gameplay game = new Gameplay();
        obj.setBounds(100, 100, 700, 600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(obj.EXIT_ON_CLOSE);
        obj.add(game);  // The game needs to be a panel object
    }
}
