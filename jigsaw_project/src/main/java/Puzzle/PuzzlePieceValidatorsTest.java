package Puzzle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;



    public class PuzzlePieceValidatorsTest {
    int[][] puzzlePieces;



        @Test
        public void ValidateAllCornerWhileBlMissing(){
            puzzlePieces = new int[][]{{0,0,1,-1},{1,0,0,1},{0,1,1,0},{-1,1,0,0}};
            assertTrue(PuzzlePieceValidators.validateCorners(puzzlePieces));
        }



    }