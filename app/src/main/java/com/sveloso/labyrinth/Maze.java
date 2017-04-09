package com.sveloso.labyrinth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Veloso on 4/2/2017.
 *
 * A 3D array of tiles, uses cells to procedurally generate mazes
 */
public class Maze {

    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;

    private int width;
    private int height;

    Tile[][] maze;

    private Random generator;
    private int startX;
    private int startY;

    private int endX;
    private int endY;

    public Maze() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;

        maze = new Tile[width][height];
        generator = new Random();
        startX = generator.nextInt(width - 2) + 1; // avoids first and last index (Blocks surrounding the maze)
        startY = generator.nextInt(height - 2) + 1; // avoids first and last index (Blocks surrounding the maze)

        generateMaze();
    }
    
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        maze = new Tile[width][height];
        generator = new Random();
        startX = generator.nextInt(width - 2) + 1; // avoids first and last index (Blocks surrounding the maze)
        startY = generator.nextInt(height - 2) + 1; // avoids first and last index (Blocks surrounding the maze)
        
        generateMaze();
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public Tile[][] getMaze() {
        return maze;
    }

    private void generateMaze() {
        // Generate cells surrounded by blocks, a blank slate for the maze
        fillCells();

        // A stack for visited cells to facilitate backtracking
        Stack<Cell> visited = new Stack<>();

        // Starting from an initial cell
        Cell current = (Cell) maze[startY][startX];
        // Make the initial cell the current cell and mark it as visited
        current.setAsVisited();

        int totalCells = (width - 2) * (height - 2);
        int numVisited = 1;

        // While there are unvisited cells
        while (numVisited < totalCells) {
            Map<Integer, Cell> unvisitedNeighbors = new LinkedHashMap<>();

            int upY = current.getY() - 1;
            int downY = current.getY() + 1;
            int rightX = current.getX() + 1;
            int leftX = current.getX() - 1;

            // Check unvisited neighbors
            if (upY >= 0) /* if the cell above the current cell is a valid tile */ {
                Tile c = maze[upY][current.getX()];
                // if the valid cell is not visited (or not a block), add it as an unvisited neighbor
                if (!c.isVisited()) {
                    unvisitedNeighbors.put(0, (Cell) c);
                }
            }
            if (rightX < DEFAULT_WIDTH) {
                Tile c = maze[current.getY()][rightX];
                if (!c.isVisited()) {
                    unvisitedNeighbors.put(1, (Cell) c);
                }
            }
            if (downY < DEFAULT_HEIGHT) {
                Tile c = maze[downY][current.getX()];
                if (!c.isVisited()) {
                    unvisitedNeighbors.put(2, (Cell) c);
                }
            }
            if (leftX >= 0) {
                Tile c = maze[current.getY()][leftX];
                if (!c.isVisited()) {
                    unvisitedNeighbors.put(3, (Cell) c);
                }
            }

            // If the current cell has any neighbours which have not been visited
            if (unvisitedNeighbors.size() > 0) {
                // Choose randomly one of the unvisited neighbors
                List<Integer> keys = new ArrayList<>(unvisitedNeighbors.keySet());
                int randomInt = keys.get(generator.nextInt(keys.size()));
                Cell randomUnvisitedNeighbor = unvisitedNeighbors.get(randomInt);

                // Push the current cell to the stack
                visited.push(current);

                // Remove the wall between the current cell and the chosen cell
                switch (randomInt) {
                    case 0 /* open path upward */ :
                        current.breakTop();
                        randomUnvisitedNeighbor.breakBot();
                        break;
                    case 1 /* open path to the right */ :
                        current.breakRight();
                        randomUnvisitedNeighbor.breakLeft();
                        break;
                    case 2 /* open path downward */ :
                        current.breakBot();
                        randomUnvisitedNeighbor.breakTop();
                        break;
                    case 3 /* open path to the left */ :
                        current.breakLeft();
                        randomUnvisitedNeighbor.breakRight();
                        break;
                }
                // Mark the chosen cell the current cell and mark it as visited
                current = randomUnvisitedNeighbor;
                current.setAsVisited();
                numVisited++;
            } else if (visited.size() > 0) /* Else if stack is not empty */ {
                // Pop a cell from the stack and make it the current cell
                current = visited.pop();
            }
        }
    }

    private void fillCells() {
        for (int h = 0; h < DEFAULT_HEIGHT; h++) {
            for (int w = 0; w < DEFAULT_WIDTH; w++) {
                // If maze index is outer border (0, height or width) then place a block (boundary) on it
                if (h == 0 || w == 0 || h == (height - 1) || w == (width - 1)) {
                    maze[h][w] = new Block(w, h);
                } else /* else place a Cell on it */ {
                    maze[h][w] = new Cell(w, h);
                }
            }
        }
    }
}