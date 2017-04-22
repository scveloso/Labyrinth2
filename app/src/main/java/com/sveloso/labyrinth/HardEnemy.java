package com.sveloso.labyrinth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by sveloso on 2017-04-22.
 */

public class HardEnemy implements Enemy {

    private static final int MAX_HEALTH = 80;

    private String name;
    private int attack;
    private Drawable src;
    private int currHealth;
    private int level;

    private int charge;

    public HardEnemy (Context context) {
        name = "KitKat";
        level = 3;
        currHealth = MAX_HEALTH;
        attack = 20;
        src = ContextCompat.getDrawable(context, R.mipmap.img_enemy_hard);
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

    public Move getNextMove() {
        return Move.CHARGE_1_ATTACK;
    }
}
