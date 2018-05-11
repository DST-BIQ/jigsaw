package com.att.biq.dst.jigsaw.puzzle;

import java.util.List;

/**
 * @ author Stav
 * This class makes the validations on PuzzlePieces list, if validation fails, we won't continue to the puzzle solution stage.
 */
public class PuzzlePieceValidators {

    int topStraightSum =0;
    int totalStraightEdges =0;
    int leftStraightSum =0;
    int rightStraightSum =0;
    int totalZero=0;
    int bottomStraightSum =0;
    int totalCorners=0;
    int topLeftCorners;
    int topRightCorners;
    int bottomLeftCorners;
    int bottomRightCorners;
    boolean rotate;

    public PuzzlePieceValidators(boolean rotate) {
        this.rotate = rotate;
    }

    public int getTopStraightSum() {
        return topStraightSum;
    }

    public int getTotalStraightEdges() {
        return totalStraightEdges;
    }

    public int getLeftStraightSum() {
        return leftStraightSum;
    }

    public int getRightStraightSum() {
        return rightStraightSum;
    }

    public int getTotalZero() {
        return totalZero;
    }

    public int getBottomStraightSum() {
        return bottomStraightSum;
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


            if (piece.getLeft() == 0){
                totalStraightEdges++;
                leftStraightSum++;
                if (piece.getTop()==0){
                    topLeftCorners++;
                }
                if(piece.getBottom()==0){
                    bottomLeftCorners++;
                }
            }
            if (piece.getBottom()==0) {
                totalStraightEdges++;
                bottomStraightSum++;

            }
            if (piece.getRight()==0){
                totalStraightEdges++;
                rightStraightSum++;
                if (piece.getTop()==0){
                    topRightCorners++;
                }
                if(piece.getBottom()==0){
                    bottomRightCorners++;
                }
            }
            if (piece.getTop()==0) {
                totalStraightEdges++;
                topStraightSum++;
            }
            if ((piece.getTop()==0 || piece.getBottom()==0)&& (piece.getRight()==0 || piece.getLeft()==0)) {
                totalCorners++;
            }

            totalZero += piece.getLeft() + piece.getTop() + piece.getRight() + piece.getBottom();
        }

        boolean straightEdgesValidationResult = validateStraightEdges(totalStraightEdges, puzzlePieces.size());
        if (!straightEdgesValidationResult){
            errorsManager.addFatalErrorsList("Cannot solve puzzle: wrong number of straight edges");
        }


        boolean isTotalEdgesZero = (totalZero == 0);
        if (!isTotalEdgesZero){
            errorsManager.addFatalErrorsList("Cannot solve puzzle: sum of edges is not zero");
        }
        boolean cornersValidationResult = validateCorners(totalCorners);
        if (!cornersValidationResult){
            errorsManager.addFatalErrorsList("Cannot solve puzzle missing corners");
        }



        return (cornersValidationResult && isTotalEdgesZero && straightEdgesValidationResult);
    }

    /**
     * validate that there is at least 4 corners
     * @param totalCorners
     * @return
     */
    private  boolean validateCorners(int totalCorners) {
        if (rotate) {

            return (topLeftCorners+topRightCorners+ bottomRightCorners +bottomLeftCorners >=4);
        }else{
               return (topLeftCorners>=1 && topRightCorners>=1 && bottomRightCorners>=1 && bottomLeftCorners>=1);
        }
    }

    /** Validate that the minimum of left and right edges multile the minumum of top
     *  and bottom is equal or bigger to puzzle length
     * @param totalEdges
     * @param puzzeleLength
     * @return true if valid
     */
    private  boolean validateStraightEdges(int totalEdges,  int puzzeleLength) {
       if(rotate) {
           if (puzzeleLength > 1) {
               int value = (((int) (Math.sqrt(puzzeleLength)) / 1) * 4 + 2);
               return (value <= totalEdges);
           } else {
               return (totalEdges == 4);
           }
       }else{
           return (Math.min(topStraightSum, bottomStraightSum)*Math.min(leftStraightSum, rightStraightSum)>=puzzeleLength);
       }
    }

}