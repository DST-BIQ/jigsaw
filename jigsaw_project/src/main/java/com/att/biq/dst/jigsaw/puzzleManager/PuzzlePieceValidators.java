package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;

import java.util.List;

public class PuzzlePieceValidators {


    private int minTopButtom;
    private int minLeftRigh;


    public  boolean validatePuzzle(List<PuzzlePiece> puzzlePieces, FileManager fm) {

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
          if (!BR && piece.isBottomright()){
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
         fm.writeToFile("Cannot solve puzzle: wrong number of straight edges");
      }
      if (!TL){
         fm. writeToFile("Cannot solve puzzle: missing corner element: TL");
      }
      if (!TR){
         fm.writeToFile("Cannot solve puzzle: missing corner element: TR");
      }
      if (!BL){
         fm.writeToFile("Cannot solve puzzle: missing corner element: BL");
      }
      if (!BR){
         fm.writeToFile("Cannot solve puzzle: missing corner element: BR");
      }

      if (!validateZero(rightSum,topSum,leftSum,bottomSum)){
         fm.writeToFile("Cannot solve puzzle: sum of edges is not zero");
      }



      return (validateCorners(TL,TR,BR,BL)&&validateZero(rightSum,topSum,leftSum,bottomSum)&&validateStraightEdges(top,left,right,bottom,puzzlePieces.size()));
   }

   private  boolean validateCorners(boolean TL, boolean TR, boolean BR, boolean BL) {

         return (TL &&TR && BL && BR);


   }

   private  boolean validateZero(int rightSum, int topSum , int leftSum, int bottomSum){

         return (rightSum+leftSum==0&&topSum+bottomSum==0);

   }

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
