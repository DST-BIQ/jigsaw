package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileInputParser;
import com.att.biq.dst.jigsaw.fileUtils.FileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
}
