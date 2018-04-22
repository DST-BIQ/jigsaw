package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.PuzzleUtils.FileInputParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PuzzleTest {


    PuzzlePieceValidators puzzlePieceValidators = new PuzzlePieceValidators();
    Puzzle puzzle = new Puzzle();




//
//    public FileInputParser(ArrayList<Integer> piecesID,ArrayList<int[]> puzzlePieceList){
//
//        this.piecesID  = new ArrayList<>();
//        this.puzzlePieceList = new ArrayList<>();
//
//    }
    @Test
    public void getPuzzlePositiveTest(){
        ArrayList<Integer> piecesID = new ArrayList<>();
        piecesID.add(1);
        piecesID.add(2);
        piecesID.add(3);
        piecesID.add(4);

        ArrayList<int[]> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new int[]{0,0,0,0});
        puzzlePieceList.add(new int[]{0,0,0,0});
        puzzlePieceList.add(new int[]{0,0,0,0});
        puzzlePieceList.add(new int[]{0,0,0,0});
        FileInputParser fileInputParser = new FileInputParser(piecesID,puzzlePieceList);



        ArrayList<PuzzlePiece> expected = new ArrayList<>();
        List<String> puzzleContent = new ArrayList<>();
        puzzleContent.add("NumElements = 4");
        puzzleContent.add("1  0  0  0  0");
        puzzleContent.add("2  0  0  0  0");
        puzzleContent.add("3  0  0  0  0");
        puzzleContent.add("4  0  0  0  0");

        expected.add(new PuzzlePiece(1,0,0,0,0));
        expected.add(new PuzzlePiece(2,0,0,0,0));
        expected.add(new PuzzlePiece(3,0,0,0,0));
        expected.add(new PuzzlePiece(4,0,0,0,0));

        ArrayList<PuzzlePiece> receivedPuzzle = puzzle.getPuzzle(fileInputParser,puzzleContent, puzzlePieceValidators);
        assertEquals(expected, receivedPuzzle);
    }


    @Test
    public void getPuzzleWithMissingIdTest() {

        ArrayList<Integer> piecesID = new ArrayList<>();
        piecesID.add(1);
        piecesID.add(1);
        piecesID.add(3);
        piecesID.add(4);



        ArrayList<int[]> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new int[]{0,0,0,0});
        puzzlePieceList.add(new int[]{0,0,0,0});
        puzzlePieceList.add(new int[]{0,0,0,0});
        puzzlePieceList.add(new int[]{0,0,0,0});
        FileInputParser fileInputParser = new FileInputParser(piecesID,puzzlePieceList);


        List<String> puzzleContent = new ArrayList<>();
        puzzleContent.add("NumElements = 4");
        puzzleContent.add("1  0  0  0  0");
        puzzleContent.add("1  0  0  0  0");
        puzzleContent.add("3  0  0  0  0");
        puzzleContent.add("4  0  0  0  0");
        assertNull(puzzle.getPuzzle(fileInputParser,puzzleContent,puzzlePieceValidators));

    }






}
