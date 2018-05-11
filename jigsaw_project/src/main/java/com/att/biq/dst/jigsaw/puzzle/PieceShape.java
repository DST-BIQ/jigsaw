package com.att.biq.dst.jigsaw.puzzle;

//TODO implement pieceShape in all solution areas
/**
 * @author dorit
 * this class represents the shape structure of the puzzle piece
 * relevant values = 0,1,-1
 * if 2 = means do not count this value on comparison.
 * We are using this class when using indexing -> creating map's keys, and later filtering.
 */
public class PieceShape {


    int[] edges = new int[4];

    public PieceShape( int left,int top,int right,int bottom){

        edges[0]=left;
        edges[1]=top;
        edges[2]=right;
        edges[3]=bottom;

    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceShape other = (PieceShape) o;

        return (
                  ((edges[0]==other.edges[0])||(edges[0]==2)||other.edges[0]==2))
                &&((edges[1]==other.edges[1])||(edges[1]==2||other.edges[1]==2))
                &&((edges[2]==other.edges[2])||(edges[2]==2||other.edges[2]==2))
                &&((edges[3]==other.edges[3])||(edges[3]==2||other.edges[3]==2));
    }

    @Override
    public int hashCode() {
        return edges[0] * 54 + edges[1] * 21 + edges[2] * 3 + edges[3];
    }

    @Override
    public String toString(){
        return edges[0]+","+edges[1]+","+edges[2]+","+edges[3];
    }

    public int getTop(){
        return edges[1];
    }

    public int getLeft(){
        return edges[0];
    }

    public int getRight(){
        return edges[2];
    }

    public int getBottom(){
        return edges[3];
    }




}
