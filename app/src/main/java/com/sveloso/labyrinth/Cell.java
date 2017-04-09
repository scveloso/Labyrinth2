package com.sveloso.labyrinth;

/**
 * Created by Veloso on 4/2/2017.
 *
 * The main tile that players walk in, used for generating the maze
 */
public class Cell extends Tile {

    private boolean north, west, south, east;
    private boolean visited;

    public Cell (int x, int y) {
        super(x, y);

        north = false;
        west = false;
        south = false;
        east = false;
        visited = false;
    }

    public void setAsVisited () {
        visited = true;
    }

    public boolean isVisited () {
        return visited;
    }

    public void breakTop() {
        north = true;
    }

    public void breakLeft() {
        west = true;
    }

    public void breakBot() {
        south = true;
    }

    public void breakRight() {
        east = true;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isWest() {
        return west;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isEast() {
        return east;
    }
}