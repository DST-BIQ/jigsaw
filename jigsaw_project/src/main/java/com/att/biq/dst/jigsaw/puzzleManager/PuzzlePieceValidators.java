package com.att.biq.dst.jigsaw.puzzleManager;

public class PuzzlePieceValidators {
   public static boolean validateCorners(int[][] puzzlePieces) {

      boolean TL, TR, BL, BR;
      for ( int[] pieces : puzzlePieces ) {
         if (pieces[0] == 0 && pieces[1] == 0) {
            TL = true;
         }


      }

      return false;

   }
}
