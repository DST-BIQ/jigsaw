package com.att.biq.dst.jigsaw.puzzle;

/**
 * @author  Dorit
 * This class represents the PuzzlePieceIdentity which include the puzzlePieceID , PieceShape and the rotation
 */
public class PuzzlePieceIdentity {

    int puzzlePieceID;
    PieceShape shape;
    int rotation;

    public PuzzlePieceIdentity(int puzzlePieceID, int rotation, PieceShape shape){
        this.puzzlePieceID = puzzlePieceID;
        this.rotation=rotation;
        this.shape = shape;
    }



    public int getPuzzlePieceID(){
        return this.puzzlePieceID;
    }

    public int getRotation(){
        return rotation;
    }

    public PieceShape getShape() {
        return shape;
    }

    public void setShape(PieceShape shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        if (rotation!=0){
            return puzzlePieceID +" ["+ rotation+"] ";
        }else {return puzzlePieceID+" ";}

    }
}
