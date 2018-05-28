package com.att.biq.dst.jigsaw.puzzle;
/**
 * @author Tal
 * this class contains the algorithm for puzzle solving.
 * including how to handle puzzle piece's location, and where to put it, according to
 * all defined input algorithm and input puzzle structure.
 * This class implements runnabld, and execute the threads on different solutions calculation..
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class PuzzleSolver implements Runnable {

    Puzzle puzzle;
    static PuzzleSolution endResult;
    PuzzleSolution solution;
    private ErrorsManager errorsManager = new ErrorsManager();
    private Map<Integer, PuzzlePiece> puzzlePieceMap;

    public PuzzleSolver(Puzzle puzzle, PuzzleSolution solution) {
        this.puzzle = puzzle;
        this.solution = solution;
        puzzlePieceMap = clonePuzzlePiecesList(puzzle.getPuzzlePieces());
    }


    @Override
    public void run() {
        PuzzleSolution solution = solve(this.solution);
        if (solution != null && solution.isValid()) {
            puzzle.setSolved();
            setSolution(solution);
        }
    }


    /**
     * calculates puzzle solution
     *
     * @param puzzleStructures - available structures of puzzle
     * @return possible puzzle solution if found. else returns null.
     * @
     */

    public static PuzzleSolution calculatePuzzleSolution(List<int[]> puzzleStructures, ThreadsManager threadsManager, Puzzle puzzle) {

        PuzzleSolver solver;
        ThreadPoolExecutor threadPoolExecutor = threadsManager.getThreadPoolExecutor();
        for (int i = puzzleStructures.size() - 1; i >= 0; i--) {
            PuzzleSolution attemptSolution = new PuzzleSolution(puzzleStructures.get(i)[0], puzzleStructures.get(i)[1]);
            solver = new PuzzleSolver(puzzle, attemptSolution);
            threadPoolExecutor.execute(solver);
        }
        threadPoolExecutor.shutdown();
        int activeCount = threadPoolExecutor.getActiveCount();
        int queueSize = threadPoolExecutor.getQueue().size();
        while (activeCount !=0 || queueSize !=0) {
            if (puzzle.isSolved()) {
                threadPoolExecutor.shutdownNow();
                return endResult;
            }
        }
        threadPoolExecutor.shutdownNow();
        return endResult;

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
        for (PuzzlePiece puzzlePiece : pieces) {
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
     * gets available structures for a possible solution
     *
     * @param puzzlePieceValidator - validators object
     * @return available solutions list (e.g: 1,6 ; 6,1 ; 2;3)
     */

    public static List<int[]> calculateSolutionStructure(PuzzlePieceValidators puzzlePieceValidator, int puzzleSize, boolean rotate) {
        List<int[]> structureOptions = new ArrayList<>();
        int maxIndex;
        if (rotate) {
            maxIndex = (int) Math.sqrt(puzzleSize);
        } else {
            maxIndex = puzzleSize;
        }
        for (int rows = 1; rows <= maxIndex; rows++) {
            int columns;
            if (puzzleSize % rows == 0) {
                columns = puzzleSize / rows;
                if ((columns + rows) * 2 <= puzzlePieceValidator.getTotalStraightEdges()) {
                    structureOptions.add(new int[]{rows, columns});
                }
            }

        }
        return structureOptions;
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
     * get list of matched pieces from the puzzle array, by condition defined on piece location.
     * left/right/top/buttom - if need specific value put it (e.g. top = 1, if does not matter, put 2 as condition value);
     *
     * @param left   - left side condition
     * @param top    - top side condition
     * @param right  - right side condition
     * @param bottom - bottom side condition
     *               //     * @param puzzlePieceMap - list of all puzzle pieces.
     * @return list of matched puzzle pieces.
     */


    public List<PuzzlePieceIdentity> getMatch(int left, int top, int right, int bottom) {
        List<PuzzlePieceIdentity> matchedPiecesIdentities = new ArrayList<>();
        PieceShape ps = new PieceShape(left, top, right, bottom);
        for (Map.Entry<PieceShape, ArrayList<PuzzlePieceIdentity>> treeEntry : puzzle.getTreeMap().entrySet()) {

            if (treeEntry.getKey().equals(ps)) {

                for (PuzzlePieceIdentity ppi : treeEntry.getValue()) { // rotate on this node and take the first that is not "INUSE"

                    if (!puzzlePieceMap.get(ppi.getPuzzlePieceID()).isInUse()) {
                        matchedPiecesIdentities.add(ppi);
                    }

                }

            }


        }

        return matchedPiecesIdentities;
    }

    private PuzzlePiece getPuzzlePieceFromIdentityID(PuzzlePieceIdentity ppi) {

        for (PuzzlePiece pp : puzzlePieceMap.values()) {

            if (ppi.getPuzzlePieceID() == pp.getId()) {
                return pp;
            }

        }
        return null;
    }

    /**
     * this method tried to find possible solution. if found returns is, else return null
     *
     * @param solution - current solution
     * @return possible solution if found.
     */

    public PuzzleSolution solve(PuzzleSolution solution) {
        if (foundSolution(solution)) {
            return solution;
        }
        if (puzzle.isSolved()) {
            return null;
        } else if (noMorePiecesAndNoValidSolution()) {
            return null;
        }
        List<PuzzlePieceIdentity> foundPieces = null;
        if (isOnFirstRow(solution)) {
            foundPieces = handleFirstRowSolution(solution);
        } else if (isOnLastRow(solution)) {
            foundPieces = handleBottomRowSolution(solution);
        } else {
            foundPieces = handleBetweenTopAndBottomRows(solution);
        }
        if (foundPieces != null) {
            PuzzleSolution possibleSolution = findSolution(solution, foundPieces);
            if (possibleSolution != null) {
                return possibleSolution;
            }
        }
        return null;
    }

    /**
     * get available pieces for top and bottom pieces
     *
     * @param solution- current solution
     *                  * @return list of matched pieces
     */

    private List<PuzzlePieceIdentity> handleBetweenTopAndBottomRows(PuzzleSolution solution) {
        List<PuzzlePieceIdentity> foundPieces;
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0 - solution.getAbovePiece().getShape().getBottom(), 2, 2);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getShape().getRight(), 0 - solution.getAbovePiece().getShape().getBottom(), 0, 2);
        } else {
            foundPieces = getMatch(0 - solution.getFormerPiece().getShape().getRight(), 0 - solution.getAbovePiece().getShape().getBottom(), 2, 2);
        }
        return foundPieces;
    }

    private boolean noMorePiecesAndNoValidSolution() {
        return isAllPuzzlePiecesInUse() && !puzzle.isSolved();
    }

    public boolean isAllPuzzlePiecesInUse() {
        for (PuzzlePiece puzzlePiece : this.puzzlePieceMap.values()) {
            if (!puzzlePiece.isInUse()) {
                return false;
            }
        }
        return true;
    }

    private boolean foundSolution(PuzzleSolution solution) {
        return solution.isValid();
    }


    private boolean isOnLastRow(PuzzleSolution solution) {
        return solution.getCurRow() == solution.getRows() - 1;
    }

    /**
     * get available pieces for bottom row
     *
     * @param solution - current solution
     *                 * @return list of matched pieces
     */

    private List<PuzzlePieceIdentity> handleBottomRowSolution(PuzzleSolution solution) {
        List<PuzzlePieceIdentity> foundPieces = null;
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0 - solution.getAbovePiece().getShape().getBottom(), 2, 0);
        } else if (isBetweenFirstAndLastColumns(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getShape().getRight(), 0 - solution.getAbovePiece().getShape().getBottom(), 2, 0);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getShape().getRight(), 0 - solution.getAbovePiece().getShape().getBottom(), 0, 0);
        }
        return foundPieces;
    }

    /**
     * get available pieces for first row
     *
     * @param solution - current solution
     * @return list of matched pieces
     */

    private List<PuzzlePieceIdentity> handleFirstRowSolution(PuzzleSolution solution) {
        List<PuzzlePieceIdentity> foundPieces = new ArrayList<>();
        if (isOnFirstColumn(solution)) {
            foundPieces = getMatch(0, 0, 2, 2);
        } else if (isBetweenFirstAndLastColumns(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getShape().getRight(), 0, 2, 2);
        } else if (isOnLastColumn(solution)) {
            foundPieces = getMatch(0 - solution.getFormerPiece().getShape().getRight(), 0, 0, 2);
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
     * @param foundPieces
     * @return possible solution found
     */
    private PuzzleSolution findSolution(PuzzleSolution solution, List<PuzzlePieceIdentity> foundPieces) {
        PuzzleSolution possibleSolution;
        for (PuzzlePieceIdentity piece : foundPieces) {
            solution.insertPiece(piece);
            puzzlePieceMap.get(piece.getPuzzlePieceID()).setInUse(true);
            possibleSolution = solve(solution);
            if (possibleSolution != null) {
                return possibleSolution;
            } else {
                puzzlePieceMap.get(piece.getPuzzlePieceID()).setInUse(false);
                solution.removePiece();

            }
        }
        return null;
    }

    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }


    public PuzzleSolution getSolution() {
        return solution;
    }


    public void setSolution(PuzzleSolution solution) {
        endResult = solution;
    }


    /**
     * for recursion needs - clone puzzle pieces list
     *
     * @param puzzlePieces - current puzzle pieceslist
     * @return new list of puzzlePieces without the removed piece
     */

    private Map<Integer, PuzzlePiece> clonePuzzlePiecesList(List<PuzzlePiece> puzzlePieces) {
        Map<Integer, PuzzlePiece> newPuzzlePiecesList = new HashMap<>();
        for (PuzzlePiece piece : puzzlePieces) {

            newPuzzlePiecesList.put(piece.getId(), new PuzzlePiece(piece.getId(),piece.getLeft(), piece.getTop(), piece.getRight(), piece.getBottom()));
        }
        return newPuzzlePiecesList;
    }
}
