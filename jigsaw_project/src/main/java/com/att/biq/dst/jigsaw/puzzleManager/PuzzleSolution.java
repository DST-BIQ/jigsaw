package com.att.biq.dst.jigsaw.puzzleManager;

public class PuzzleSolution {


    private PuzzlePiece[][] solutionPieces;
    private int size;

    public PuzzleSolution(int rows, int columns){
        size = rows*columns;
        solutionPieces = new PuzzlePiece[rows][columns];
    }


    public PuzzlePiece[][] getSolutionPieces() {
        return solutionPieces;
    }


    public boolean contains(PuzzlePiece puzzlePiece) {

        for (int row=0;row<solutionPieces.length;row++){
            for (int column=0; column<=solutionPieces[row].length;column++){
                if (solutionPieces[row][column].equals(puzzlePiece)){
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }
}
