package com.JoyKrishanDas;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapGenerator {
    private int map[][];
    private int brickWidth;
    private int brickHeight;
    private final int totalBrickWidth = 540;
    private final int totalBrickHeight = 150;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j <map[0].length; j++){
                map[i][j] = 1;  // 1 means that the block is not intersected
            }
        }
        brickWidth = totalBrickWidth/col;
        brickHeight = totalBrickHeight/row;
    }

    public void draw(Graphics2D a){
        for (int i = 0; i < map.length ; i++){
            for (int j = 0; j < map[0].length; j++){
                if (map[i][j]> 0){
                    a.setColor(Color.WHITE);
                    a.fillRect(j * brickWidth + 80, i* brickHeight +50, brickWidth, brickHeight );

                    a.setStroke(new BasicStroke(3));
                    a.setColor(Color.BLACK);

                    a.drawRect(j * brickWidth + 80, i* brickHeight +50, brickWidth, brickHeight );
                }
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getBrickWidth() {
        return brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }

    public void setBrickValue(int value, int row, int col){
        map[row][col]= value;
    }
}
