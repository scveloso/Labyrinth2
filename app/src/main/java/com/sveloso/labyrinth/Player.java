package com.sveloso.labyrinth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by s.veloso on 4/11/2017.
 */

public class Player {

    public static final int PLAYER_HEALTH = 100;
    public static final int PLAYER_BASE_ATTACK = 20;

    private int health;
    private int attack;
    private int currHealth;

    public Player() {
        currHealth = health;
        attack = PLAYER_BASE_ATTACK;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }
}