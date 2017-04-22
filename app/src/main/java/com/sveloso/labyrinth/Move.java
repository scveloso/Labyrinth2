package com.sveloso.labyrinth;

/**
 * Created by sveloso on 2017-04-22.
 */

public enum Move {
    LOAF (0, "loaf"),
    CHARGE_1_ATTACK (1, "attack"),
    CHARGE_2_ATTACK (2, "attack"),
    CHARGE_3_ATTACK (3, "attack"),
    CHARGE_1_BLOCK (1, "block"),
    CHARGE_2_BLOCK (2, "block"),
    CHARGE_3_BLOCK (3, "block"),
    CHARGE (0, "charge");

    private final int power;
    private final String moveType;

    private Move (int chargeCost, String moveType) {
        this.power = chargeCost;
        this.moveType = moveType;
    }

    public int getPower() {
        return power;
    }

    public String getMoveType() {
        return moveType;
    }
}