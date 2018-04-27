package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.PuzzleUtils.ErrorsManager;
import com.att.biq.dst.jigsaw.PuzzleUtils.FileInputParser;
import com.att.biq.dst.jigsaw.PuzzleUtils.PuzzleSolver;
import com.att.biq.dst.jigsaw.PuzzleUtils.ThreadsManager;

import java.util.ArrayList;
import java.util.List;


public class Puzzle {
    private ErrorsManager errorsManager = new ErrorsManager();//TODO - move to PuzzleSolver
    private List<PuzzlePiece> puzzlePieces;
    private boolean isSolved = false;//TODO - move to PuzzleSolver or maybe remove
    private PuzzleSolution solution = null;//TODO - move to PuzzleSolver

    /**
     * calculates puzzle solution
     * @
     * @param puzzleStructures - available structures of puzzle
     * @return possible puzzle solution if found. else returns null.
     */
    //TODO - move calculation to PuzzleSolver
    PuzzleSolution calculatePuzzleSolution(List<int[]> puzzleStructures, ThreadsManager threadsManager) throws InterruptedException {
        int counter=0;
        List<Runnable> solvers = new ArrayList<>();
        for(int[] structure:puzzleStructures) {
            PuzzleSolution solution = new PuzzleSolution(structure[0], structure[1]);
            PuzzleSolver solver = new PuzzleSolver(this, solution);
            solvers.add(solver);
            threadsManager.getThreadPoolExecutor().execute(solver);
        }
        while (solution==null && counter<200){//TODO - find another exit criteria
            Thread.sleep(10);
            counter++;
        }
        if (solution!=null){
            threadsManager.getThreadPoolExecutor().shutdown();
            return solution;

        }else {

            return null;
        }
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

    /**
     * checks is the puzzle solution created is valid
     * @param puzzle - tested puzzle
     * @param solution - tested solution
     * @return valid/not valid
     */
    //TODO - move to PuzzleSolver
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
     * Verify if puzzle size equal to solution size
     * checks is the puzzle solution created is valid
     * @param puzzle - tested puzzle
     * @param solution - tested solution
     * @return true/false
     */
    //TODO - move to PuzzleSolver
    private static   boolean verifySolutionSize(List<PuzzlePiece> puzzle, PuzzleSolution solution) {
        if (puzzle.size()==(solution.getSize())){
            return true;
        }
        return false;
    }

    /**
     * gets available structures for a possible solution
     * @param puzzlePieceValidator - validators object
     *
     * @return available solutions list (e.g: 1,6 ; 6,1 ; 2;3)
     */
    //TODO - move to PuzzleSolver
    public List<int[]> calculateSolutionStructure(PuzzlePieceValidators puzzlePieceValidator){
        List<int[]> structureOptions = new ArrayList<>();
        int puzzleSize = getPuzzlePieces().size();

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
     *  get list of matched pieces from teh puzzle array, by condition defined on piece location.
     *  left/right/top/buttom - if need specific value put it (e.g. top = 1, if does not matter, put 2 as condition value);
     * @param left - left side condition
     * @param top - top side condition
     * @param right - right side condition
     * @param bottom - bottom side condition
     * @param puzzlePieceArray - list of all puzzle pieces.
     * @return list of matched puzzle pieces.
     */
    //TODO - move to PuzzleSolver
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
    //TODO - move to PuzzleSolver
    public PuzzleSolution solve(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces){
        if (foundSolution(solution, puzzlePieces)) {return solution;}
        if (isSolved){return null;}
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
    //TODO - move to PuzzleSolver
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
    //TODO - move to PuzzleSolver
    private boolean noMorePiecesAndNoValidSolution(List<PuzzlePiece> puzzlePieces) {
        return puzzlePieces.size()==0;
    }
    //TODO - move to PuzzleSolver
    private boolean foundSolution(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces) {
        return puzzlePieces.size()==0 && solution.isValid();
    }


    //TODO - move to PuzzleSolver
    private boolean isOnLastRow(PuzzleSolution solution) {
        return solution.getCurRow() == solution.getRows()-1;
    }

    /**
     * get available pieces for bottom row
     * @param solution - current solution
     * @param puzzlePieces - current array pieces
     * @return list of matched pieces
     */
    //TODO - move to PuzzleSolver
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
    //TODO - move to PuzzleSolver
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


    //TODO - move to PuzzleSolver
    private boolean isOnLastColumn(PuzzleSolution solution) {
        return solution.getCurCol() == solution.getColumns()-1;
    }
    //TODO - move to PuzzleSolver
    private boolean isBetweenFirstAndLastColumns(PuzzleSolution solution) {
        return solution.getCurCol() < solution.getColumns()-1;
    }
    //TODO - move to PuzzleSolver
    private boolean isOnFirstColumn(PuzzleSolution solution) {
        return solution.getCurCol() == 0;
    }
    //TODO - move to PuzzleSolver
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
    //TODO - move to PuzzleSolver
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
    //TODO - move to PuzzleSolver
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
    //TODO - move to PuzzleSolver
    private  List<PuzzlePiece> clonePuzzlePiecesList(List<PuzzlePiece> puzzlePieces, PuzzlePiece removedPiece){
        List<PuzzlePiece> newPuzzlePiecesList = new ArrayList<>();
        for (PuzzlePiece piece: puzzlePieces){
            if (!piece.equals(removedPiece)){
                newPuzzlePiecesList.add(piece);
            }
        }
        return newPuzzlePiecesList;
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

    //TODO - move to PuzzleSolver
    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }

    public List<PuzzlePiece> getPuzzlePieces() {
        return puzzlePieces;
    }
    //TODO - move to PuzzleSolver or maybe even remove
    public boolean isSolved() {
        return isSolved;
    }
    //TODO - move to PuzzleSolver
    public PuzzleSolution getSolution() {
        return solution;
    }
    //TODO - move to PuzzleSolver or maybe remove
    public void setSolved(boolean solved) {
        isSolved = solved;
    }
    //TODO - move to PuzzleSolver or maybe remove
    public void setSolution(PuzzleSolution solution) {
        this.solution = solution;
    }
}
