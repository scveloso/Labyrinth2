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

    private Button btnCharge;

    private Button btnAttack1;
    private Button btnAttack2;
    private Button btnAttack3;

    private Button btnBlock1;
    private Button btnBlock2;
    private Button btnBlock3;

    private static final int MAX_IMG_HEALTH_WIDTH = 200; // 200dp

    private Enemy currEnemy;

    private int playerHealth;
    private int playerCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        imgPlayerHealth = (ImageView) findViewById(R.id.imgPlayerHealth);
        imgPlayerSprite = (ImageView) findViewById(R.id.imgPlayerSprite);
        imgEnemyHealth = (ImageView) findViewById(R.id.imgEnemyHealth);
        imgEnemySprite = (ImageView) findViewById(R.id.imgEnemySprite);
        txtEnemyName = (TextView) findViewById(R.id.txtEnemyName);


        btnAttack1 = (Button) findViewById(R.id.btnAttack1);
        btnAttack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayerTurn(Move.CHARGE_1_ATTACK);
            }
        });

        btnAttack2 = (Button) findViewById(R.id.btnAttack2);
        btnAttack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayerTurn(Move.CHARGE_2_ATTACK);
            }
        });

        btnAttack3 = (Button) findViewById(R.id.btnAttack3);
        btnAttack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayerTurn(Move.CHARGE_3_ATTACK);
            }
        });

        btnBlock1 = (Button) findViewById(R.id.btnBlock1);
        btnBlock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayerTurn(Move.CHARGE_1_BLOCK);
            }
        });

        btnBlock2 = (Button) findViewById(R.id.btnBlock2);
        btnBlock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayerTurn(Move.CHARGE_2_BLOCK);
            }
        });

        btnBlock3 = (Button) findViewById(R.id.btnBlock3);
        btnBlock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayerTurn(Move.CHARGE_3_BLOCK);
            }
        });

        btnCharge = (Button) findViewById(R.id.btnCharge);
        btnCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayerTurn(Move.CHARGE);
            }
        });

        txtCombatLog = (TextView) findViewById(R.id.txtCombatLog);
        txtCombatLog.setMovementMethod(new ScrollingMovementMethod());

        int difficulty = getIntent().getIntExtra(MainActivity.EXTRA_DIFFICULTY, -1);
        playerHealth = getIntent().getIntExtra(MainActivity.EXTRA_PLAYER_CURR_HEALTH, -1);
        playerCharge = getIntent().getIntExtra(MainActivity.EXTRA_PLAYER_CHARGE, -1);

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
        updatePlayerAttacks();

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

    private void updatePlayerAttacks() {
        btnAttack1.setEnabled(true);
        btnAttack2.setEnabled(true);
        btnAttack3.setEnabled(true);


        if (playerCharge < 3) {
            btnAttack3.setEnabled(false);

            if (playerCharge < 2) {
                btnAttack2.setEnabled(false);

                if (playerCharge < 1) {
                    btnAttack1.setEnabled(false);
                }
            }
        }
    }

    private void handlePlayerTurn (Move playerMove) {
        Move enemyMove = currEnemy.getNextMove(playerCharge);
        log("Player used " + playerMove.getName());
        log("Enemy used " + enemyMove.getName());

        if (playerMove.getMoveType().equals("attack")) /* player uses attack */ {

            playerCharge -= playerMove.getPower();
            
            if (enemyMove.getMoveType().equals("block")) {
                if (playerMove.getPower() != enemyMove.getPower()) {
                    inflictDamageToEnemy();
                } else {
                    log(currEnemy.getName() + " successfully blocked the player's attack.");
                }
            } else if (enemyMove.getMoveType().equals("attack")) {
                if (playerMove.getPower() > enemyMove.getPower()) {
                    inflictDamageToEnemy();
                } else if (playerMove.getPower() < enemyMove.getPower()) {
                    inflictDamageToPlayer();
                } else {
                    log("Both attacks matched each other, vanishing into thin air.");
                }
            } else {
                inflictDamageToEnemy();
            }
        } else if (playerMove.getMoveType().equals("block")) /* player uses block */ {
            if (enemyMove.getMoveType().equals("block")) {
                log("Both parties blocked, nothing happened!");
            } else if (enemyMove.getMoveType().equals("attack")) {
                if (enemyMove.getPower() != playerMove.getPower()) {
                    inflictDamageToPlayer();
                }  else {
                    log("Player successfully blocked the enemy " + currEnemy.getName() + " attack.");
                }
            } else {
                log("Player blocked, but the enemy was just charging!");
            }
        } else /* player uses charge */ {
            playerCharge++;

            if (enemyMove.getMoveType().equals("attack")) {
                log("Enemy " + currEnemy.getName() + " attacked the player while charging!");
                inflictDamageToPlayer();
            } else {
                log("Both parties charged.");
            }
        }
        updatePlayerAttacks();
    }

    private void inflictDamageToEnemy() {
        // Inflict damage to the enemy
        currEnemy.setCurrHealth(currEnemy.getCurrHealth() - Player.PLAYER_BASE_ATTACK);

        // Log the player action
        log("Dealt " + Player.PLAYER_BASE_ATTACK + " damage to the enemy " + currEnemy.getName() + ".");
        // Check if enemy died from attack
        checkIfEnemyDead();
        // If enemy not dead, continue by updating the UI
        updateEnemyUI();
    }
    
    private void inflictDamageToPlayer() {
        // Inflict damage to the player
        playerHealth -= currEnemy.getAttack();
        // Log the enemy action
        log("Took " + currEnemy.getAttack() + " damage from the enemy " + currEnemy.getName() + ".");
        // Check if player died from attack
        checkIfGameOver();
        // If player didn't die, continue by updating the UI
        updatePlayerUI();
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