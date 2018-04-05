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


    }

    /**
     *
     * @param filePath
     * @param fileManager
     * @return
     */
    public static ArrayList<int[]> getPuzzle(String filePath, FileManager fileManager){
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
}
