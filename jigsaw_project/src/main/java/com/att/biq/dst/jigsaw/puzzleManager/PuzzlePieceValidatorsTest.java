package com.att.biq.dst.jigsaw.puzzleManager;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;



    public class PuzzlePieceValidatorsTest {
    int[][] puzzlePieces;



        @Test
        public void ValidateAllCornerWhileBlMissing(){
            puzzlePieces = new int[][]{{0,0,1,-1},{1,0,0,1},{0,1,1,0},{-1,1,0,0}};
            assertTrue(PuzzlePieceValidators.validateCorners(puzzlePieces));
        }



    }