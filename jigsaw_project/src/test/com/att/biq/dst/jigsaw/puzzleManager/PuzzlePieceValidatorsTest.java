package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class PuzzlePieceValidatorsTest {
    List<PuzzlePiece> puzzlePieces;
    PuzzlePieceValidators ppv;
    FileManager fm = new FileManager("src/resources/output/report_");



    @Test
    public void ValidateAllPossitve(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,1,1,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,0,-1,1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,-1,0,0);
        PuzzlePiece p4 = new PuzzlePiece(4,1,0,0,-1);
        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertTrue(ppv.validatePuzzle(puzzlePieces, fm));
    }

    @Test
    public void ValidateMissingTLCorner(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,1,1,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,-1,-1,1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,0,0,0);
        PuzzlePiece p4 = new PuzzlePiece(4,1,0,0,-1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, fm));
    }


    @Test
    public void ValidateMissingTRCorner(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,0,1,1);
        PuzzlePiece p2 = new PuzzlePiece(2,-1,-1,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,1,1,0,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,0);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, fm));
    }

    @Test
    public void ValidateMissingBRCorner(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,0,0,1);
        PuzzlePiece p2 = new PuzzlePiece(2,-1,0,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,1,0,1,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,0);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, fm));
    }

    @Test
    public void ValidateMissingBLCorner(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,1,0,0,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,0,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,0,1,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, fm));
    }

    @Test
    public void ValidateEdgesNotZero(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,1,0,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,0,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,0,1,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, fm));
    }

    @Test
    public void ValidateWorngStraightEdges(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,1,1,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,0,-1,1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,-1,0,0);
        PuzzlePiece p4 = new PuzzlePiece(4,1,0,0,-1);
        PuzzlePiece p5 = new PuzzlePiece(4,1,1,1,1);
        PuzzlePiece p6 = new PuzzlePiece(4,-1,-1,-1,-1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);
        puzzlePieces.add(p5);
        puzzlePieces.add(p6);



        assertFalse(ppv.validatePuzzle(puzzlePieces, fm));
    }

    @Test
    public void ValidateAllErrorsCombination(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,1,1,1,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,1,-1,1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,-1,1,0);
        PuzzlePiece p4 = new PuzzlePiece(4,1,1,0,-1);
        PuzzlePiece p5 = new PuzzlePiece(4,1,1,1,1);
        PuzzlePiece p6 = new PuzzlePiece(4,-1,-1,-1,-1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);
        puzzlePieces.add(p5);
        puzzlePieces.add(p6);



        assertFalse(ppv.validatePuzzle(puzzlePieces, fm));
    }



}