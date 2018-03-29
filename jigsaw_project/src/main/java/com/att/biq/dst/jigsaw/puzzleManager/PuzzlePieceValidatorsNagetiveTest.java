package com.att.biq.dst.jigsaw.puzzleManager;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;


//@RunWith(Parametrized.class)
    public class PuzzlePieceValidatorsNagetiveTest {
    int[][] puzzlePieces;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(
                    new Object[][] {
                            {new int[][]{{0,0,1,-1},{1,0,0,1},{0,1,1,0},{-1,1,0,0}}},
                            {new int[][]{{1,0,1,-1},{1,0,0,1},{0,1,1,0},{-1,1,0,0}}},
                            {new int[][]{{0,0,1,-1},{1,-1,0,1},{0,1,1,0},{-1,1,0,0}}},
                            {new int[][]{{0,0,1,-1},{1,0,0,1},{-1,1,1,0},{-1,1,0,-1}}},
                            {new int[][]{{1,0,1,-1},{1,-1,0,1},{0,1,1,0},{-1,1,0,0}}},
                            {new int[][]{{0,1,1,-1},{1,0,1,1},{0,1,1,-1},{-1,1,0,0}}},
                            {new int[][]{{0,-1,1,-1},{1,-1,0,1},{0,1,1,1},{-1,1,0,1}}},

                    }
            );
        }


        public PuzzlePieceValidatorsNagetiveTest(int[][] puzzlePieces){
           this.puzzlePieces=puzzlePieces;
        }

        @Test
        public void ValidateAllCornerWhileBlMissing(){

            assertTrue(PuzzlePieceValidators.validateCorners(puzzlePieces));
        }



    }