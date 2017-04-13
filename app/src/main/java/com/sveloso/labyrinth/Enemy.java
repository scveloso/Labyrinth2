package com.sveloso.labyrinth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by s.veloso on 4/10/2017.
 */

public class Enemy {

    private static final int LEVEL_0_ENEMY_HEALTH = 40;
    private static final int LEVEL_1_ENEMY_HEALTH = 60;
    private static final int LEVEL_2_ENEMY_HEALTH = 80;

    private String name;
    private int attack;
    private Drawable src;

    private int currHealth;

    private int level;

    public Enemy (Context context, int level) {
        this.level = level;

        switch (level) {
            case 0:
                name = "Honeycomb";
                currHealth = LEVEL_0_ENEMY_HEALTH;
                attack = 5;
                src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_easy);

                break;
            case 1:
                name = "Jelly Bean";
                currHealth = LEVEL_1_ENEMY_HEALTH;
                attack = 10;
                src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_medium);

                break;
            case 2:
                name = "KitKat";
                currHealth = LEVEL_2_ENEMY_HEALTH;
                attack = 20;
                src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_hard);

                break;
        }
    }

    public int getHealth() {
        switch (level) {
            case 0:
                return LEVEL_0_ENEMY_HEALTH;
            case 1:
                return LEVEL_1_ENEMY_HEALTH;
            case 2:
                return LEVEL_2_ENEMY_HEALTH;
            default:
                return -1;
        }
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public Drawable getSrc() {
        return src;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }
}