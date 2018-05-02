package com.att.biq.dst.jigsaw.puzzle;

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

        return edges[0]==other.edges[0] &&edges[1]==other.edges[1]&&edges[2]==other.edges[2]&&edges[3]==other.edges[3];
    }

    @Override
    public int hashCode() {
        return edges[0] * 54 + edges[1] * 21 + edges[2] * 3 + edges[3];
    }



}
