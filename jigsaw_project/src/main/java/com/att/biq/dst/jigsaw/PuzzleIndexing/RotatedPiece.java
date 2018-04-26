package com.att.biq.dst.jigsaw.PuzzleIndexing;

import com.att.biq.dst.jigsaw.puzzle.PuzzlePiece;

/**
 * This class represents the rotated piece - holds the refernce to the piece + rotation angle needed
 *
 */
public class RotatedPiece {

    PuzzlePiece puzzlePiece;
    int rotationAngle;

    public RotatedPiece(PuzzlePiece puzzlePiece, RotationAngle rotationAngle) {
        this.puzzlePiece = puzzlePiece;
        this.rotationAngle = rotationAngle.getValue();
    }



    public int getRotationAngle(){
        return  rotationAngle;
    }

    public PuzzlePiece getRotatedPuzzlePiece(){
        return puzzlePiece;
    }




}
