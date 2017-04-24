package com.sveloso.labyrinth;

/**
 * Created by sveloso on 2017-04-22.
 */

public enum Move {
    LOAF (0, "loaf", "Loaf"),
    CHARGE_1_ATTACK (1, "attack", "Bang"),
    CHARGE_2_ATTACK (2, "attack", "Double Bang"),
    CHARGE_3_ATTACK (3, "attack", "Hula-hula"),
    CHARGE_1_BLOCK (1, "block", "Absorb"),
    CHARGE_2_BLOCK (2, "block", "Shield"),
    CHARGE_3_BLOCK (3, "block", "Hula-bsorb"),
    CHARGE (0, "charge", "Charge");

    private final int power;
    private final String moveType;
    private final String name;

    private Move (int chargeCost, String moveType, String name) {
        this.power = chargeCost;
        this.moveType = moveType;
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public String getMoveType() {
        return moveType;
    }

    public String getName() {
        return name;
    }
}