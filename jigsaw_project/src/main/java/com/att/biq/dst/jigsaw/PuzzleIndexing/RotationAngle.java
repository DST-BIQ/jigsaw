package com.att.biq.dst.jigsaw.PuzzleIndexing;


/**
 * Enum holds the rotation angles values
 */
public enum RotationAngle {
    ZERO(0),
    Ninety(90),
    ONEHUNDRED_EIGHTY(180),
    TWOHUMNDRED_SEVENTY(270)
    ;

    private int value;

    RotationAngle(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
