package com.att.biq.dst.jigsaw.PuzzleUtils;

import com.att.biq.dst.jigsaw.puzzle.Puzzle;
import com.att.biq.dst.jigsaw.puzzle.PuzzlePiece;
import com.att.biq.dst.jigsaw.puzzle.PuzzlePieceValidators;
import com.att.biq.dst.jigsaw.puzzle.PuzzleSolution;

import java.util.ArrayList;
import java.util.List;

public class PuzzleSolver implements Runnable {

    Puzzle puzzle;
    PuzzleSolution solution;
    private ErrorsManager errorsManager = new ErrorsManager();


    public PuzzleSolver( Puzzle puzzle, PuzzleSolution solution){
        this.puzzle=puzzle;
        this.solution = solution;

    }

    @Override
    public void run() {
        solution = solve(solution,puzzle.getPuzzlePieces());
        if (solution!=null && solution.isValid()){
            setSolution(solution);
            puzzle.setSolved();
        }
    }

    /**
     * calculates puzzle solution
     * @
     * @param puzzleStructures - available structures of puzzle
     * @return possible puzzle solution if found. else returns null.
     */

    public static PuzzleSolution calculatePuzzleSolution(List<int[]> puzzleStructures, ThreadsManager threadsManager, Puzzle puzzle) throws InterruptedException {
        int counter=0;
        PuzzleSolver solver=null;
        List<Runnable> solvers = new ArrayList<>();
        for(int[] structure:puzzleStructures) {
            PuzzleSolution attemptSolution = new PuzzleSolution(structure[0], structure[1]);
            solver = new PuzzleSolver(puzzle, attemptSolution);
            solvers.add(solver);
            threadsManager.getThreadPoolExecutor().execute(solver);
        }
        while (!puzzle.isSolved() && counter<200){//TODO - find another exit criteria
            Thread.sleep(10);
            counter++;
        }
        if (puzzle.isSolved()){
            threadsManager.getThreadPoolExecutor().shutdown();
            if (solver!=null) {
                return solver.solution;
            }else return null;

        }else {

            return null;
        }
    }

    /**
     * checks is the puzzle solution created is valid
     * @param puzzle - tested puzzle
     * @param solution - tested solution
     * @return valid/not valid
     */

    public static boolean checkPuzzleSolution(Puzzle puzzle, PuzzleSolution solution ){
        List<PuzzlePiece> pieces = puzzle.getPuzzlePieces();
        if (verifySolutionSize(pieces, solution)) return false;
        for (PuzzlePiece puzzlePiece: pieces){
            if (!solution.contains(puzzlePiece)){
                return false;
            }
        }
        if (!solution.isValid()){ return false;}

        return true;
    }

    /**
     * gets available structures for a possible solution
     * @param puzzlePieceValidator - validators object
     *
     * @return available solutions list (e.g: 1,6 ; 6,1 ; 2;3)
     */

    public static List<int[]> calculateSolutionStructure(PuzzlePieceValidators puzzlePieceValidator, int puzzleSize){
        List<int[]> structureOptions = new ArrayList<>();
        for (int rows=1; rows<=puzzleSize;rows++){
            int columns;
            if (puzzleSize%rows==0){
                columns = puzzleSize/rows;
                if (columns<=puzzlePieceValidator.getMinTopBottom() && rows<=puzzlePieceValidator.getMinLeftRigh() ){
                    structureOptions.add(new int[]{rows,columns});
                }
            }

        }
        return structureOptions;
    }

    /**
     * Verify if puzzle size equal to solution size
     * checks is the puzzle solution created is valid
     * @param puzzle - tested puzzle
     * @param solution - tested solution
     * @return true/false
     */

    private static   boolean verifySolutionSize(List<PuzzlePiece> puzzle, PuzzleSolution solution) {
        if (puzzle.size()==(solution.getSize())){
            return true;
        }
        return false;
    }

    /**
     *  get list of matched pieces from teh puzzle array, by condition defined on piece location.
     *  left/right/top/buttom - if need specific value put it (e.g. top = 1, if does not matter, put 2 as condition value);
     * @param left - left side condition
     * @param top - top side condition
     * @param right - right side condition
     * @param bottom - bottom side condition
     * @param puzzlePieceArray - list of all puzzle pieces.
     * @return list of matched puzzle pieces.
     */

    private  List<PuzzlePiece> getMatch(int left, int top, int right, int bottom, List<PuzzlePiece> puzzlePieceArray ){
        List<PuzzlePiece> matchedPieces = new ArrayList<>();
        for (PuzzlePiece piece : puzzlePieceArray){
            boolean isValidPiece = true;
            if (left!=2&& left != piece.getLeft()){isValidPiece=false;}
            if (top!=2 && top!=piece.getTop()){isValidPiece=false;}
            if (right!=2 && right!=piece.getRight()){isValidPiece=false;}
            if (bottom!=2 && bottom!=piece.getBottom()){isValidPiece=false;}
            if (isValidPiece){matchedPieces.add(piece);}
        }
        return matchedPieces;
    }

    /**
     *  this method tried to find possible solution. if found returns is, else return null
     * @param solution - current solution
     * @param puzzlePieces - current array pieces
     * @return possible solution if found.
     */

    public PuzzleSolution solve(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces){
        if (foundSolution(solution, puzzlePieces)) {return solution;}
        if (puzzle.isSolved()){return null;}
        else if (noMorePiecesAndNoValidSolution(puzzlePieces)){return null;}
        List<PuzzlePiece> foundPieces =null;
        if (isOnFirstRow(solution)) {
            foundPieces = handleFirstRowSolution(solution, puzzlePieces, foundPieces);
        }else if (isOnLastRow(solution)){
            foundPieces = handleBottomRowSolution(solution, puzzlePieces);
        }else {
            foundPieces = handleBetweenTopAndBottomRows(solution, puzzlePieces);
        }
        if (foundPieces!=null) {
            PuzzleSolution possibleSolution = findSolution(solution, puzzlePieces, foundPieces);
            if (possibleSolution != null) return possibleSolution;
        }
        return null;
    }

    /**
     * get available pieces for top and bottom pieces
     * @param solution - current solution
     * @param puzzlePieces - current array pieces
     * @return list of matched pieces
     */

    private List<PuzzlePiece> handleBetweenTopAndBottomRows(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces) {
        List<PuzzlePiece> foundPieces;
        if (isOnFirstColumn(solution)){
            foundPieces = getMatch(0 , 0-solution.getAbovePiece().getBottom(), 2, 2, puzzlePieces);
        }else if (isOnLastColumn(solution)){
            foundPieces = getMatch(0-solution.getFormerPiece().getRight() , 0-solution.getAbovePiece().getBottom(), 0, 2, puzzlePieces);
        }else{
            foundPieces = getMatch(0-solution.getFormerPiece().getRight() , 0-solution.getAbovePiece().getBottom(), 2, 2, puzzlePieces);
        }
        return foundPieces;
    }

    private boolean noMorePiecesAndNoValidSolution(List<PuzzlePiece> puzzlePieces) {
        return puzzlePieces.size()==0;
    }

    private boolean foundSolution(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces) {
        return puzzlePieces.size()==0 && solution.isValid();
    }

    private boolean isOnLastRow(PuzzleSolution solution) {
        return solution.getCurRow() == solution.getRows()-1;
    }

    /**
     * get available pieces for bottom row
     * @param solution - current solution
     * @param puzzlePieces - current array pieces
     * @return list of matched pieces
     */

    private List<PuzzlePiece> handleBottomRowSolution(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces) {
        List<PuzzlePiece> foundPieces =null;
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0-solution.getAbovePiece().getBottom(), 2, 0, puzzlePieces);
        } else if (isBetweenFirstAndLastColumns(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0-solution.getAbovePiece().getBottom(), 2, 0, puzzlePieces);
        }else if (isOnLastColumn(solution)){
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0-solution.getAbovePiece().getBottom(), 0, 0, puzzlePieces);
        }
        return foundPieces;
    }

    /**
     * get available pieces for first row
     * @param solution - current solution
     * @param puzzlePieces - current array pieces
     * @return list of matched pieces
     */

    private List<PuzzlePiece> handleFirstRowSolution(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces, List<PuzzlePiece> foundPieces) {
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0, 2, 2, puzzlePieces);
        } else if (isBetweenFirstAndLastColumns(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0, 2, 2, puzzlePieces);
        }else if (isOnLastColumn(solution)){
            foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0, 0, 2, puzzlePieces);
        }
        return foundPieces;
    }

    private boolean isOnLastColumn(PuzzleSolution solution) {
        return solution.getCurCol() == solution.getColumns()-1;
    }

    private boolean isBetweenFirstAndLastColumns(PuzzleSolution solution) {
        return solution.getCurCol() < solution.getColumns()-1;
    }

    private boolean isOnFirstColumn(PuzzleSolution solution) {
        return solution.getCurCol() == 0;
    }

    private boolean isOnFirstRow(PuzzleSolution solution) {
        return solution.getCurRow() == 0;
    }

    /**
     * if found solution returns it
     * @param solution
     * @param puzzlePieces
     * @param foundPieces
     * @return possible solution found
     */

    private  PuzzleSolution findSolution(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces, List<PuzzlePiece> foundPieces) {
        for (PuzzlePiece piece : foundPieces) {
            PuzzleSolution possibleSolution = solve(cloneSolution(solution, piece), clonePuzzlePiecesList(puzzlePieces, piece));
            if (possibleSolution != null && possibleSolution.isValid()) {
                return possibleSolution;
            }
        }
        return null;
    }


    /**
     * for recursion needs - clone current solution
     * @param curSolution - current solution
     * @param enteredPiece piece to add to solution
     * @return new solution
     */

    private  PuzzleSolution cloneSolution(PuzzleSolution curSolution, PuzzlePiece enteredPiece){
        PuzzleSolution newSolution = new PuzzleSolution(curSolution.getRows(),curSolution.getColumns());
        newSolution.setSolution(curSolution.getSolution());
        newSolution.setCurRow(curSolution.getCurRow());
        newSolution.setCurCol(curSolution.getCurCol());
        newSolution.insertPiece(enteredPiece);

        return newSolution;
    }

    /**
     * for recursion needs - clone puzzle pieces list
     * @param puzzlePieces - current puzzle pieceslist
     * @param removedPiece piece to dismiss from the list
     * @return new list of puzzlePieces without the removed piece
     */

    private  List<PuzzlePiece> clonePuzzlePiecesList(List<PuzzlePiece> puzzlePieces, PuzzlePiece removedPiece){
        List<PuzzlePiece> newPuzzlePiecesList = new ArrayList<>();
        for (PuzzlePiece piece: puzzlePieces){
            if (!piece.equals(removedPiece)){
                newPuzzlePiecesList.add(piece);
            }
        }
        return newPuzzlePiecesList;
    }

    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }




    public PuzzleSolution getSolution() {
        return solution;
    }



    public void setSolution(PuzzleSolution solution) {
        this.solution = solution;
    }

}
