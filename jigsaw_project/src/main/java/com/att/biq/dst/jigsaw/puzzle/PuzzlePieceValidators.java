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

      boolean TL=false;
      boolean TR=false;
      boolean BR=false;
      boolean BL=false;
      int top=0;
      int topSum=0;
      int left=0;
      int leftSum=0;
      int right=0;
      int rightSum=0;
      int bottom=0;
      int bottomSum=0;
      for ( PuzzlePiece piece : puzzlePieces ) {
          if (!TL && piece.isTopLeft()){
              TL=true;
          }
          if (!TR && piece.isTopRight()){
              TR=true;
          }
          if (!BL && piece.isBottomLeft()){
              BL=true;
          }
          if (!BR && piece.isBottomRight()){
              BR=true;
          }

         if (piece.getLeft() == 0){
            left++;
         }
         if (piece.getTop() == 0){
            top++;
         }
         if (piece.getRight() == 0){
            right++;
         }
         if (piece.getBottom() == 0){
            bottom++;
         }
         leftSum+=piece.getLeft();
         topSum+=piece.getTop();
         rightSum+=piece.getRight();
         bottomSum+=piece.getBottom();

      }
      if (!validateStraightEdges(top,left,right,bottom,puzzlePieces.size())){
         errorsManager.addFatalErrorsList("Cannot solve puzzle: wrong number of straight edges");
      }
      if (!TL){
         errorsManager.addFatalErrorsList("Cannot solve puzzle: missing corner element: TL");
      }
      if (!TR){
          errorsManager.addFatalErrorsList("Cannot solve puzzle: missing corner element: TR");
      }
      if (!BL){
          errorsManager.addFatalErrorsList("Cannot solve puzzle: missing corner element: BL");
      }
      if (!BR){
          errorsManager.addFatalErrorsList("Cannot solve puzzle: missing corner element: BR");
      }

      if (!validateZero(rightSum,topSum,leftSum,bottomSum)){
          errorsManager.addFatalErrorsList("Cannot solve puzzle: sum of edges is not zero");
      }



      return (validateCorners(TL,TR,BR,BL)&&validateZero(rightSum,topSum,leftSum,bottomSum)&&validateStraightEdges(top,left,right,bottom,puzzlePieces.size()));
   }

   private  boolean validateCorners(boolean TL, boolean TR, boolean BR, boolean BL) {

         return (TL &&TR && BL && BR);


   }

   private  boolean validateZero(int rightSum, int topSum , int leftSum, int bottomSum){

         return (rightSum+leftSum==0&&topSum+bottomSum==0);

   }

    /** Validate that the minimum of left and right edges multile the minumum of top
     *  and bottom is equal or bigger to puzzle length
     * @param top
     * @param left
     * @param right
     * @param bottom
     * @param puzzeleLength
     * @return true if valid
     */
   private  boolean validateStraightEdges(int top, int left, int right, int bottom , int puzzeleLength) {
        minLeftRigh = Math.min(left,right);
        minTopButtom = Math.min(top,bottom);
        int min=minTopButtom*minLeftRigh;

        return (min>=puzzeleLength);

   }


    public int getMinTopBottom() {
        return minTopButtom;
    }

    public int getMinLeftRigh() {
        return minLeftRigh;
    }
}
