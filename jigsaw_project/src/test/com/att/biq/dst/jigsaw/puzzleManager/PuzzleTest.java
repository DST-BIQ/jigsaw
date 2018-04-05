package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PuzzleTest {

    FileManager fm = new FileManager("src/resources/output/report_"+ System.currentTimeMillis());

    @Test
    public void getPuzzlePositiveTest(){
        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[]{1,0,0,0,0});
        expected.add(new int[]{2,0,0,0,0});
        expected.add(new int[]{3,0,0,0,0});
        expected.add(new int[]{4,0,0,0,0});
        ArrayList<int[]> receivedPuzzle = Puzzle.getPuzzle("src/resources/input/getPuzzlePositiveTest.txt", fm);
        asserEqualsOfPuzzle(expected, receivedPuzzle);
    }


    @Test
    public void getPuzzleWithMissingIdTest() {

        Throwable exception = assertThrows(RuntimeException.class,
                () -> {
                    Puzzle.getPuzzle("src/resources/input/getPuzzleMissingIdTest.txt", fm);
                }
        );

        assertTrue(exception.getMessage().contains("invalid"));
    }


    /**
     * Applies assertEquals on each element of the expected puzzle ArrayList<int[]></int[]> and the received one
     * @param expected
     * @param receivedPuzzle
     */
    public static void asserEqualsOfPuzzle(ArrayList<int[]> expected, ArrayList<int[]> receivedPuzzle) {
        for (int i=0;i<expected.size();i++){
            int[] currentExpectedPiece = expected.get(i);
            for(int j = 0; j< currentExpectedPiece.length; j++){
                assertEquals(currentExpectedPiece[j],receivedPuzzle.get(i)[j]);
            }
        }
    }

}
