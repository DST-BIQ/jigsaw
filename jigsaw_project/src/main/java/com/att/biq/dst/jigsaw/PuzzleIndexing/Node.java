package com.att.biq.dst.jigsaw.PuzzleIndexing;

import java.util.ArrayList;

public class Node {


    ArrayList<RotatedPiece> rotatedPieceList;

    int treeLevel;
    int edgeValue;

    //

    /**
     * create new node. represents tree node. holds details like number value (1,0,-1) and edge symbole (right, left ect) +  relevant rotated pieces
     * @param treeLevel - 1,2,3,4 (1=left, 2=up, 3 = right, 4 = bottom)
     * @param edgeValue - 0,1,-1
     *
     */

    public Node(int treeLevel,int edgeValue){

        this.edgeValue = edgeValue;
        this.treeLevel=treeLevel;
        rotatedPieceList = new ArrayList<>();


    }


    /**
     * Add rotatedPiece to Node's rotated piece list
     * @param rotatedPiece
     * ece
     */
    public void addRotatedPieceToNode(RotatedPiece rotatedPiece){
        rotatedPieceList.add(rotatedPiece);
    }






}
