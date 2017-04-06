package com.sveloso.labyrinth;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

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
    
    private Map<Tile, ImageView> tileimgMap;

    private Maze mazeObj;
    private Tile[][] maze;

    private int currX;
    private int currY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tileimgMap = new LinkedHashMap<>();

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
        
        updateTileimgMap();
    }
    
    private void updateTileimgMap () {
        tileimgMap = new LinkedHashMap<>();
        tileimgMap.put(maze[currY - 1][currX - 1], imgNW);
        tileimgMap.put(maze[currY - 1][currX], imgN);
        tileimgMap.put(maze[currY - 1][currX + 1], imgNE);

        tileimgMap.put(maze[currY][currX - 1], imgW);
        tileimgMap.put(maze[currY][currX + 1], imgE);

        tileimgMap.put(maze[currY + 1][currX - 1], imgSW);
        tileimgMap.put(maze[currY + 1][currX], imgS);
        tileimgMap.put(maze[currY + 1][currX + 1], imgSE);
    }

    private void initializeTiles() {
        for (Map.Entry<Tile, ImageView> entry : tileimgMap.entrySet()) {
            Tile tile = entry.getKey();
            ImageView img = entry.getValue();

            if (tile instanceof Block) {
                img.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_block));
            } else {
                Cell cell = (Cell) tile;

                Resources r = getResources();
                Drawable[] layers = new Drawable[4];

                if (!cell.isNorth()) {
                    layers[0] = r.getDrawable(R.mipmap.img_top);
                } else {
                    layers[0] = r.getDrawable(R.mipmap.img_open);
                }

                if (!cell.isEast()) {
                    layers[1] = r.getDrawable(R.mipmap.img_right);
                } else {
                    layers[1] = r.getDrawable(R.mipmap.img_open);
                }

                if (!cell.isSouth()) {
                    layers[2] = r.getDrawable(R.mipmap.img_bot);
                } else {
                    layers[2] = r.getDrawable(R.mipmap.img_open);
                }

                if (!cell.isWest()) {
                    layers[3] = r.getDrawable(R.mipmap.img_left);
                } else {
                    layers[3] = r.getDrawable(R.mipmap.img_open);
                }

                LayerDrawable layerDrawable = new LayerDrawable(layers);
                img.setImageDrawable(layerDrawable);
            }
        }
    }
}