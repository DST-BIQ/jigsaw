package com.att.biq.dst.jigsaw.puzzle;

public class PuzzlePiece {

    private int id;
    private int top;
    private int bottom;
    private int right;
    private int left;
    private int sumEdges;
    private boolean inUse;


    public PuzzlePiece(int id,int left, int top, int right, int bottom){
        this.id=id;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.sumEdges = top+bottom+left+right;
        this.inUse=false;
    }

    public boolean isTopLeft() {
        return (top==0&&left==0);
    }

    public boolean isTopRight() {
        return (top==0&&right==0);
    }

    public boolean isBottomright() {
        return (bottom==0&&right==0);
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


    public PieceShape getEdgesFromPiece(){

        int[] edges = new int[4];
        edges[0]=getLeft();
        edges[1]=getTop();
        edges[2]=getRight();
        edges[3]=getBottom();

        return new PieceShape(edges[0],edges[1],edges[2],edges[3]);

    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof PuzzlePiece)) {
            return false;
        }
        PuzzlePiece o = (PuzzlePiece) other;
        return (o.id==id && o.top==top && o.right==right && o.left==left && o.bottom==bottom);
    }

    //TODO - implement toString
    @Override
    public String toString() {
       return  ( id +" ");
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
