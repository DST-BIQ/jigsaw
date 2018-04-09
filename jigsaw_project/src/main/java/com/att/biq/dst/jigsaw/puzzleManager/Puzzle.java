package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileInputParser;
import com.att.biq.dst.jigsaw.fileUtils.FileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Puzzle {


    private PuzzleSolution solution;


    public static void main(String[] args) {
        String inputFilePath = "c:/puzzle/puzzle.txt";
        FileManager fm = new FileManager("c:/puzzle/output_");
        List<int[]> puzzleDataFromFile = getPuzzle(inputFilePath, fm);
        //TODO - convert List<int[]> to List<PuzzlePiece>
        //TODO - find solution
        //TODO - print solution to file

    }

    /**
     *
     * @param filePath
     * @param fileManager
     * @return
     */
    private static ArrayList<int[]> getPuzzle(String filePath, FileManager fileManager){
        Path inputFilePath = Paths.get(filePath);
        FileInputParser fileInputParser = new FileInputParser();
        ArrayList<int[]> puzzle =fileInputParser.produceArrayForPuzzle(FileManager.readFromFile(inputFilePath), fileManager);
        if (puzzle==null || !PuzzlePieceValidators.validatePuzzle(puzzle, fileManager)){
            for(String error: fileManager.getErrorReportList()){
                fileManager.writeToFile(error);
            }
            throw new RuntimeException("Puzzle input is invalid");
        }
        return puzzle;
    }

    private static boolean checkPuzzleSolution(ArrayList<PuzzlePiece> puzzle,PuzzleSolution solution ){
        PuzzlePiece[][] solutionPieces = solution.getSolutionPieces();
        if (verifySolutionSize(puzzle, solution)) return false;
        for (PuzzlePiece puzzlePiece: puzzle){
            if (!solution.contains(puzzlePiece)){
                return false;
            }
        }

        for (int row = 0; row<= solutionPieces.length; row++){
            int rowSum = 0;
            for (int column = 0; column<= solutionPieces[row].length; column++){
                rowSum+= solutionPieces[row][column].getSumEdges();
            }
            if (rowSum!=0){
                return false;
            }
        }
        //Verify all puzzle tops are 0
        for (int column = 0;column<=solutionPieces.length;column++){
            if (solutionPieces[0][column].getTop()!=0){
                return false;
            }
        }
        //Verify all puzzle bottoms are 0
        for (int column = 0;column<=solutionPieces.length;column++){
            if (solutionPieces[solutionPieces.length][column].getBottom()!=0){
                return false;
            }
        }
        //Verify all puzzle lefts are 0
        for (int row = 0;row<=solutionPieces.length;row++){
            if (solutionPieces[row][0].getLeft()!=0){
                return false;
            }
        }
        //Verify all puzzle rights are 0
        for (int row = 0;row<=solutionPieces.length;row++){
            if (solutionPieces[row][solutionPieces[0].length].getRight()!=0){
                return false;
            }
        }
        return true;
    }

    private static boolean verifySolutionSize(ArrayList<PuzzlePiece> puzzle, PuzzleSolution solution) {
        if (puzzle.size()==(solution.getSize())){
            return true;
        }
        return false;
    }
}