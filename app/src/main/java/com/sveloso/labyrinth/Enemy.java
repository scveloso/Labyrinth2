package com.sveloso.labyrinth;

import android.graphics.drawable.Drawable;

/**
 * Created by sveloso on 2017-04-22.
 */

public interface Enemy {

    public String getName();

    public int getAttack();

    public Drawable getSrc();

    public int getLevel();

    public int getMaxHealth();

    public int getCurrHealth();

    public void setCurrHealth(int currHealth);

    public Move getNextMove();

    public int getCharge();

}
