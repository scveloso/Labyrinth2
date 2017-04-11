package com.sveloso.labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by s.veloso on 4/10/2017.
 */

public class CombatActivity extends Activity {

    private static final String MOVE_ATTACK = "attack";
    private static final String MOVE_BLOCK = "block";

    private ImageView imgPlayerHealth;

    private ImageView imgEnemyHealth;
    private ImageView imgEnemySprite;
    private TextView txtEnemyName;

    private Button btnAttack;
    private Button btnBlock;

    private Enemy currEnemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        imgPlayerHealth = (ImageView) findViewById(R.id.imgPlayerHealth);
        imgEnemyHealth = (ImageView) findViewById(R.id.imgEnemyHealth);
        imgEnemySprite = (ImageView) findViewById(R.id.imgEnemySprite);

        txtEnemyName = (TextView) findViewById(R.id.txtEnemyName);

        btnAttack = (Button) findViewById(R.id.btnAttack);
        btnAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePlayerTurn(MOVE_ATTACK);
            }
        });
        btnBlock = (Button) findViewById(R.id.btnBlock);
        btnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePlayerTurn(MOVE_BLOCK);
            }
        });

        int difficulty = getIntent().getIntExtra(MainActivity.EXTRA_DIFFICULTY, -1);

        if (difficulty < 6) {
            currEnemy = new Enemy(this, 0);
        } else if (difficulty < 10) {
            currEnemy = new Enemy(this, 1);
        } else {
            currEnemy = new Enemy(this, 2);
        }
        txtEnemyName.setText(currEnemy.getName());

        updateEnemyUI();
    }

    private void updateEnemyUI() {
        double percentHealth = currEnemy.getCurrHealth() / currEnemy.getHealth();
        double newHealthW = imgEnemyHealth.getWidth() * percentHealth;
        imgEnemyHealth.setMaxWidth((int) newHealthW);
    }

    private void handlePlayerTurn(String move) {
        if (move.equals(MOVE_ATTACK)) {
            currEnemy.setCurrHealth(currEnemy.getCurrHealth() - 20);
            checkIfGameOver();

        } else if (move.equals(MOVE_BLOCK)) {

        }
    }
}