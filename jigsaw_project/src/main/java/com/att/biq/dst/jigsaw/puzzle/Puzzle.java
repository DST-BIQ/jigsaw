package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.PuzzleUtils.ErrorsManager;
import com.att.biq.dst.jigsaw.PuzzleUtils.FileInputParser;

import java.util.ArrayList;
import java.util.List;


public class Puzzle {
    private ErrorsManager errorsManager;
    private List<PuzzlePiece> puzzlePieces;
    private boolean isSolved = false;



    public Puzzle(ErrorsManager errorsManager){
        this.errorsManager = errorsManager;
    }
    /**
     * creates available list of puzzlePieces
     * @param puzzleContent - list of strings, represent the valid file input lines
     * @param puzzlePieceValidators - object that it's purpose to validate the puzzle piece
     * @return Array list of PuzzlePieces
     */
    public  ArrayList<PuzzlePiece> getPuzzle(FileInputParser fim,List<String> puzzleContent, PuzzlePieceValidators puzzlePieceValidators){

        ArrayList<int[]> puzzleArray = fim.produceArrayForPuzzle(puzzleContent, errorsManager);
        if (puzzleArray==null){ return null;}

        ArrayList<PuzzlePiece> puzzle = convertPuzzleArray(puzzleArray);

        if (puzzle==null || !puzzlePieceValidators.validatePuzzle(puzzle, errorsManager)){

            return null;

        }
        puzzlePieces = puzzle;
        return puzzle;
    }

    public List<PuzzlePiece> getPuzzlePieces() {
        return puzzlePieces;
    }
    /**
     * convert puzzle array from list of integers to list of puzzlePieces
     * @param puzzleArray - puzzle pieces as int
     * @return
     */
    public ArrayList<PuzzlePiece> convertPuzzleArray(List<int[]> puzzleArray) {
        ArrayList<PuzzlePiece> puzzlePiecesList = new ArrayList<>();
        for (int[] puzzlePiece : puzzleArray) {
            PuzzlePiece pp = new PuzzlePiece(puzzlePiece[0], puzzlePiece[1], puzzlePiece[2], puzzlePiece[3], puzzlePiece[4]);
            puzzlePiecesList.add(pp);
        }
        return puzzlePiecesList;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }
}
