package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.puzzleUtils.ErrorsManager;

import java.util.List;

public class PuzzlePieceValidators {

    int topSum=0;
    int totalEdges=0;
    int leftSum=0;
    int rightSum=0;
    int totalZero=0;
    int bottomSum=0;
    int totalCorners=0;

    public int getTopSum() {
        return topSum;
    }

    public int getTotalEdges() {
        return totalEdges;
    }

    public int getLeftSum() {
        return leftSum;
    }

    public int getRightSum() {
        return rightSum;
    }

    public int getTotalZero() {
        return totalZero;
    }

    public int getBottomSum() {
        return bottomSum;
    }

    public int getTotalCorners() {
        return totalCorners;
    }

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



        for ( PuzzlePiece piece : puzzlePieces ) {


            if (piece.getLeft() == 0){totalEdges++;}
            if (piece.getBottom()==0) {totalEdges++;}
            if (piece.getRight()==0){totalEdges++;}
            if (piece.getTop()==0) {totalEdges++;}
            if ((piece.getTop()==0 || piece.getBottom()==0)&& (piece.getRight()==0 || piece.getLeft()==0)) {
                totalCorners++;
            }

            totalZero += piece.getLeft() + piece.getTop() + piece.getRight() + piece.getBottom();
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
        if (puzzeleLength>1) {
            int value =  (((int)(Math.sqrt(puzzeleLength)) / 1) * 4 + 2);
            return (value <= totalEdges);
        }else{
            return(totalEdges==4);
        }
    }

}