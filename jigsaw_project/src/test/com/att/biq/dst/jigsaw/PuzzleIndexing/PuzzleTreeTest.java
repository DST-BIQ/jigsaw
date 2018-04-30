package com.att.biq.dst.jigsaw.PuzzleIndexing;

import com.att.biq.dst.jigsaw.puzzle.PuzzlePiece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PuzzleTreeTest {


    @Test
    void createTreeStructure() {


    }


    //    public void addNodesToTree(int treeLevel) {
//
////        Node nextNode = new Node(treeLevel,0);
//
//
//        node.createNodeSet();
//
//
//        if (node.getTreeLevel() <= treeLevel) {
//
//            addNodesToTree(++treeLevel);
//            node.setNext(node.getChildren());
//
//        } else {
//            return;
//        }
//
//
//    }
    @Test
    void addNodesToTreeTest() {

        PuzzleTree puzzleTree = new PuzzleTree();


//        puzzleTree.addNodesToTree(0);
//        puzzleTree.printRootNode();

//        System.out.println(puzzleTree);


//        Assertions.assertTrue(puzzleTree.);

    }



    @Test
    void addPieceToTreeAllSameValue() {



        PuzzleTree puzzleTree = new PuzzleTree();

        PuzzlePiece puzzlePiece = new PuzzlePiece(23,1,1,1,1);

        RotatedPiece rotatedPiece = new RotatedPiece(puzzlePiece,90);
        puzzleTree.addPieceToTree(rotatedPiece);


        ArrayList<RotatedPiece> testedArray = new ArrayList<>();
        testedArray.add(rotatedPiece);

        assertEquals(puzzleTree.getRotatedPiecesFromNode(rotatedPiece).get(0).rotationAngle,testedArray.get(0).rotationAngle);
    }


    @Test
    void addPieceToTreeDifferentValues() {



        PuzzleTree puzzleTree = new PuzzleTree();

        PuzzlePiece puzzlePiece = new PuzzlePiece(23,-1,1,0,1);

        RotatedPiece rotatedPiece = new RotatedPiece(puzzlePiece,90);
        puzzleTree.addPieceToTree(rotatedPiece);


        ArrayList<RotatedPiece> testedArray = new ArrayList<>();
        testedArray.add(rotatedPiece);

        assertEquals(puzzleTree.getRotatedPiecesFromNode(rotatedPiece).get(0).rotationAngle,testedArray.get(0).rotationAngle);
    }


    @Test
    void addPieceToTreeNegative() {



        PuzzleTree puzzleTree = new PuzzleTree();

        PuzzlePiece puzzlePiece = new PuzzlePiece(23,-1,1,0,1);

        RotatedPiece rotatedPiece = new RotatedPiece(puzzlePiece,90);
        puzzleTree.addPieceToTree(rotatedPiece);


        ArrayList<RotatedPiece> testedArray = new ArrayList<>();
        RotatedPiece rotatedPiece1 = new RotatedPiece(puzzlePiece,180);
        testedArray.add(rotatedPiece1);

        assertNotEquals(puzzleTree.getRotatedPiecesFromNode(rotatedPiece).get(0).rotationAngle,testedArray.get(0).rotationAngle);
    }



    @Test
    void addPieceToTreeFewPieces() {



        PuzzleTree puzzleTree = new PuzzleTree();

        PuzzlePiece puzzlePiece = new PuzzlePiece(23,-1,1,0,1);

        RotatedPiece rotatedPiece = new RotatedPiece(puzzlePiece,90);
        RotatedPiece rotatedPiece1 = new RotatedPiece(puzzlePiece,0);
        RotatedPiece rotatedPiece2 = new RotatedPiece(puzzlePiece,270);
        puzzleTree.addPieceToTree(rotatedPiece);
        puzzleTree.addPieceToTree(rotatedPiece1);
        puzzleTree.addPieceToTree(rotatedPiece2);


        ArrayList<RotatedPiece> testedArray = new ArrayList<>();
        testedArray.add(rotatedPiece);
        testedArray.add(rotatedPiece1);
        testedArray.add(rotatedPiece2);


        for (int i=0;i<testedArray.size();i++){
            assertEquals(puzzleTree.getRotatedPiecesFromNode(rotatedPiece).get(i).rotationAngle,testedArray.get(i).rotationAngle);
        }

    }

}