package com.att.biq.dst.jigsaw.puzzleManager;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PuzzleTest {


    PuzzlePieceValidators puzzlePieceValidators = new PuzzlePieceValidators();
    Puzzle puzzle = new Puzzle();

    @Test
    public void getPuzzlePositiveTest(){
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

        ArrayList<PuzzlePiece> receivedPuzzle = puzzle.getPuzzle(puzzleContent, puzzlePieceValidators);
        assertEquals(expected, receivedPuzzle);
    }


    @Test
    public void getPuzzleWithMissingIdTest() {
        List<String> puzzleContent = new ArrayList<>();
        puzzleContent.add("NumElements = 4");
        puzzleContent.add("1  0  0  0  0");
        puzzleContent.add("1  0  0  0  0");
        puzzleContent.add("3  0  0  0  0");
        puzzleContent.add("4  0  0  0  0");
        assertNull(puzzle.getPuzzle(puzzleContent,puzzlePieceValidators));

    }






}
