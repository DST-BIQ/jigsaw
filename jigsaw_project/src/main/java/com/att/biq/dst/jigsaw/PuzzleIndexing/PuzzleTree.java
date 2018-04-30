package com.att.biq.dst.jigsaw.PuzzleIndexing;

import java.util.ArrayList;

/**
 * Puzzle tree will handle the tree creation and accessing
 * We assume the tree is fixed = 4 levels, and optional values of 1,0,-1
 */

public class PuzzleTree {


    private Node rootNode;
    private Node nextNode;
    private ArrayList<RotatedPiece> rotatedPieces;
    private int currentTreeLevel;


    public PuzzleTree() {

        currentTreeLevel = 0;
        rootNode = new Node(currentTreeLevel, NodeType._STRAIGHT);
        createTreeStructure();
    }

    /**
     * Create tree on the levels we asked
     */
    private void createTreeStructure() {


        addNodesToTree();

    }

//TODO make recursive

    public void addNodesToTree() {

        while (currentTreeLevel <= 4) {
            currentTreeLevel++;


            if (currentTreeLevel == 1) {
                rootNode.setNextFemale(new Node(currentTreeLevel, NodeType._FEMALE));
                rootNode.setNextMale(new Node(currentTreeLevel, NodeType._MALE));
                rootNode.setNextStraight(new Node(currentTreeLevel, NodeType._STRAIGHT));
                nextNode = rootNode;

            }

            if (currentTreeLevel == 2) {
                Node tempNode;

                tempNode = nextNode.getNextMale();
                setNextNodeValues(tempNode);
                nextNode.setNextMale(tempNode);

                tempNode = nextNode.getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.setNextFemale(tempNode);

                tempNode = nextNode.getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.setNextStraight(tempNode);
                rootNode = nextNode;

            }

            if (currentTreeLevel == 3) {
                Node tempNode;

                tempNode = nextNode.getNextMale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().setNextMale(tempNode);

                tempNode = nextNode.getNextMale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().setNextFemale(tempNode);

                tempNode = nextNode.getNextMale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().setNextStraight(tempNode);

//
                tempNode = nextNode.getNextFemale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().setNextMale(tempNode);

                tempNode = nextNode.getNextFemale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().setNextFemale(tempNode);

                tempNode = nextNode.getNextFemale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().setNextStraight(tempNode);
//
                tempNode = nextNode.getNextStraight().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().setNextMale(tempNode);

                tempNode = nextNode.getNextStraight().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().setNextFemale(tempNode);

                tempNode = nextNode.getNextStraight().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().setNextStraight(tempNode);
                rootNode = nextNode;



            }

            if (currentTreeLevel == 4) {
                Node tempNode;

                tempNode = nextNode.getNextMale().getNextMale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextMale().setNextMale(tempNode);

                tempNode = nextNode.getNextMale().getNextMale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextMale().setNextFemale(tempNode);

                tempNode = nextNode.getNextMale().getNextMale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextMale().setNextStraight(tempNode);

//
                tempNode = nextNode.getNextMale().getNextFemale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextFemale().setNextMale(tempNode);

                tempNode = nextNode.getNextMale().getNextFemale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextFemale().setNextFemale(tempNode);

                tempNode = nextNode.getNextMale().getNextFemale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextFemale().setNextStraight(tempNode);
//
                tempNode = nextNode.getNextMale().getNextStraight().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextStraight().setNextMale(tempNode);

                tempNode = nextNode.getNextMale().getNextStraight().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextStraight().setNextFemale(tempNode);

                tempNode = nextNode.getNextMale().getNextStraight().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextMale().getNextStraight().setNextStraight(tempNode);
//

                tempNode = nextNode.getNextFemale().getNextMale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextMale().setNextMale(tempNode);

                tempNode = nextNode.getNextFemale().getNextMale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextMale().setNextFemale(tempNode);

                tempNode = nextNode.getNextFemale().getNextMale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextMale().setNextStraight(tempNode);

//
                tempNode = nextNode.getNextFemale().getNextFemale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextFemale().setNextMale(tempNode);

                tempNode = nextNode.getNextFemale().getNextFemale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextFemale().setNextFemale(tempNode);

                tempNode = nextNode.getNextFemale().getNextFemale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextFemale().setNextStraight(tempNode);
//
                tempNode = nextNode.getNextFemale().getNextStraight().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextStraight().setNextMale(tempNode);

                tempNode = nextNode.getNextFemale().getNextStraight().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextStraight().setNextFemale(tempNode);

                tempNode = nextNode.getNextFemale().getNextStraight().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextFemale().getNextStraight().setNextStraight(tempNode);

                tempNode = nextNode.getNextStraight().getNextMale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextMale().setNextMale(tempNode);

                tempNode = nextNode.getNextStraight().getNextMale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextMale().setNextFemale(tempNode);

                tempNode = nextNode.getNextStraight().getNextMale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextMale().setNextStraight(tempNode);

//
                tempNode = nextNode.getNextStraight().getNextFemale().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextFemale().setNextMale(tempNode);

                tempNode = nextNode.getNextStraight().getNextFemale().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextFemale().setNextFemale(tempNode);

                tempNode = nextNode.getNextStraight().getNextFemale().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextFemale().setNextStraight(tempNode);
//
                tempNode = nextNode.getNextStraight().getNextStraight().getNextMale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextStraight().setNextMale(tempNode);

                tempNode = nextNode.getNextStraight().getNextStraight().getNextFemale();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextStraight().setNextFemale(tempNode);

                tempNode = nextNode.getNextStraight().getNextStraight().getNextStraight();
                setNextNodeValues(tempNode);
                nextNode.getNextStraight().getNextStraight().setNextStraight(tempNode);

                rootNode = nextNode;



            }


//                nextNode = currentNode;
//                .getNextStraight();
//                System.out.println("this is the next streight node level "+nextNode.getTreeLevel()+"   type:  "+nextNode.getNodeType());
//            setNextMaleNodeValues();
////                nextNode = currentNode.getNextMale();
//                setNextMaleNodeValues(currentTreeLevel, NodeType._MALE);
////                nextNode = currentNode.getNextFemale();
//                setNextMaleNodeValues(currentTreeLevel, NodeType._FEMALE);
//            rootNode = nextNode;
//            nextNode.setNextMale(nextNode.getNextMale().getNextMale());
//            nextNode = nextNode.getNextMale().getNextMale();


        }

//        addNodesToTree();


    }





    //TODO  do we need to set tree level and type for current here, or do we do that already?
    private void setNextNodeValues(Node node) {
        node.setNextMale(new Node(currentTreeLevel, NodeType._MALE));
        node.setNextFemale(new Node(currentTreeLevel, NodeType._FEMALE));
        node.setNextStraight(new Node(currentTreeLevel, NodeType._STRAIGHT));
    }


    public void printRootNode() {
        System.out.println("node level: " + rootNode.getTreeLevel());
        System.out.println("node childrenList: " + rootNode.getChildren());

    }

    /**
     * we get puzzlepieces we assume the order of tree is: left, up, right,. bottom
     *
     * @param rotatedPiece
     */
    public void addPieceToTree(RotatedPiece rotatedPiece) {
        ArrayList<NodeType> array = getPathArrayFromPuzzlePiece(rotatedPiece);
        rootNode.getNextByValue(array.get(0)).getNextByValue(array.get(1)).getNextByValue(array.get(2)).getNextByValue(array.get(3)).addRotatedPieceToNode(rotatedPiece);

    }

    /**
     * Creating array of NodeTypes, according to the piece
     *
     * @param rotatedPiece - rotated piece to create path
     * @return array list of NodeType. wlll be used later for accessing the node and add / retrieve the piece
     */
    public ArrayList<NodeType> getPathArrayFromPuzzlePiece(RotatedPiece rotatedPiece) {
        ArrayList<NodeType> array = new ArrayList<>();
        NodeType nodeType = getEdgeValue(rotatedPiece.puzzlePiece.getLeft());
        array.add(nodeType);
        nodeType = getEdgeValue(rotatedPiece.puzzlePiece.getTop());
        array.add(nodeType);
        nodeType = getEdgeValue(rotatedPiece.puzzlePiece.getRight());
        array.add(nodeType);
        nodeType = getEdgeValue(rotatedPiece.puzzlePiece.getBottom());
        array.add(nodeType);
        return array;
    }


    /**
     * get rotated piece list from specific leaf
     * create the array, access the leaft and retrieve the list
     *
     * @param rotatedPiece - according to this we will look for the path
     * @return Array list of rotated pieces.
     */
    public ArrayList<RotatedPiece> getRotatedPiecesFromNode(RotatedPiece rotatedPiece) {
        ArrayList<NodeType> array = getPathArrayFromPuzzlePiece(rotatedPiece);
        return rootNode.getNextByValue(array.get(0)).getNextByValue(array.get(1)).getNextByValue(array.get(2)).getNextByValue(array.get(3)).getRotatedPieceList();


    }

    /**
     * translate edge value to nodeType
     *
     * @param edgeValue - from rotated piece top, left, up, bottom
     * @return relevant nodeType
     */
    public NodeType getEdgeValue(int edgeValue) {

        switch (edgeValue) {
            case 1:
                return NodeType._MALE;
            case 0:
                return NodeType._STRAIGHT;
            case -1:
                return NodeType._FEMALE;
        }

        return null;

    }


    public void getPiecesArray() {

    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        Node curr = rootNode;
        while (curr.getNextFemale() != null) {
            s.append("You are on Node: ");
            s.append("Node value = " + curr.getNodeType() + " rootNode treeLevel:  " + curr.getTreeLevel());
            s.append(' ');
            curr = curr.getNextFemale();
        }
        return s.toString();
    }


}
