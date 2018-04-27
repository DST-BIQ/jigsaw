package com.att.biq.dst.jigsaw.PuzzleIndexing;

import java.util.ArrayList;
import java.util.List;


//TODO node containing list of chile nodes (next level children)
public class Node {
    /**
     * Node containing the list of rotated pieces (null if not on lowest level)
     * tree level - by order of left, up, right, bottom
     * node value - 1,0,-1
     * reference to next node
     */

private    ArrayList<RotatedPiece> rotatedPieceList;
    private Edges treeLevel;
    private int nodeValue;
    private Node next;
    private List<Node> children; // list of children of this node. for 0,1,-1

    //

    /**
     * create new node. represents tree node. holds details like number value (1,0,-1) and edge symbole (right, left ect) +  relevant rotated pieces
     *
     * @param treeLevel - 1,2,3,4 (1=left, 2=up, 3 = right, 4 = bottom)
     * @param nodeValue - 0,1,-1
     */

    public Node(Edges treeLevel, int nodeValue) {

        this.nodeValue = nodeValue;
        this.treeLevel = treeLevel;
        rotatedPieceList = new ArrayList<>();


    }


    /**
     * Add rotatedPiece to Node's rotated piece list
     *
     * @param rotatedPiece ece
     */
    public void addRotatedPieceToNode(RotatedPiece rotatedPiece) {
        rotatedPieceList.add(rotatedPiece);
    }


    public void setNext(Node next) {
        this.next = next;

    }

    public Node getNext() {
        return next;
    }


    public Edges getTreeLevel() {
        return treeLevel;
    }

    public int getNodeValue() {
        return nodeValue;
    }


    public ArrayList<RotatedPiece> getRotatedPieceList(){
        return rotatedPieceList;
    }


    /**
     * this will decide if to add the rotatedPiec to the node.
     * @param rotatedPiece
     */

    public void addRotatedPiece(RotatedPiece rotatedPiece){

        /// only if on the bottom level, add the piece to the correct location)

        // parse rotatedPiece list and look for the node.



        if (getTreeLevel()==Edges._BOTTOM){
            addRotatedPieceToNode(rotatedPiece);

        }
    }
}



