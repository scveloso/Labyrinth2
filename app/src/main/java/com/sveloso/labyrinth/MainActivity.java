package com.sveloso.labyrinth;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Veloso on 4/3/2017.
 *
 * The main game activity
 */
public class MainActivity extends AppCompatActivity {

    private static final int COMBAT_REQUEST = 0;
    public static final int COMBAT_RESULT_WIN = 1;
    public static final int COMBAT_RESULT_LOSE = 2;

    public static final String EXTRA_DIFFICULTY = "com.sveloso.labyrinth.DIFFICULTY";
    public static final String EXTRA_PLAYER_CURR_HEALTH = "com.sveloso.labyrinth.PLAYER_CURR_HEALTH";

    private ImageView imgNW;
    private ImageView imgN;
    private ImageView imgNE;

    private ImageView imgW;
    private ImageView imgE;

    private ImageView imgSW;
    private ImageView imgS;
    private ImageView imgSE;

    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    
    private Map<Tile, ImageView> tileImgMap;

    private Maze mazeObj;
    private Tile[][] maze;
    private Player currPlayer;

    private int currX;
    private int currY;

    private Random spawner;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == COMBAT_REQUEST) {
            if (resultCode == COMBAT_RESULT_WIN) {
                currPlayer.setCurrHealth(data.getIntExtra(EXTRA_PLAYER_CURR_HEALTH, -1));
            } else {
                btnUp.setEnabled(false);
                btnDown.setEnabled(false);
                btnLeft.setEnabled(false);
                btnRight.setEnabled(false);
                Toast.makeText(this, "Game over.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tileImgMap = new LinkedHashMap<>();

        imgNW = (ImageView) findViewById(R.id.imgNW);
        imgN = (ImageView) findViewById(R.id.imgN);
        imgNE = (ImageView) findViewById(R.id.imgNE);

        imgW = (ImageView) findViewById(R.id.imgW);
        imgE = (ImageView) findViewById(R.id.imgE);

        imgSW = (ImageView) findViewById(R.id.imgSW);
        imgS = (ImageView) findViewById(R.id.imgS);
        imgSE = (ImageView) findViewById(R.id.imgSE);

        btnUp = (Button) findViewById(R.id.btnUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currY--;
                updateUI();

                checkEnemy();
            }
        });
        btnDown = (Button) findViewById(R.id.btnDown);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currY++;
                updateUI();

                checkEnemy();
            }
        });
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currX--;
                updateUI();

                checkEnemy();
            }
        });
        btnRight = (Button) findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currX++;
                updateUI();

                checkEnemy();
            }
        });

        // Make a new maze for the current game
        mazeObj = new Maze();
        maze = mazeObj.getMaze();
        // Initialize player's current coordinates
        currPlayer = new Player();
        currX = mazeObj.getStartX();
        currY = mazeObj.getStartY();

        // Update the UI for the first time
        updateUI();

        // Initialize spawner
        spawner = new Random();
    }

    private void updateUI() {
        updateControlsUI();
        updateTileImgMap();
        updateImageViews();
    }

    /**
     * Check the current cell player is standing on
     * Restrict movement based on the cell's walls
     *
     */
    private void updateControlsUI() {
        Cell curr = (Cell) maze[currY][currX];
        if (curr.isNorth()) {
            btnUp.setEnabled(true);
        } else {
            btnUp.setEnabled(false);
        }

        if (curr.isEast()) {
            btnRight.setEnabled(true);
        } else {
            btnRight.setEnabled(false);
        }

        if (curr.isSouth()) {
            btnDown.setEnabled(true);
        } else {
            btnDown.setEnabled(false);
        }

        if (curr.isWest()) {
            btnLeft.setEnabled(true);
        } else {
            btnLeft.setEnabled(false);
        }
    }

    /**
     *
     * Update structure that maps tiles and ImageViews together
     * based on current player coordinates
     *
     */
    private void updateTileImgMap () {
        // Re-initialize the map
        tileImgMap = new LinkedHashMap<>();
        
        tileImgMap.put(maze[currY - 1][currX - 1], imgNW);
        tileImgMap.put(maze[currY - 1][currX], imgN);
        tileImgMap.put(maze[currY - 1][currX + 1], imgNE);

        tileImgMap.put(maze[currY][currX - 1], imgW);
        tileImgMap.put(maze[currY][currX + 1], imgE);

        tileImgMap.put(maze[currY + 1][currX - 1], imgSW);
        tileImgMap.put(maze[currY + 1][currX], imgS);
        tileImgMap.put(maze[currY + 1][currX + 1], imgSE);
    }

    /**
     *
     * Iterate through the Tile-ImageView map
     * Update the ImageView's drawable based on what tile it is mapped to
     *
     */
    private void updateImageViews() {
        for (Map.Entry<Tile, ImageView> entry : tileImgMap.entrySet()) {
            Tile tile = entry.getKey();
            ImageView img = entry.getValue();

            if (tile instanceof Block) {
                img.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
            } else {
                Cell cell = (Cell) tile;

                Resources r = getResources();
                Drawable[] layers = new Drawable[4];

                if (!cell.isNorth()) {
                    layers[0] = ContextCompat.getDrawable(this, R.mipmap.img_top);
                } else {
                    layers[0] = ContextCompat.getDrawable(this, R.mipmap.img_open);
                }

                if (!cell.isEast()) {
                    layers[1] = ContextCompat.getDrawable(this, R.mipmap.img_right);
                } else {
                    layers[1] = ContextCompat.getDrawable(this, R.mipmap.img_open);
                }

                if (!cell.isSouth()) {
                    layers[2] = ContextCompat.getDrawable(this, R.mipmap.img_bot);
                } else {
                    layers[2] = ContextCompat.getDrawable(this, R.mipmap.img_open);
                }

                if (!cell.isWest()) {
                    layers[3] = ContextCompat.getDrawable(this, R.mipmap.img_left);
                } else {
                    layers[3] = ContextCompat.getDrawable(this, R.mipmap.img_open);
                }

                LayerDrawable layerDrawable = new LayerDrawable(layers);
                img.setImageDrawable(layerDrawable);
            }
        }
    }

    private void checkEnemy() {
        // 1 / 5 Chance to initiate combat
        int rand = spawner.nextInt(6);

        // If combat engaged
        if (rand == 0) {
            // Create combat intent
            Intent combatIntent = new Intent(this, CombatActivity.class);
            // Determine the difficulty
            int difficulty = spawner.nextInt(10);
            combatIntent.putExtra(EXTRA_DIFFICULTY, difficulty);

            int playerCurrHealth = currPlayer.getCurrHealth();
            combatIntent.putExtra(EXTRA_PLAYER_CURR_HEALTH, playerCurrHealth);

            startActivityForResult(combatIntent, COMBAT_REQUEST);
        }
    }
}