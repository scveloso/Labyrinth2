package com.sveloso.labyrinth;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Veloso on 4/2/2017.
 */
public class Maze {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    Tile[][] maze;

    private Random generator;
    private int startX;
    private int startY;

    public Maze() {
        maze = new Cell[WIDTH + 2][HEIGHT + 2];
        generator = new Random();
        startX = generator.nextInt(WIDTH - 1) + 1; // avoids first and last index (Blocks surrounding the maze)
        startY = generator.nextInt(HEIGHT - 1) + 1; // avoids first and last index (Blocks surrounding the maze)
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
        // Space for a maze as a large grid of cells, each starting with 4 walls
        fillCells();

        // A stack for visited nodes to facilitate backtracking
        Stack<Cell> visited = new Stack<>();

        // Starting from a random cell
        Cell current = (Cell) maze[startY][startX];
        // Make the initial cell the current cell and mark it as visited
        current.setAsVisited();

        int totalCells = (WIDTH - 2) * (HEIGHT - 2);
        int numVisited = 1;

        // While there are unvisited cells
        while (numVisited < totalCells) {

            List<Cell> unvisitedNeighbors = new LinkedList<>();
            List<Integer> unvisitedIndexes = new LinkedList<>();
            int upY = current.getY() - 1;
            int downY = current.getY() + 1;
            int rightX = current.getX() + 1;
            int leftX = current.getX() - 1;

            int indexCounter = 0;

            // Check unvisited neighbors
            if (upY >= 0) /* if the cell above the current cell is a valid cell */ {
                Tile c = maze[upY][current.getX()];
                // if the valid cell is not visited, add it as an unvisited neighbor
                if (!c.isVisited()) {
                    unvisitedNeighbors.add((Cell) c);
                    unvisitedIndexes.add(indexCounter);
                    indexCounter++;
                }
            }
            if (rightX < WIDTH) {
                Tile c = maze[current.getY()][rightX];
                if (!c.isVisited()) {
                    unvisitedNeighbors.add((Cell) c);
                    unvisitedIndexes.add(indexCounter);
                    indexCounter++;
                }
            }
            if (downY < HEIGHT) {
                Tile c = maze[downY][current.getX()];
                if (!c.isVisited()) {
                    unvisitedNeighbors.add((Cell) c);
                    unvisitedIndexes.add(indexCounter);
                    indexCounter++;
                }
            }
            if (leftX >= 0) {
                Tile c = maze[current.getY()][leftX];
                if (!c.isVisited()) {
                    unvisitedNeighbors.add((Cell) c);
                    unvisitedIndexes.add(indexCounter);
                }
            }

            // If the current cell has any neighbours which have not been visited
            if (unvisitedNeighbors.size() > 0) {
                // Choose randomly one of the unvisited neighbors
                int randPos = generator.nextInt(unvisitedIndexes.size());
                int pos = unvisitedIndexes.get(randPos);
                Cell randomUnvisitedNeighbor = unvisitedNeighbors.get(pos);

                // Push the current cell to the stack
                visited.push(current);

                // Remove the wall between the current cell and the chosen cell
                switch (pos) {
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
        for (int h = 0; h < HEIGHT; h++) {
            for (int w = 0; w < WIDTH; w++) {
                if (h == 0 || w == 0 || h == (HEIGHT - 1) || w == (WIDTH - 1)) {
                    maze[h][w] = new Block(w, h);
                } else {
                    maze[h][w] = new Cell(w, h);
                }
            }
        }
    }
}