package com.att.biq.dst.jigsaw.PuzzleIndexing;


/**
 * Enum holds the rotation angles values
 */
public enum RotationAngle {
    _ZERO(0),
    _NINETY(90),
    _ONEHUNDRED_EIGHTY(180),
    _TWOHUMNDRED_SEVENTY(270)
    ;

    private int value;

    RotationAngle(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
