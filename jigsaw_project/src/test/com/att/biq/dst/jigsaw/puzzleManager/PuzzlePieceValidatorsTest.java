package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


public class PuzzlePieceValidatorsTest {
    List<PuzzlePiece> puzzlePieces;
    PuzzlePieceValidators ppv;
    FileManager fm = new FileManager("src/resources/output/report_"+ System.currentTimeMillis());



    @Test
    public void ValidateAllTest(){
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

//    @Test
//    public void ValidateTLMissinglTest(){
//        ppv = new PuzzlePieceValidators();
//        puzzlePieces = new ArrayList<>();
//        int [] p1 = new int[]{0,1,1,0};
//        int [] p2 = new int[]{1,0,-1,1};
//        int [] p3 = new int[]{0,-1,0,0};
//        int [] p4 = new int[]{-1,0,0,-1};
//        puzzlePieces.add(p1);
//        puzzlePieces.add(p2);
//        puzzlePieces.add(p3);
//        puzzlePieces.add(p4);


     //   assertFalse(ppv.validatePuzzle(puzzlePieces, fm));


  //  }



}