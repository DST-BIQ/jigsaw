package com.att.biq.dst.jigsaw.puzzleManager;

public class PuzzlePiece {
    private int id;
    private int top;
    private int bottom;
    private int right;
    private int left;
    private int sumEdges;

    public PuzzlePiece(int top, int bottom, int right, int left){
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.sumEdges = top+bottom+left+right;
    }
    //TODO - implement toString
    @Override
    public String toString() {
        return "";
    }

    public boolean canBeTopLeft(){

        return top ==0&& left==0;
    }

    public int getSumEdges() {
        return sumEdges;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }



    @Override
    public boolean equals(Object obj) {
        PuzzlePiece comparedPiece = (PuzzlePiece)obj;
        return (this.id==comparedPiece.id && this.top==comparedPiece.top&& this.left==comparedPiece.left
                && this.right==comparedPiece.right&& this.bottom==comparedPiece.bottom);
    }
}
