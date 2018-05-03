package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.PuzzleUtils.ErrorsManager;
import com.att.biq.dst.jigsaw.PuzzleUtils.FileInputParser;

import java.util.ArrayList;
import java.util.List;


public class Puzzle {
    private ErrorsManager errorsManager;
    private List<PuzzlePiece> puzzlePieces;
    private boolean rotate=true;


     *
     * @param puzzlePieces     - list of available puzzle pieces

        for (int[] structure : puzzleStructures) {
            PuzzleSolution solution = new PuzzleSolution(structure[0], structure[1]);
            if (possibleSolution != null && possibleSolution.isValid()) {
                return possibleSolution;
            }
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
     *
     * @param puzzle   - tested puzzle
    public static boolean checkPuzzleSolution(Puzzle puzzle, PuzzleSolution solution) {
        for (PuzzlePiece puzzlePiece : pieces) {
            if (!solution.contains(puzzlePiece)) {
        if (!solution.isValid()) {
            return false;
        }
     *
     * @param puzzle   - tested puzzle
    private static boolean verifySolutionSize(List<PuzzlePiece> puzzle, PuzzleSolution solution) {
        if (puzzle.size() == (solution.getSize())) {
     *
     * @param puzzleSize           - number of pieces on puzzle
    public List<int[]> calculateSolutionStructure(PuzzlePieceValidators puzzlePieceValidator, int puzzleSize) {
        for (int rows = 1; rows <= puzzleSize; rows++) {
            if (puzzleSize % rows == 0) {
                columns = puzzleSize / rows;
                if (columns + rows <= getStraightEdgesSum()) {
                    structureOptions.add(new int[]{rows, columns});
     * get list of matched pieces from teh puzzle array, by condition defined on piece location.
     * left/right/top/buttom - if need specific value put it (e.g. top = 1, if does not matter, put 2 as condition value);
     *
     * @param left             - left side condition
     * @param top              - top side condition
     * @param right            - right side condition
     * @param bottom           - bottom side condition
    private List<PuzzlePiece> getMatch(int left, int top, int right, int bottom, List<PuzzlePiece> puzzlePieceArray) {
        for (PuzzlePiece piece : puzzlePieceArray) {
            if (left != 2 && left != piece.getLeft()) {
                isValidPiece = false;
            }
            if (top != 2 && top != piece.getTop()) {
                isValidPiece = false;
            }
            if (right != 2 && right != piece.getRight()) {
                isValidPiece = false;
            }
            if (bottom != 2 && bottom != piece.getBottom()) {
                isValidPiece = false;
            }
            if (isValidPiece) {
                matchedPieces.add(piece);
            }
        }
     * this method tried to find possible solution. if found returns is, else return null
     *
     * @param solution     - current solution
    public PuzzleSolution solve(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces) {
        if (foundSolution(solution, puzzlePieces)) {
            return solution;
        } else if (noMorePiecesAndNoValidSolution(puzzlePieces)) {
            return null;
        }
        List<PuzzlePiece> foundPieces = null;
        if (isOnFirstRow(solution)) {
            foundPieces = handleFirstRowSolution(solution, puzzlePieces, foundPieces);
        } else if (isOnLastRow(solution)) {
            foundPieces = handleBottomRowSolution(solution, puzzlePieces);
        } else {
            foundPieces = handleBetweenTopAndBottomRows(solution, puzzlePieces);
        }
        if (foundPieces != null) {
            PuzzleSolution possibleSolution = findSolution(solution, puzzlePieces, foundPieces);
            if (possibleSolution != null) return possibleSolution;
        }
        return null;
     *
     * @param solution     - current solution
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0 - solution.getAbovePiece().getBottom(), 2, 2, puzzlePieces);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 0, 2, puzzlePieces);
        } else {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 2, 2, puzzlePieces);
        return puzzlePieces.size() == 0;
        return puzzlePieces.size() == 0 && solution.isValid();
        return solution.getCurRow() == solution.getRows() - 1;
     *
     * @param solution     - current solution
        List<PuzzlePiece> foundPieces = null;
            foundPieces = getMatch(0, 0 - solution.getAbovePiece().getBottom(), 2, 0, puzzlePieces);
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 2, 0, puzzlePieces);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 0, 0, puzzlePieces);
     *
     * @param solution     - current solution
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0, 2, 2, puzzlePieces);
        } else if (isOnLastColumn(solution)) {
        return solution.getCurCol() == solution.getColumns() - 1;
        return solution.getCurCol() < solution.getColumns() - 1;
     *
    private PuzzleSolution findSolution(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces, List<PuzzlePiece> foundPieces) {
     *
     * @param curSolution  - current solution
    private PuzzleSolution cloneSolution(PuzzleSolution curSolution, PuzzlePiece enteredPiece) {
        PuzzleSolution newSolution = new PuzzleSolution(curSolution.getRows(), curSolution.getColumns());
     *
    private List<PuzzlePiece> clonePuzzlePiecesList(List<PuzzlePiece> puzzlePieces, PuzzlePiece removedPiece) {
        for (PuzzlePiece piece : puzzlePieces) {
            if (!piece.equals(removedPiece)) {
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
        int total = 0;
        for (PuzzlePiece pp:puzzlePieces) {
            if (pp.getTop()==0 ){
                total++;
            }
            if (pp.getLeft()==0){
                total++;
            }
            if (pp.getRight()==0){
                total++;
            }
            if (pp.getBottom()==0){
                total++;
            }

        }
        return total;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }

}
