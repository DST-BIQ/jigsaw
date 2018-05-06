package com.att.biq.dst.jigsaw.puzzle;

public class PuzzlePiece {

    private int id;
    private int top;
    private int bottom;
    private int right;
    private int left;
    private int sumEdges;
    private int rotation=0;


    public PuzzlePiece(int id,int left, int top, int right, int bottom){
        this.id = id;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.sumEdges = top+bottom+left+right;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public PuzzlePiece rotate(int num, int rotation) {
        PuzzlePiece puzzlePiece = new PuzzlePiece(getId(),left,top,right,bottom);
        int temp=0;
        for (int i=0 ;i<num ;i++) {
            temp = puzzlePiece.top;
            puzzlePiece.top = puzzlePiece.left;
            puzzlePiece.left = puzzlePiece.bottom;
            puzzlePiece.bottom = puzzlePiece.right;
            puzzlePiece.right = temp;
        }
        setRotation(rotation);
        return puzzlePiece;
    }

    public boolean isTopLeft() {
        return (top == 0 && left == 0);
    }

    public boolean isTopRight() {
        return (top == 0 && right == 0);
    }

    public boolean isBottomRight() {
        return (bottom == 0 && right == 0);
    }

    public boolean isBottomLeft() {
        return (bottom==0&&left==0);
    }

    public int getId() {
        return id;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }

    public int getLeft() {
        return left;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getSumEdges() {
        return sumEdges;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof PuzzlePiece)) {
            return false;
        }
        PuzzlePiece o = (PuzzlePiece) other;
        return (o.id==id && o.rotation==rotation);
    }


    @Override
    public String toString() {
        if (rotation != 0) {
            return (id + " " + "[" + rotation + "]'");
        } else {
            return (id + " ");
        }
    }
}
