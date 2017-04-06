package com.sveloso.labyrinth;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameNW;
    private FrameLayout frameN;
    private FrameLayout frameNE;

    private FrameLayout frameW;
    private FrameLayout frameE;

    private FrameLayout frameSW;
    private FrameLayout frameS;
    private FrameLayout frameSE;

    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    
    private Map<Tile, FrameLayout> tileFrameMap;

    private Maze mazeObj;
    private Tile[][] maze;

    private int currX;
    private int currY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tileFrameMap = new LinkedHashMap<>();

        frameNW = (FrameLayout) findViewById(R.id.tileNW);
        frameN = (FrameLayout) findViewById(R.id.tileN);
        frameNE = (FrameLayout) findViewById(R.id.tileNE);

        frameW = (FrameLayout) findViewById(R.id.tileW);
        frameE = (FrameLayout) findViewById(R.id.tileE);

        frameSW = (FrameLayout) findViewById(R.id.tileSW);
        frameS = (FrameLayout) findViewById(R.id.tileS);
        frameSE = (FrameLayout) findViewById(R.id.tileSE);

        btnUp = (Button) findViewById(R.id.btnUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currY--;
                updateControlsUI();
                initializeTiles();
            }
        });
        btnDown = (Button) findViewById(R.id.btnDown);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currY++;
                updateControlsUI();
                initializeTiles();
            }
        });
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currX--;
                updateControlsUI();
                initializeTiles();
            }
        });
        btnRight = (Button) findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currX++;
                updateControlsUI();
                initializeTiles();
            }
        });

        mazeObj = new Maze();
        maze = mazeObj.getMaze();

        currX = mazeObj.getStartX();
        currY = mazeObj.getStartY();

        updateControlsUI();
        initializeTiles();
    }

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
        
        updateTileFrameMap();
    }
    
    private void updateTileFrameMap () {
        tileFrameMap.put(maze[currY - 1][currX - 1], frameNW);
        tileFrameMap.put(maze[currY - 1][currX], frameN);
        tileFrameMap.put(maze[currY - 1][currX + 1], frameNE);

        tileFrameMap.put(maze[currY][currX - 1], frameW);
        tileFrameMap.put(maze[currY][currX - 1], frameE);

        tileFrameMap.put(maze[currY + 1][currX - 1], frameSW);
        tileFrameMap.put(maze[currY + 1][currX], frameS);
        tileFrameMap.put(maze[currY + 1][currX + 1], frameSE);
    }

    private void initializeTiles() {
        for (Map.Entry<Tile, FrameLayout> entry : tileFrameMap.entrySet()) {
            Tile tile = entry.getKey();
            FrameLayout frame = entry.getValue();

            for (int i = 0; i < frame.getChildCount(); i++) {
                switch (i) {
                    case 0:
                        ImageView frame0 = (ImageView) frame.getChildAt(i);
                        if (tile instanceof Block) {
                            frame0.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                        } else {
                            Cell cell = (Cell) tile;
                            if (!cell.isNorth()) {
                                frame0.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_top));
                            } else {
                                frame0.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                            }
                        }
                        break;
                    case 1:
                        ImageView frame1 = (ImageView) frame.getChildAt(i);
                        if (tile instanceof Block) {
                            frame1.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                        } else {
                            Cell cell = (Cell) tile;
                            if (!cell.isEast()) {
                                frame1.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_right));
                            } else {
                                frame1.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                            }
                        }
                        break;
                    case 2:
                        ImageView frame2 = (ImageView) frame.getChildAt(i);
                        if (tile instanceof Block) {
                            frame2.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                        } else {
                            Cell cell = (Cell) tile;
                            if (!cell.isSouth()) {
                                frame2.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_bot));
                            } else {
                                frame2.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                            }
                        }
                        break;
                    case 3:
                        ImageView frame3 = (ImageView) frame.getChildAt(i);
                        if (tile instanceof Block) {
                            frame3.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                        } else {
                            Cell cell = (Cell) tile;
                            if (!cell.isNorth()) {
                                frame3.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_left));
                            } else {
                                frame3.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
                            }
                        }
                        break;
                }
            }
        }
    }
}