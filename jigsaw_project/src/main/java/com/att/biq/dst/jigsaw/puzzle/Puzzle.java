package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.puzzleUtils.ErrorsManager;
import com.att.biq.dst.jigsaw.puzzleUtils.FileInputParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO implement pieceShape in all solution areas

/**
 * The puzzle class calculates the puzzle solution.
 */

public class Puzzle {


    private ErrorsManager errorsManager = new ErrorsManager();
    private List<PuzzlePiece> puzzlePieces;

    private Map<PieceShape, ArrayList<PuzzlePiece>> treeMap = new HashMap();


    /**
     * calculates puzzle solution
     *
     *
     * @param puzzleStructures - available structures of puzzle
     * @return possible puzzle solution if found. else returns null.
     */
    PuzzleSolution calculatePuzzleSolution(List<int[]> puzzleStructures) {

        for ( int[] structure : puzzleStructures ) {
            PuzzleSolution solution = new PuzzleSolution(structure[0], structure[1]);
            PuzzleSolution possibleSolution = solve(solution);
            if (possibleSolution != null && possibleSolution.isValid()) {
                return possibleSolution;
            }
        }
        return null;
    }

    /**
     * creates available list of puzzlePieces
     *
     * @param puzzleContent         - list of strings, represent the valid file input lines
     * @param puzzlePieceValidators - object that it's purpose to validate the puzzle piece
     * @return Array list of PuzzlePieces
     */
    public ArrayList<PuzzlePiece> getPuzzle(FileInputParser fim, List<String> puzzleContent, PuzzlePieceValidators puzzlePieceValidators) {

        ArrayList<int[]> puzzleArray = fim.produceArrayForPuzzle(puzzleContent, errorsManager);
        if (puzzleArray == null) {
            return null;
        }

        ArrayList<PuzzlePiece> puzzle = convertPuzzleArray(puzzleArray);

        if (puzzle == null || !puzzlePieceValidators.validatePuzzle(puzzle, errorsManager)) {

            return null;

        }
        puzzlePieces = puzzle;
        return puzzle;
    }

    /**
     * checks is the puzzle solution created is valid
     *
     * @param puzzle   - tested puzzle
     * @param solution - tested solution
     * @return valid/not valid
     */
    public static boolean checkPuzzleSolution(Puzzle puzzle, PuzzleSolution solution) {
        List<PuzzlePiece> pieces = puzzle.getPuzzlePieces();
        if (verifySolutionSize(pieces, solution)) return false;
        for ( PuzzlePiece puzzlePiece : pieces ) {
            if (!solution.contains(puzzlePiece)) {
                return false;
            }
        }
        if (!solution.isValid()) {
            return false;
        }

        return true;
    }

    /**
     * Verify if puzzle size equal to solution size
     * checks is the puzzle solution created is valid
     *
     * @param puzzle   - tested puzzle
     * @param solution - tested solution
     * @return true/false
     */
    private static boolean verifySolutionSize(List<PuzzlePiece> puzzle, PuzzleSolution solution) {
        if (puzzle.size() == (solution.getSize())) {
            return true;
        }
        return false;
    }

    /**
     * gets available structures for a possible solution
     *
     * @param puzzlePieceValidator - validators object
     * @param puzzleSize           - number of pieces on puzzle
     * @return available solutions list (e.g: 1,6 ; 6,1 ; 2;3)
     */
    public List<int[]> calculateSolutionStructure(PuzzlePieceValidators puzzlePieceValidator, int puzzleSize) {
        List<int[]> structureOptions = new ArrayList<>();
        for ( int rows = 1; rows <= puzzleSize; rows++ ) {
            int columns;
            if (puzzleSize % rows == 0) {
                columns = puzzleSize / rows;
                if (columns <= puzzlePieceValidator.getMinTopBottom() && rows <= puzzlePieceValidator.getMinLeftRigh()) {
                    structureOptions.add(new int[]{rows, columns});
                }
            }

        }
        return structureOptions;
    }


    /**
     * get list of matched pieces from the puzzle array, by condition defined on piece location.
     * left/right/top/buttom - if need specific value put it (e.g. top = 1, if does not matter, put 2 as condition value);
     *
     * @param left   - left side condition
     * @param top    - top side condition
     * @param right  - right side condition
     * @param bottom - bottom side condition
     *               //     * @param puzzlePieceArray - list of all puzzle pieces.
     * @return list of matched puzzle pieces.
     */


    List<PuzzlePiece> getMatch(int left, int top, int right, int bottom) {
        List<PuzzlePiece> matchedPieces = new ArrayList<>();
        PieceShape ps = new PieceShape(left, top, right, bottom);
        for ( Map.Entry<PieceShape, ArrayList<PuzzlePiece>> treeEntry : treeMap.entrySet() ) {

            if (treeEntry.getKey().equals(ps)) {

                for ( PuzzlePiece puzzlePiece : treeEntry.getValue() ) { // rotate on this node and take the first that is not "INUSE"

                    if (puzzlePiece.isInUse()) {
                        continue;
                    }
                    else {matchedPieces.add(puzzlePiece);}
                }

            }


        }

        return matchedPieces;
    }

    /**
     * this method tried to find possible solution. if found returns is, else return null
     *
     * @param solution     - current solution
     *
     * @return possible solution if found.
     */
    private PuzzleSolution solve(PuzzleSolution solution) {

        if (foundSolution(solution)) {
            return solution;
        } else if (noMorePiecesAndNoValidSolution()) {
            return null;
        }
        List<PuzzlePiece> foundPieces = null;
        if (isOnFirstRow(solution)) {
            foundPieces = handleFirstRowSolution(solution, foundPieces);
        } else if (isOnLastRow(solution)) {
            foundPieces = handleBottomRowSolution(solution);
        } else {
            foundPieces = handleBetweenTopAndBottomRows(solution);
        }
        if (foundPieces != null) {
            PuzzleSolution possibleSolution = findSolution(solution,  foundPieces);
            if (possibleSolution != null) return possibleSolution;
        }
        return null;
    }

    /**
     * get available pieces for top and bottom pieces
     *
     * @param solution     - current solution
     *
     * @return list of matched pieces
     */
    private List<PuzzlePiece> handleBetweenTopAndBottomRows(PuzzleSolution solution) {
        List<PuzzlePiece> foundPieces;
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0 - solution.getAbovePiece().getBottom(), 2, 2);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 0, 2);
        } else {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 2, 2);
        }
        return foundPieces;
    }

    private boolean noMorePiecesAndNoValidSolution() {
        return isAllPuzzlePiecesInUse();
    }

    private boolean foundSolution(PuzzleSolution solution) {
        return  solution.isValid() && isAllPuzzlePiecesInUse();
    }


    private boolean isAllPuzzlePiecesInUse(){
        for (PuzzlePiece puzzlePiece: puzzlePieces){
            if (!puzzlePiece.isInUse()) {
                return false;
            }
        }
        return true ;
    }

    private boolean isOnLastRow(PuzzleSolution solution) {
        return solution.getCurRow() == solution.getRows() - 1;
    }

    /**
     * get available pieces for bottom row
     *
     * @param solution     - current solution
     *
     * @return list of matched pieces
     */
    private List<PuzzlePiece> handleBottomRowSolution(PuzzleSolution solution) {
        List<PuzzlePiece> foundPieces = null;
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0 - solution.getAbovePiece().getBottom(), 2, 0);
        } else if (isBetweenFirstAndLastColumns(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 2, 0);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0 - solution.getAbovePiece().getBottom(), 0, 0);
        }
        return foundPieces;
    }


    /**
     * get available pieces for first row
     *
     * @param solution     - current solution
     *
     * @return list of matched pieces
     */
    private List<PuzzlePiece> handleFirstRowSolution(PuzzleSolution solution, List<PuzzlePiece> foundPieces) {
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0, 2, 2);
        } else if (isBetweenFirstAndLastColumns(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0, 2, 2);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0, 0, 2);
        }
        return foundPieces;
    }


    private boolean isOnLastColumn(PuzzleSolution solution) {
        return solution.getCurCol() == solution.getColumns() - 1;
    }

    private boolean isBetweenFirstAndLastColumns(PuzzleSolution solution) {
        return solution.getCurCol() < solution.getColumns() - 1;
    }

    private boolean isOnFirstColumn(PuzzleSolution solution) {
        return solution.getCurCol() == 0;
    }

    private boolean isOnFirstRow(PuzzleSolution solution) {
        return solution.getCurRow() == 0;
    }

    /**
     * if found solution returns it
     *
     * @param solution
     *
     * @param foundPieces
     * @return possible solution found
     */
    private PuzzleSolution findSolution(PuzzleSolution solution, List<PuzzlePiece> foundPieces) {
        for ( PuzzlePiece piece : foundPieces ) {
            solution.insertPiece(piece);
            piece.setInUse(true);
            PuzzleSolution possibleSolution = solve(solution);
            if (possibleSolution != null && possibleSolution.isValid()) {
                return possibleSolution;
            }else {
                solution.removePiece();
                piece.setInUse(false);
            }
        }
        return null;
    }

//
//    /**
//     * for recursion needs - clone current solution
//     *
//     * @param curSolution  - current solution
//     * @param enteredPiece piece to add to solution
//     * @return new solution
//     */
//    private PuzzleSolution cloneSolution(PuzzleSolution curSolution, PuzzlePiece enteredPiece) {
//        PuzzleSolution newSolution = new PuzzleSolution(curSolution.getRows(), curSolution.getColumns());
//        newSolution.setSolution(curSolution.getSolution());
//        newSolution.setCurRow(curSolution.getCurRow());
//        newSolution.setCurCol(curSolution.getCurCol());
//        newSolution.insertPiece(enteredPiece);
//
//        return newSolution;
//    }
//
//    /**
//     * for recursion needs - clone puzzle pieces list
//     *
//     * @param puzzlePieces - current puzzle pieceslist
//     * @param removedPiece piece to dismiss from the list
//     * @return new list of puzzlePieces without the removed piece
//     */
//    private List<PuzzlePiece> clonePuzzlePiecesList(List<PuzzlePiece> puzzlePieces, PuzzlePiece removedPiece) {
//        List<PuzzlePiece> newPuzzlePiecesList = new ArrayList<>();
//        for ( PuzzlePiece piece : puzzlePieces ) {
//            if (!piece.equals(removedPiece)) {
//                newPuzzlePiecesList.add(piece);
//            }
//        }
//        return newPuzzlePiecesList;
//    }


    /**
     * convert puzzle array from list of integers to list of puzzlePieces
     *
     * @param puzzleArray - puzzle pieces as int
     * @return
     */
    public ArrayList<PuzzlePiece> convertPuzzleArray(List<int[]> puzzleArray) {
        ArrayList<PuzzlePiece> puzzlePiecesList = new ArrayList<>();
        for ( int[] puzzlePiece : puzzleArray ) {
            PuzzlePiece pp = new PuzzlePiece(puzzlePiece[0], puzzlePiece[1], puzzlePiece[2], puzzlePiece[3], puzzlePiece[4]);
            puzzlePiecesList.add(pp);
        }
        return puzzlePiecesList;
    }


    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }

    public List<PuzzlePiece> getPuzzlePieces() {
        return puzzlePieces;
    }


    /**
     * creates tree structure + related puzzle pieces that match each criteria
     *
     * @param puzzlePieces - list of current puzzle pieces
     */
    public void addNodesToTreeStructure(List<PuzzlePiece> puzzlePieces) {


        ArrayList<PuzzlePiece> puzzlePieceArray;


        for ( PuzzlePiece puzzlePiece : puzzlePieces ) {

            if (!treeMap.containsKey(puzzlePiece.getEdgesFromPiece())) {
                puzzlePieceArray = new ArrayList<>();
                puzzlePieceArray.add(puzzlePiece);
                treeMap.put(puzzlePiece.getEdgesFromPiece(), puzzlePieceArray);
            } else {

                treeMap.get(puzzlePiece.getEdgesFromPiece()).add(puzzlePiece);

            }
        }


    }


    public Map<PieceShape, ArrayList<PuzzlePiece>> getTreeMap() {
        return this.treeMap;
    }

}
