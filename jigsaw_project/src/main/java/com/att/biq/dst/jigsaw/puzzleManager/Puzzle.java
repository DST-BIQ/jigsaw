package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileInputParser;
import com.att.biq.dst.jigsaw.fileUtils.FileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.att.biq.dst.jigsaw.fileUtils.FileManager.*;

public class Puzzle {



    private PuzzleSolution solution;
    private static PuzzlePieceValidators puzzlePieceValidators = new PuzzlePieceValidators();

    public static void main(String[] args) {
        String inputFilePath = "c:/puzzle/puzzle.txt";
        FileManager fm = new FileManager("c:/puzzle/output_");
        List<PuzzlePiece> puzzleDataFromFile = getPuzzle(inputFilePath, fm, puzzlePieceValidators);



    }

    /**
     *
     * @param filePath
     * @param fileManager
     * @return
     */
    public static ArrayList<PuzzlePiece> getPuzzle(String filePath, FileManager fileManager, PuzzlePieceValidators puzzlePieceValidators){
        Path inputFilePath = Paths.get(filePath);
        FileInputParser fileInputParser = new FileInputParser();
        ArrayList<PuzzlePiece> puzzle = (ArrayList<PuzzlePiece>) fileInputParser.produceArrayForPuzzle(fileManager.readFromFile(inputFilePath), fileManager);
        if (puzzle==null || !puzzlePieceValidators.validatePuzzle(puzzle, fileManager)){
            for(String error: fileManager.getErrorReportList()){
                fileManager.writeToFile(error);
            }
            throw new RuntimeException("Puzzle input is invalid");
        }
        return puzzle;
    }

    public static boolean checkGivenSolutionAndPuzzleFiles(String puzzleFilePath, String solutionFilePath){
        FileManager fm =new FileManager();
        ArrayList<PuzzlePiece> puzzlePieces = getPuzzle(puzzleFilePath,fm,new PuzzlePieceValidators());
        return checkPuzzleSolution(puzzlePieces,generateSolutionFromFile(solutionFilePath, puzzlePieces));
    }

    private static PuzzleSolution generateSolutionFromFile(String solutionFilePath, List<PuzzlePiece> puzzlePieces) {
        List<String> solutionFileData = readFromFile(Paths.get(solutionFilePath));
        PuzzleSolution solution = new PuzzleSolution(solutionFileData.size(), solutionFileData.get(0).length());
        //TODO - parse List<String> to List<Integer> and generate solution
        return solution;
    }

    public static boolean checkPuzzleSolution(ArrayList<PuzzlePiece> puzzle, PuzzleSolution solution ){

        if (verifySolutionSize(puzzle, solution)) return false;
        for (PuzzlePiece puzzlePiece: puzzle){
            if (!solution.contains(puzzlePiece)){
                return false;
            }
        }
        if (!solution.isValid()){ return false;}

        return true;
    }

    private static boolean verifySolutionSize(ArrayList<PuzzlePiece> puzzle, PuzzleSolution solution) {
        if (puzzle.size()==(solution.getSize())){
            return true;
        }
        return false;
    }


    private PuzzlePiece getMatch(int left, int top, int right, int bottom, ArrayList<PuzzlePiece> puzzlePieceArray ){

        for (PuzzlePiece piece : puzzlePieceArray){

            if (left == piece.getLeft()&& top ==piece.getTop()&& right == piece.getRight()&& bottom==piece.getBottom()){
                return piece;
            }
        }

        return null;

    }

}
