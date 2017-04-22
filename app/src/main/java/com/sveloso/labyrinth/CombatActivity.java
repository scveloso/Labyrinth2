package com.sveloso.labyrinth;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
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
    private ImageView imgPlayerSprite;
    private ImageView imgEnemyHealth;
    private ImageView imgEnemySprite;
    private TextView txtEnemyName;
    private TextView txtCombatLog;

    private Button btnAttack;
    private Button btnBlock;

    private static final int MAX_IMG_HEALTH_WIDTH = 200; // 200dp

    private Enemy currEnemy;

    private int playerHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        imgPlayerHealth = (ImageView) findViewById(R.id.imgPlayerHealth);
        imgPlayerSprite = (ImageView) findViewById(R.id.imgPlayerSprite);
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

        imgPlayerSprite.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
        if (difficulty < 5) /* 5 out of 10 chance for easy */ {
            currEnemy = new EasyEnemy(this);
            imgEnemySprite.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_enemy_easy));
        } else if (difficulty < 8) /* 3 out of 10 chance for medium */ {
            currEnemy = new MediumEnemy(this);
            imgEnemySprite.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_enemy_medium));
        } else /* 2 out of 10 chance for hard */ {
            currEnemy = new HardEnemy(this);
            imgEnemySprite.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.img_enemy_hard));
        }
        txtEnemyName.setText(currEnemy.getName());

        updateEnemyUI();
        updatePlayerUI();

        log("Combat log started.");
    }

    private void updateEnemyUI() {
        // Get how much % health the enemy has left
        double percentHealth = (double) currEnemy.getCurrHealth() / currEnemy.getMaxHealth();
        // Convert the % health enemy has left to % health of ImageView width
        double newHealthW = MAX_IMG_HEALTH_WIDTH * percentHealth;

        Resources r = getResources();
        // Convert the % health of ImageView width to pixels
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) newHealthW, r.getDisplayMetrics());

        // Apply the pixel change to the ImageView
        imgEnemyHealth.getLayoutParams().width = (int) px;
        imgEnemyHealth.requestLayout();
    }

    private void updatePlayerUI() {
        // Get how much % health player has left
        double percentHealth = (double) playerHealth / Player.PLAYER_HEALTH;
        // COnvert the % health player has left to % health of ImageView width
        double newHealthW = MAX_IMG_HEALTH_WIDTH * percentHealth;

        Resources r = getResources();
        // Convert the % health of ImageView width to pixels
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) newHealthW, r.getDisplayMetrics());

        // Apply the pixel change to the ImageView
        imgPlayerHealth.getLayoutParams().width = (int) px;
        imgPlayerHealth.requestLayout();
    }

    private void handlePlayerTurn(String move) {
        if (move.equals(MOVE_ATTACK)) /* If the player attacks */ {
            // Inflict damage to the enemy
            currEnemy.setCurrHealth(currEnemy.getCurrHealth() - Player.PLAYER_BASE_ATTACK);

            // Log the player action
            log("Dealt " + Player.PLAYER_BASE_ATTACK + " damage to the enemy " + currEnemy.getName() + ".");
            // Check if enemy died from attack
            checkIfEnemyDead();
            // If enemy not dead, continue by updating the UI
            updateEnemyUI();

            // Inflict damage to the player
            playerHealth -= currEnemy.getAttack();
            // Log the enemy action
            log("Took " + currEnemy.getAttack() + " damage from the enemy " + currEnemy.getName() + ".");
            // Check if player died from attack
            checkIfGameOver();
            // If player didn't die, continue by updating the UI
            updatePlayerUI();
        } else if (move.equals(MOVE_BLOCK)) {
            // Blocked enemy attack, no damage inflicted on both sides
            // Just log the turn
            log("Blocked " + currEnemy.getAttack() + " damage from the enemy " + currEnemy.getName() + ".");
        }
    }

    private void checkIfEnemyDead() {
        if (currEnemy.getCurrHealth() <= 0) {
            // end activity with success result
            Intent data = new Intent();
            data.putExtra(MainActivity.EXTRA_PLAYER_CURR_HEALTH, playerHealth);
            setResult(MainActivity.COMBAT_RESULT_WIN, data);
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