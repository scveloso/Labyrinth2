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
    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public Move getNextMove(int playerCharge) {
        int move = moveGenerator.nextInt(3);
        if (move == 0) {
            return Move.CHARGE;
        } else if (move == 1) {
            switch (charge) {
                case 0:
                    charge++;
                    return Move.CHARGE;
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
        } else {
            switch (playerCharge) {
                case 0:
                    return Move.CHARGE;
                case 1:
                    return Move.CHARGE_1_BLOCK;
                case 2:
                    return Move.CHARGE_2_BLOCK;
                case 3:
                    return Move.CHARGE_3_BLOCK;
            }
        }
        return Move.LOAF;
    }
}