package com.sveloso.labyrinth;

/**
 * Created by Veloso on 4/3/2017.
 */
public abstract class Tile {

    private int x;
    private int y;

    public Tile (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean isVisited();
}