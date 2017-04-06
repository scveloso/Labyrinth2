package com.sveloso.labyrinth;

/**
 * Created by Veloso on 4/3/2017.
 */
public class Block extends Tile {

    public Block (int x, int y) {
        super(x, y);
    }

    // Always return true to not be included in maze generation algorithm
    public boolean isVisited() {
        return true;
    }
}