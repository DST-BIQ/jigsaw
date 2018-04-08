package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PuzzleTest {

    FileManager fm = new FileManager("src/resources/output/report_"+ System.currentTimeMillis());
    PuzzlePieceValidators puzzlePieceValidators = new PuzzlePieceValidators();

    @Test
    public void getPuzzlePositiveTest(){
        ArrayList<PuzzlePiece> expected = new ArrayList<>();

        expected.add(new PuzzlePiece(1,0,0,0,0));
        expected.add(new PuzzlePiece(2,0,0,0,0));
        expected.add(new PuzzlePiece(3,0,0,0,0));
        expected.add(new PuzzlePiece(4,0,0,0,0));

        ArrayList<PuzzlePiece> receivedPuzzle = Puzzle.getPuzzle("src/resources/input/getPuzzlePositiveTest.txt", fm, puzzlePieceValidators);
        assertEquals(expected, receivedPuzzle);
    }


    @Test
    public void getPuzzleWithMissingIdTest() {

        Throwable exception = assertThrows(RuntimeException.class,
                () -> {
                    Puzzle.getPuzzle("src/resources/input/getPuzzleMissingIdTest.txt", fm,puzzlePieceValidators);
                }
        );

        assertTrue(exception.getMessage().contains("invalid"));
    }




}
