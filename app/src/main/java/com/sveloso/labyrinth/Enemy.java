package com.sveloso.labyrinth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by s.veloso on 4/10/2017.
 */

public class Enemy {

    private String name;
    private int health;
    private int attack;
    private Drawable src;

    private int currHealth;

    private int level;

    public Enemy (Context context, int level) {
        this.level = level;

        switch (level) {
            case 0:
                name = "Honeycomb";
                health = 40;
                currHealth = health;
                attack = 5;
                src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_easy);

                break;
            case 1:
                name = "Jelly Bean";
                health = 60;
                currHealth = health;
                attack = 10;
                src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_medium);

                break;
            case 2:
                name = "KitKat";
                health = 80;
                currHealth = health;
                attack = 20;
                src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_hard);

                break;
        }
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
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