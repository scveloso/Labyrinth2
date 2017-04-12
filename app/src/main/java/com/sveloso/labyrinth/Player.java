package com.sveloso.labyrinth;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by s.veloso on 4/11/2017.
 */

public class Player implements Parcelable{

    private int health;
    private int currHealth;

    public Player() {
        health = 100;
        currHealth = health;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}