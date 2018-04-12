package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class PuzzleManager {

    /**
     * Puzzle manager should handle the puzzle creation process. Including, reading from file, error handling, finind solution and reporting
     */

    /////////////////////////////////////////   Class members

    private PuzzleSolution solution;
    private PuzzlePieceValidators puzzlePieceValidators;
    private Puzzle puzzle;
    private FileManager fileManager;
    private String inputFilePath; // input file path
    private String outputFilePath; // output filepath
    private ArrayList<String> reportList; // all reports to file will be written to this list
    private Path path;
    ArrayList<PuzzlePiece> puzzlePieces;
    List<int[]> solutionStructures;

    /////////////////////////////////////////   class constructors

    public PuzzleManager() {

    }

    public PuzzleManager(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        puzzlePieceValidators = new PuzzlePieceValidators();
        puzzle = new Puzzle();
        reportList = new ArrayList<>();
        puzzlePieces = new ArrayList<>();
        solutionStructures = new ArrayList<>();
        fileManager = new FileManager();
    }


    /////////////////////////////////////////   class methods




    public void loadPuzzle() {
        puzzlePieces = puzzle.getPuzzle(inputFilePath, fileManager, puzzlePieceValidators);

    }

    public void playPuzzle() {
        solutionStructures = puzzle.calculateSolutionStructure(puzzlePieceValidators, puzzlePieces.size());
        if (reportList.size() > 0) {
            reportData(reportList, "file");

        }
        solution = puzzle.calculatePuzzleSolution(puzzlePieces, solutionStructures);


        if (solution != null) {
            preparePuzzleSolutionToPrint(solution);
            reportData(reportList, "file");
        }

    }


    /**
     * report solution to general array list
     *
     * @param solution
     */
    private void preparePuzzleSolutionToPrint(PuzzleSolution solution) {
        PuzzlePiece[][] finalSolution = solution.getSolution();
        for ( int i = 0; i < finalSolution.length; i++ ) {
            reportList.add(convertPuzzlePiecesToString(finalSolution[i]));
        }

    }


    private String convertPuzzlePiecesToString(PuzzlePiece[] puzzlePieces) {

        StringBuilder builder = new StringBuilder();
        for ( PuzzlePiece piece : puzzlePieces ) {
            builder.append(piece.toString());
        }
        return builder.toString();
    }

    /**
     * report data to file. select reporting option and what to report.
     * <p>
     * Currently supported method: file
     *
     * @param dataList
     * @param reportMethod
     */
    private void reportData(ArrayList<String> dataList, String reportMethod) {

        for ( String dataLine : dataList ) {
            switch (reportMethod) {
                case "file":
                    writeToFile(dataLine);
                    break;

                default:
                    System.out.println("no type selected sorry");
            }

        }

    }


    /**
     * Files handling section
     */


    /**
     * Read lines from file
     * return List of strings
     *
     * @param path
     * @return List<String></String>
     */
    private static List<String> readFromFile(Path path) {

        try {
            return readAllLines(path);

        } catch (IOException | NullPointerException ex) {
        //TODO handle exceptions
            System.out.println(ex.getMessage()); //handle an exception here


        }

        return null; // if fails and nothing to return.

    }

    /**
     * Append data to file
     *
     * @param data - string line to insert to file
     * @param -    file - the file to write into
     */

    private void writeToFile(String data) {


        try (FileWriter fw = new FileWriter(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
//TODO
            System.out.println("you have any error accessing your file:  " + e.getMessage());

        }

    }
}





//    public String getFileInputPath() {
//        return inputFilePath;
//    }
//    public static void main(String[] args) {
//     *   String inputFilePath = "c:/puzzle/test14.in";
//    *    FileManager fm = new FileManager("c:/puzzle/output/");
//   *     List<PuzzlePiece> puzzlePieces = getPuzzle(inputFilePath, fm, puzzlePieceValidators);
//   *     List<int[]> puzzleStructures = calculateSolutionStructure(puzzlePieceValidators, puzzlePieces.size());
//        PuzzleSolution solution = calculatePuzzleSolution(puzzlePieces,puzzleStructures);
//        if (solution!=null) {
//            printSolution(solution, fm);
//        }
//
//    }


