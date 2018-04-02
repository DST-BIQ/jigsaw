package com.att.biq.dst.jigsaw.puzzleManager;

public class PuzzleSolution {


    private PuzzlePiece[][] solutionPieces;

    public PuzzleSolution(int rows, int lines){
        solutionPieces = new PuzzlePiece[rows][lines];
    }


    public PuzzlePiece[][] getSolutionPieces() {
        return solutionPieces;
    }



}
