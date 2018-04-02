package com.att.biq.dst.jigsaw.puzzleManager;

public class PuzzlePiece {

    private int top;
    private int bottom;
    private int right;
    private int left;

    public PuzzlePiece(int top, int bottom, int right, int left){
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }
    //TODO - implement toString
    @Override
    public String toString() {
        return "";
    }
}
