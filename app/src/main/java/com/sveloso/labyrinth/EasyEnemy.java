package com.sveloso.labyrinth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.Random;

/**
 * Created by s.veloso on 4/10/2017.
 */

public class EasyEnemy implements Enemy {

    private static final int MAX_HEALTH = 40;

    private String name;
    private int attack;
    private Drawable src;
    private int currHealth;
    private int level;

    private int charge;
    private Random moveGenerator;

    public EasyEnemy(Context context) {
        name = "Honeycomb";
        level = 1;
        currHealth = MAX_HEALTH;
        attack = 5;
        src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_easy);

        charge = 0;
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

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    public int getCharge() {
        return charge;
    }

    public Move getNextMove() {
        int move = moveGenerator.nextInt(3);
        // 50-50 chance to charge or attack
        if (move == 0) {
            return Move.CHARGE;
        } else /* Random attack based on number of charges */ {
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