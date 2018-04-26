package com.att.biq.dst.jigsaw.PuzzleUtils;

import com.att.biq.dst.jigsaw.puzzle.Puzzle;
import com.att.biq.dst.jigsaw.puzzle.PuzzleSolution;

public class PuzzleSolver implements Runnable {

    Puzzle puzzle;
    PuzzleSolution solution;

    public PuzzleSolver(Puzzle puzzle, PuzzleSolution solution){
        this.puzzle=puzzle;
        this.solution=solution;
    }

    @Override
    public void run() {
        solution = puzzle.solve(solution,puzzle.getPuzzlePieces());
        if (solution!=null && solution.isValid()){
            puzzle.setSolution(solution);
            puzzle.setSolved(true);
        }
    }

    public PuzzleSolution getSolution(){
        return solution;
    }
}
