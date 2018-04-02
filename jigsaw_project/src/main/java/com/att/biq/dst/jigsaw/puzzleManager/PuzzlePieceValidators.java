package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;

import java.util.List;

public class PuzzlePieceValidators {

 FileManager fm = new FileManager();



   public boolean validatePuzzle(List<int[]> puzzlePieces) {

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
      for ( int[] pieces : puzzlePieces ) {
          if ((pieces[0] == 0) && pieces[1] == 0){
              TL=true;
          }
          if ((pieces[1] == 0) && pieces[2] == 0){
              TR=true;
          }
          if ((pieces[0] == 0) && pieces[3] == 0){
              BL=true;
          }
          if ((pieces[2] == 0) && pieces[3] == 0){
              BR=true;
          }

         if (pieces[0] == 0){
            left++;
         }
         if (pieces[1] == 0){
            top++;
         }
         if (pieces[2] == 0){
            right++;
         }
         if (pieces[3] == 0){
            bottom++;
         }
         leftSum+=pieces[0];
         topSum+=pieces[1];
         rightSum+=pieces[2];
         bottomSum+=pieces[3];

      }
      if (!validateStraightEdges(top,left,right,bottom,puzzlePieces.size())){
         fm.reportError("Cannot solve puzzle: wrong number of straight edges");
      }
      if (!TL){
         fm.reportError("Cannot solve puzzle: missing corner element: TL");
      }
      if (!TR){
         fm.reportError("Cannot solve puzzle: missing corner element: TR");
      }
      if (!BL){
         fm.reportError("Cannot solve puzzle: missing corner element: BL");
      }
      if (!BR){
         fm.reportError("Cannot solve puzzle: missing corner element: BR");
      }

      if (!validateZero(rightSum,topSum,leftSum,bottomSum)){
         fm.reportError("Cannot solve puzzle: sum of edges is not zero");
      }



      return (validateCorners(TL,TR,BR,BL)&&validateZero(rightSum,topSum,leftSum,bottomSum)&&validateStraightEdges(top,left,right,bottom,puzzlePieces.size()));
   }

   private boolean validateCorners(boolean TL, boolean TR, boolean BR, boolean BL) {
      if (TL &&TR && BL && BR){
         return true;
      }else {

         return false;
      }
   }

   private boolean validateZero(int rightSum, int topSum , int leftSum, int bottomSum){
      if (rightSum==0&&topSum==0&&leftSum==0&&bottomSum==0) {
         return true;
      }else {
         return false;
      }
   }

   private boolean validateStraightEdges(int top, int left, int right, int bottom , int puzzeleLength) {
     int min=Math.min(top,bottom)*Math.min(right,left);
     if (min>=puzzeleLength) {
        return true;
     }else {
        return false;
     }

   }

   private boolean bottomRight(int right, int bottom) {
      return bottom>0&&right>0;
   }

   private boolean bottomLeft(int left, int botton) {
      return botton>0&&left>0;
   }

   private boolean topRight(int top, int right) {
      return top>0&&right>0;
   }

   private boolean topLeft(int top, int left) {
      return top>0&&left>0;
   }
}
