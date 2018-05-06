package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.PuzzleUtils.ErrorsManager;

import java.util.List;

public class PuzzlePieceValidators {


    private int minTopButtom;
    private int minLeftRigh;

    /**Validate that all PuzzlePiece list are valid and can have solution
     *validatePuzzle - return an answer if the puzzle pieces are valid
     * @param puzzlePieces - array list of the puzzle pieces
     * The puzzlePieces list to valid
     * @param errorsManager
     *
     *
     * @return
     * return true if all validations are valid
     */
    public  boolean validatePuzzle(List<PuzzlePiece> puzzlePieces, ErrorsManager errorsManager) {


      int topEdges=0;
      int topSum=0;
      int totalEdges=0;
      int leftSum=0;
      int rightEdges=0;
      int rightSum=0;
      int bottomEdges=0;
      int bottomSum=0;
      int totalCorners=0;
      for ( PuzzlePiece piece : puzzlePieces ) {

          if (piece.getRotation() == 0) {

              if (piece.getLeft() == 0 || piece.getBottom()==0 || piece.getRight()==0 || piece.getTop()==0) {
                  totalEdges++;
              }
              if ((piece.getTop()==0 || piece.getBottom()==0)&& (piece.getRight()==0 || piece.getLeft()==0)) {
                  totalCorners++;
              }

              leftSum += piece.getLeft();
              topSum += piece.getTop();
              rightSum += piece.getRight();
              bottomSum += piece.getBottom();

          }
      }
      if (!validateStraightEdges(totalEdges,puzzlePieces.size())){
         errorsManager.addFatalErrorsList("Cannot solve puzzle: wrong number of straight edges");
      }

      if (!validateZero(rightSum,topSum,leftSum,bottomSum)){
          errorsManager.addFatalErrorsList("Cannot solve puzzle: sum of edges is not zero");
      }
        if (!validateCorners(totalCorners)){
            errorsManager.addFatalErrorsList("Cannot solve puzzle missing corners");
        }



      return (validateCorners(totalCorners)&&validateZero(rightSum,topSum,leftSum,bottomSum)&&validateStraightEdges(totalEdges,puzzlePieces.size()));
   }

    /**
     * validate that there is at least 4 corners
     * @param totalCorners
     * @return
     */
   private  boolean validateCorners(int totalCorners) {

         return (totalCorners>=4);


   }

   private  boolean validateZero(int rightSum, int topSum , int leftSum, int bottomSum){

         return (rightSum+leftSum==0&&topSum+bottomSum==0);

   }

    /** Validate that the minimum of left and right edges multile the minumum of top
     *  and bottom is equal or bigger to puzzle length
     * @param totalEdges
     * @param puzzeleLength
     * @return true if valid
     */
   private  boolean validateStraightEdges(int totalEdges,  int puzzeleLength) {

        int value = (int) (Math.sqrt(puzzeleLength)*4 +2);
        return (value<=totalEdges);

   }


    public int getMinTopBottom() {
        return minTopButtom;
    }

    public int getMinLeftRigh() {
        return minLeftRigh;
    }
}
