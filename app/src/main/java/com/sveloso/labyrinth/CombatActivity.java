package com.sveloso.labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by s.veloso on 4/10/2017.
 */

public class CombatActivity extends Activity {

    private ImageView imgPlayerHealth;

    private ImageView imgEnemyHealth;
    private ImageView imgEnemySprite;
    private TextView txtEnemyName;

    private Enemy currEnemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        imgPlayerHealth = (ImageView) findViewById(R.id.imgPlayerHealth);
        imgEnemyHealth = (ImageView) findViewById(R.id.imgEnemyHealth);
        imgEnemySprite = (ImageView) findViewById(R.id.imgEnemySprite);

        txtEnemyName = (TextView) findViewById(R.id.txtEnemyName);

        int difficulty = getIntent().getIntExtra(MainActivity.EXTRA_DIFFICULTY, -1);

        if (difficulty < 6) {
            currEnemy = new Enemy(this, 0);
        } else if (difficulty < 10) {
            currEnemy = new Enemy(this, 1);
        } else {
            currEnemy = new Enemy(this, 2);
        }

        updateEnemyUI();
    }

    private void updateEnemyUI() {
        txtEnemyName.setText(currEnemy.getName());

    }
}