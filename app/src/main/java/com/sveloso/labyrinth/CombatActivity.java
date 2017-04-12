package com.sveloso.labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
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
    private static final int MAX_HEALTH_WIDTH = 200;

    private ImageView imgPlayerHealth;
    private ImageView imgEnemyHealth;
    private ImageView imgEnemySprite;
    private TextView txtEnemyName;
    private TextView txtCombatLog;

    private Button btnAttack;
    private Button btnBlock;

    private Enemy currEnemy;

    private int playerHealth;

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
        txtCombatLog = (TextView) findViewById(R.id.txtCombatLog);
        txtCombatLog.setMovementMethod(new ScrollingMovementMethod());


        int difficulty = getIntent().getIntExtra(MainActivity.EXTRA_DIFFICULTY, -1);
        playerHealth = getIntent().getIntExtra(MainActivity.EXTRA_PLAYER_CURR_HEALTH, -1);

        if (difficulty < 6) {
            currEnemy = new Enemy(this, 0);
            imgEnemySprite.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_enemy_easy));
        } else if (difficulty < 10) {
            currEnemy = new Enemy(this, 1);
            imgEnemySprite.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_enemy_medium));
        } else {
            currEnemy = new Enemy(this, 2);
            imgEnemySprite.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_enemy_hard));
        }
        txtEnemyName.setText(currEnemy.getName());

        updateEnemyUI();
        updatePlayerUI();
    }

    private void updateEnemyUI() {
        double percentHealth = currEnemy.getCurrHealth() / currEnemy.getHealth();
        double newHealthW = MAX_HEALTH_WIDTH * percentHealth;
        imgEnemyHealth.setMaxWidth((int) newHealthW);
    }

    private void updatePlayerUI() {
        double percentHealth = playerHealth / Player.PLAYER_HEALTH;
        double newHealthW = MAX_HEALTH_WIDTH * percentHealth;
        imgPlayerHealth.setMaxWidth((int) newHealthW);
    }

    private void handlePlayerTurn(String move) {
        if (move.equals(MOVE_ATTACK)) {
            currEnemy.setCurrHealth(currEnemy.getCurrHealth() - 20);
            log("Dealt " + Player.PLAYER_BASE_ATTACK + " damage to the enemy " + currEnemy.getName() + ".");
            checkIfEnemyDead();
            updateEnemyUI();

            playerHealth -= currEnemy.getAttack();
            log("Took " + currEnemy.getAttack() + " damage from the enemy " + currEnemy.getName() + ".");
            checkIfGameOver();
            updatePlayerUI();
        } else if (move.equals(MOVE_BLOCK)) {
            log("Blocked " + currEnemy.getAttack() + " damage from the enemy " + currEnemy.getName() + ".");
        }
    }

    private void checkIfEnemyDead() {
        if (currEnemy.getCurrHealth() <= 0) {
            // end activity with success result

            setResult(MainActivity.COMBAT_RESULT_WIN);
            finish();
        }
    }

    private void checkIfGameOver() {
        if (playerHealth <= 0) {
            // end activity with fail result

            setResult(MainActivity.COMBAT_RESULT_LOSE);
            finish();
        }
    }

    private void log(String text) {
        txtCombatLog.append(text);
        txtCombatLog.append("\n");
        txtCombatLog.invalidate();
    }
}