package com.att.biq.dst.jigsaw.PuzzleIndexing;

/**
 * enum holds the edges value
 */
public enum NodeType {
    _MALE (1),
    _FEMALE(-1),
    _STRAIGHT(0)
    ;

    private int value;

    NodeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    }



