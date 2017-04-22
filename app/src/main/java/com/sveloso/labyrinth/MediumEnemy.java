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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public Drawable getSrc() {
        return src;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    @Override
    public int getCurrHealth() {
        return currHealth;
    }

    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    @Override
    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public Move getNextMove(int playerCharge) {
        if (playerCharge == 0) {
            return Move.CHARGE;
        }

        int move = moveGenerator.nextInt(2);

        if (move == 0 || charge == 0) /* block randomly based on player charge, or if have no charges */ {
            switch (playerCharge) {
                case 1:
                    return Move.CHARGE_1_BLOCK;
                case 2:
                    return Move.CHARGE_2_BLOCK;
                case 3:
                    return Move.CHARGE_3_BLOCK;
            }
        } else /* attack randomly based on charge */ {
            switch (charge) {
                case 1:
                    charge--;
                    return Move.CHARGE_1_ATTACK;
                case 2:
                    charge -= 2;
                    return Move.CHARGE_2_ATTACK;
                case 3:
                    charge -= 3;
                    return Move.CHARGE_3_ATTACK;
            }
        }
        return Move.LOAF;
    }
}