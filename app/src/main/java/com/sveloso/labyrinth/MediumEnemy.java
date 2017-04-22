package com.sveloso.labyrinth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.Random;

/**
 * Created by sveloso on 2017-04-22.
 */

public class MediumEnemy implements Enemy {

    private static final int MAX_HEALTH = 60;

    private String name;
    private int attack;
    private Drawable src;
    private int currHealth;
    private int level;

    private int charge;
    private Random moveGenerator;

    public MediumEnemy (Context context) {
        name = "Jelly Bean";
        level = 2;
        currHealth = MAX_HEALTH;
        attack = 10;
        src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_medium);

        charge = 1;
        moveGenerator = new Random();
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

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public int getCharge() {
        return charge;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    public Move getNextMove() {
        int move = moveGenerator.nextInt(3);
        // 50-50 chance to charge or attack
        if (move == 0) {
            return Move.CHARGE;
        } else if (move == 1) /* Random attack based on number of charges */ {
            int attack = moveGenerator.nextInt(charge + 1);
            switch (attack) {
                case 0:
                    return Move.CHARGE_0_ATTACK;
                case 1:
                    return Move.CHARGE_1_ATTACK;
                case 2:
                    return Move.CHARGE_2_ATTACK;
                case 3:
                    return Move.CHARGE_3_ATTACK;
            }
        }
        return Move.LOAF;
    }
}