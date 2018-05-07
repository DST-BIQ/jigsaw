package com.att.biq.dst.jigsaw.puzzle;

public class PuzzlePieceIdentity {

    int puzzlePieceID;
    int rotation;

    public PuzzlePieceIdentity(int puzzlePieceID, int rotation){
        this.puzzlePieceID = puzzlePieceID;
        this.rotation=rotation;


    }



    public int getPuzzlePieceID(){
        return this.puzzlePieceID;
    }

    public int getRotation(){
        return rotation;
    }

}
