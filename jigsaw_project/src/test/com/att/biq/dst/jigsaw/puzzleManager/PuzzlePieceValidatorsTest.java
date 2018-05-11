package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.puzzle.ErrorsManager;
import com.att.biq.dst.jigsaw.puzzle.Puzzle;
import com.att.biq.dst.jigsaw.puzzle.PuzzlePiece;
import com.att.biq.dst.jigsaw.puzzle.PuzzlePieceValidators;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PuzzlePieceValidatorsTest {
    List<PuzzlePiece> puzzlePieces;
    PuzzlePieceValidators ppv;
    ArrayList<int[]> puzzleArray;
    Puzzle puzzle;







    @Test
    public void validatePuzzleRotate(){
        puzzle = new Puzzle(new ErrorsManager(), true);
        puzzleArray = new ArrayList<>();
        puzzlePieces = new ArrayList<>();
        int[] pa1 = new int[] {1,0,-1,0,1};
        puzzleArray.add(pa1);
        PuzzlePiece p1 = new PuzzlePiece(1,0,-1,0,1);
        puzzlePieces.add(p1);
        puzzlePieces.add(new PuzzlePiece(1,1,0,-1,0));
        puzzlePieces.add(new PuzzlePiece(1,0,1,0,-1));
        puzzlePieces.add(new PuzzlePiece(1,-1,0,1,0));
       // puzzle.convertPuzzleArray(puzzleArray);
        assertArrayEquals(puzzlePieces,puzzle.convertPuzzleArray(puzzleArray));
       // assertTrue(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }

    private void assertArrayEquals(List<PuzzlePiece> puzzlePieces, List<PuzzlePiece> puzzlePieces1) {
        for (int i=0;i<puzzlePieces.size();i++) {
            assertEquals(puzzlePieces.get(i),puzzlePieces1.get(i));

        }
    }

    @Test
    public void ValidateAllPossitve(){
        ppv = new PuzzlePieceValidators(true);
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,1,1,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,0,-1,1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,-1,0,0);
        PuzzlePiece p4 = new PuzzlePiece(4,1,0,0,-1);
        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);
        puzzlePieces.add(p4);

        assertTrue(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }

    @Test
    public void ValidateMissingTLCorner(){
        ppv = new PuzzlePieceValidators(true);
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,1,1,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,-1,-1,1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,0,0,0);
        PuzzlePiece p4 = new PuzzlePiece(4,1,0,0,-1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }


    @Test
    public void ValidateMissingTRCorner(){
        ppv = new PuzzlePieceValidators(true);
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,0,1,1);
        PuzzlePiece p2 = new PuzzlePiece(2,-1,-1,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,1,1,0,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,0);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }

    @Test
    public void ValidateMissingBRCorner(){
        ppv = new PuzzlePieceValidators(true);
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,0,0,1);
        PuzzlePiece p2 = new PuzzlePiece(2,-1,0,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,1,0,1,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,0);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }

    @Test
    public void ValidateMissingBLCorner(){
        ppv = new PuzzlePieceValidators(true);
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,1,0,0,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,0,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,0,1,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }

    @Test
    public void ValidateEdgesNotZero(){
        ppv = new PuzzlePieceValidators(true);
        puzzlePieces = new ArrayList<>();
        PuzzlePiece p1 = new PuzzlePiece(1,0,1,0,0);
        PuzzlePiece p2 = new PuzzlePiece(2,0,0,0,-1);
        PuzzlePiece p3 = new PuzzlePiece(3,-1,0,1,0);
        PuzzlePiece p4 = new PuzzlePiece(4,0,0,-1,1);

        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }

    @Test
    public void ValidateWorngStraightEdges(){
        ppv = new PuzzlePieceValidators(true);
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



        assertFalse(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }

    @Test
    public void ValidateAllErrorsCombination(){
        ppv = new PuzzlePieceValidators(true);
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



        assertFalse(ppv.validatePuzzle(puzzlePieces, new ErrorsManager()));
    }



}