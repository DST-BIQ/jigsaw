package com.att.biq.dst.jigsaw.puzzle.server;

/**
 * @ author - Dorit, Stav, Tal
 * this class represents the PuzzlePiece with all edges after converting and the rotation
 */
public class PuzzlePiece {

    private int id;
    private int top;
    private int bottom;
    private int right;
    private int left;
    private int sumEdges;
    private boolean inUse;

    private int rotation=0;


    public PuzzlePiece(int id,int left, int top, int right, int bottom){
        this.id = id;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.sumEdges = top+bottom+left+right;
        this.inUse=false;
    }

    public int getRotation() {
        return rotation;
    }

    /**
     *
     * @param numTimesToRotate
     */
    public void setRotation(int numTimesToRotate) {

        int[] array=new int[4];
        array[0]=0;array[1]=90;array[2]=180;array[3]=270;

        int newLocation=decideCurrentLocation(this.rotation);
        for (int i=1;i<=numTimesToRotate;i++){
            newLocation = (newLocation + 1) % 4;
        }
        this.rotation = array[newLocation];


    }

    private int decideCurrentLocation(int rotation) {
        switch (rotation){
            case 0: return 0;

            case 90: return 1;

            case 180: return 2;

            case 270: return 3;


        }
        return -1;
    }

    public void rotate(int numTimesToRotate) {

        int temp;
        for (int i=0 ;i<numTimesToRotate ;i++) {
            temp = this.top;
            this.top = this.left;
            this.left = this.bottom;
            this.bottom = this.right;
            this.right = temp;
        }
        setRotation(numTimesToRotate);

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

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
