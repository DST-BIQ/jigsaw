package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.PuzzleUtils.ErrorsManager;
import com.att.biq.dst.jigsaw.PuzzleUtils.FileInputParser;

import java.util.ArrayList;
import java.util.List;


public class Puzzle {
    private ErrorsManager errorsManager;
    private List<PuzzlePiece> puzzlePieces;
    private boolean rotate=true;
    private boolean isSolved = false;

    public Puzzle(ErrorsManager errorsManager) {
        this.errorsManager = errorsManager;
    }

    /**
     * creates available list of puzzlePieces
     *
     * @param puzzleContent         - list of strings, represent the valid file input lines
     * @param puzzlePieceValidators - object that it's purpose to validate the puzzle piece
     * @return Array list of PuzzlePieces
     */
    public List<PuzzlePiece> getPuzzle(FileInputParser fim, List<String> puzzleContent, PuzzlePieceValidators puzzlePieceValidators) {

        ArrayList<int[]> puzzleArray = fim.produceArrayForPuzzle(puzzleContent, errorsManager);
        if (puzzleArray == null) {
            return null;
        }

        puzzlePieces = convertPuzzleArray(puzzleArray);


        if (puzzlePieces == null || !puzzlePieceValidators.validatePuzzle(puzzlePieces, errorsManager)) {

            return null;

        }

        return puzzlePieces;
    }

    public List<PuzzlePiece> getPuzzlePieces() {
        return puzzlePieces;

    }

    /**
     * convert puzzle array from list of integers to list of puzzlePieces
     *
     * @param puzzleArray - puzzle pieces as int
     * @return
     */
    public List<PuzzlePiece> convertPuzzleArray(List<int[]> puzzleArray) {
        puzzlePieces = new ArrayList<>();
        for (int[] puzzlePiece : puzzleArray) {
            PuzzlePiece pp = new PuzzlePiece(puzzlePiece[0], puzzlePiece[1], puzzlePiece[2], puzzlePiece[3], puzzlePiece[4]);
            puzzlePieces.add(pp);
            if (rotate) {
                rotatePiece(pp);
            }
        }
        return puzzlePieces;
    }

    private void rotatePiece(PuzzlePiece pp) {
        if ((pp.getBottom() == pp.getTop()) && (pp.getLeft() == pp.getRight()) && (pp.getRight() != pp.getTop())) {
            puzzlePieces.add(pp.rotate(1,90));
        } else if ((pp.getBottom() != pp.getTop()) || (pp.getLeft() != pp.getRight())) {
            puzzlePieces.add(pp.rotate(1,90));
            puzzlePieces.add(pp.rotate(2,180));
            puzzlePieces.add(pp.rotate(3,270));
        }
    }

    public int getStraightEdgesSum () {
        int totalStraightEdges = 0;
        for (PuzzlePiece pp:puzzlePieces) {
            if (pp.getTop()==0 || pp.getLeft()==0 || pp.getRight()==0 || pp.getBottom()==0){
                totalStraightEdges++;
            }
        }
        return totalStraightEdges;
    }

    public void setSolved() {
        isSolved = true;
    }

    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }

    public boolean isSolved() {
        return isSolved;
    }
}
