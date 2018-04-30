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






}
