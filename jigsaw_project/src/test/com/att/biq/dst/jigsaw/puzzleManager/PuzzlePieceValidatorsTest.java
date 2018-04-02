package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class PuzzlePieceValidatorsTest {
    List<int[]> puzzlePieces;
    PuzzlePieceValidators ppv;
    FileManager fm = new FileManager();



    @Test
    public void ValidateAllTest(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        int [] p1 = new int[]{0,1,1,0};
        int [] p2 = new int[]{0,0,-1,1};
        int [] p3 = new int[]{-1,-1,0,0};
        int [] p4 = new int[]{1,0,0,-1};
        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertTrue(ppv.validatePuzzle(puzzlePieces));
    }

    @Test
    public void ValidateTLMissinglTest(){
        ppv = new PuzzlePieceValidators();
        puzzlePieces = new ArrayList<>();
        int [] p1 = new int[]{0,1,1,0};
        int [] p2 = new int[]{1,0,-1,1};
        int [] p3 = new int[]{0,-1,0,0};
        int [] p4 = new int[]{-1,0,0,-1};
        puzzlePieces.add(p1);
        puzzlePieces.add(p2);
        puzzlePieces.add(p3);
        puzzlePieces.add(p4);


        assertFalse(ppv.validatePuzzle(puzzlePieces));


    }



}