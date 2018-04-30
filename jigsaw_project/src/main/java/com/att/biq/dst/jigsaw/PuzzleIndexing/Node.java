package com.att.biq.dst.jigsaw.PuzzleIndexing;

import java.util.ArrayList;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Node containing the list of rotated pieces (null if not on lowest level)
 * tree level - by order of left, up, right, bottom
 * node value - 1,0,-1
 * reference to next node
 */
//TODO node containing list of chile nodes (next level children)
public class Node implements Comparable<Node>{


    private ArrayList<RotatedPiece> rotatedPieceList;

    private int treeLevel;
    private NodeType nodeType;
    private Node nextStraight;
    private Node nextFemale;
    private Node nextMale;


    private SortedSet<Node> children; // list of children of this node. for 0,1,-1


    /**
     * create new node. represents tree node. holds details like number value (1,0,-1) and edge symbole (right, left ect) +  relevant rotated pieces
     *
     * @param treeLevel - 1,2,3,4 (1=left, 2=up, 3 = right, 4 = bottom)
     * @param nodeType - 0,1,-1
     */

    public Node(int treeLevel, NodeType nodeType) {

        this.nodeType = nodeType;
        this.treeLevel = treeLevel;
        rotatedPieceList = new ArrayList<>();
        children = new TreeSet<>();



    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Add rotatedPiece to Node's rotated piece list
     *
     * @param rotatedPiece ece
     */
    public void addRotatedPieceToNode(RotatedPiece rotatedPiece) {
        rotatedPieceList.add(rotatedPiece);
    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }

    public void setNextMale(Node node) {
        this.nextMale = node;

    }

    public void setNextStraight(Node node) {
        this.nextStraight = node;

    }

    public void setNextFemale(Node node) {
        this.nextFemale = node;

    }

    public Node getNextStraight() {
        return nextStraight;
    }

    public Node getNextFemale() {
        return nextFemale;
    }

    public Node getNextMale() {
        return nextMale;
    }

    public int getTreeLevel() {
        return treeLevel;
    }

    public NodeType getNodeType() {
        return nodeType;
    }


    public Node getNextByValue (NodeType nodeType){

        switch (nodeType) {
            case _STRAIGHT:
                return nextStraight;
            case _MALE:
                return nextMale;
            case _FEMALE:
                return nextFemale;
        }
        return null;
        }



    public ArrayList<RotatedPiece> getRotatedPieceList() {
        return rotatedPieceList;
    }


//    public void setChildrenList() {
//
//        nextStraight = new Node(getTreeLevel() + 1, 0);
//        nextFemale = new Node(getTreeLevel() + 1, -1);
//        nextMale = new Node(getTreeLevel() + 1, 1);
//        children.add(nextStraight);
//        children.add(nextFemale);
//        children.add(nextMale);
//
//
//    }

    public SortedSet<Node> getChildren() {
        return children;
    }

    /**
     * this will decide if to add the rotatedPiece to the node.
     *
     * @param rotatedPiece
     */

    public void addRotatedPiece(RotatedPiece rotatedPiece) {

        /// only if on the bottom level, add the piece to the correct location)

        // parse rotatedPiece list and look for the node.


//        if (getTreeLevel()==Edges._BOTTOM){
//            addRotatedPieceToNode(rotatedPiece);
//
//        }
    }




    @Override
    public int compareTo(Node o) {
        if (getTreeLevel()==o.getTreeLevel()&& getNodeType()==o.getNodeType()){
            return 0;
        }
        return 1;
    }
}



