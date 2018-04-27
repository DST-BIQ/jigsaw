package com.att.biq.dst.jigsaw.PuzzleIndexing;

import com.att.biq.dst.jigsaw.puzzle.PuzzlePiece;

/**
 * This class represents the rotated piece - holds the reference to the piece + rotation angle needed
 *
 */
public class RotatedPiece {

    PuzzlePiece puzzlePiece;
    int rotationAngle;


    public RotatedPiece(PuzzlePiece puzzlePiece, int rotationAngle) {
        this.puzzlePiece = puzzlePiece;
        this.rotationAngle = rotationAngle;
    }



    public int getRotationAngle(){
        return  rotationAngle;
    }

    public PuzzlePiece getRotatedPuzzlePiece(){
        return puzzlePiece;
    }



public int[] getEdgesValue(){

        int[] edgesArray = new int[4];
        edgesArray[0]=puzzlePiece.getLeft();
        edgesArray[1]=puzzlePiece.getTop();
        edgesArray[2]=puzzlePiece.getRight();
        edgesArray[3]=puzzlePiece.getBottom();

        return edgesArray;
}


}
