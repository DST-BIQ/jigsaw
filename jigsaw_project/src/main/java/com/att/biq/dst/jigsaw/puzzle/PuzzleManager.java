package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.parameters.ArgumentsManager;
import com.att.biq.dst.jigsaw.puzzleUtils.ErrorsManager;
import com.att.biq.dst.jigsaw.puzzleUtils.FileInputParser;
import com.att.biq.dst.jigsaw.puzzleUtils.PuzzleSolver;
import com.att.biq.dst.jigsaw.puzzleUtils.ThreadsManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.nio.file.Files.readAllLines;

/**
 * @author dorit, tal
 * Puzzle manager should handle the puzzle creation process.
 * Including, reading from file, error handling, finind solution and reporting
 */

public class PuzzleManager {


    /////////////////////////////////////////   Class members

    private PuzzleSolution solution;
    private PuzzlePieceValidators puzzlePieceValidators;
    private Puzzle puzzle;
    private String inputFilePath; // input file path
    private String outputFilePath; // output filepath
    private ArrayList<String> reportList; // all reports to file will be written to this list
    private List<int[]> solutionStructures;
    private FileInputParser fileInputParser;
    private ArgumentsManager argumentsManager = new ArgumentsManager();
    private boolean rotate;
    private int threadNumber;
    private ErrorsManager errorsManager;
    private ArrayList<Integer> piecesID = new ArrayList<>(); // list of all IDs from file
    private ArrayList<int[]> puzzlePieceList = new ArrayList<>();

    ThreadsManager threadsManager;

    /////////////////////////////////////////   class constructors

    public PuzzleManager() {

    }


    public PuzzleManager(String[] args) {

        argumentsManager.handleCommandLineOptions(args);
        this.inputFilePath = argumentsManager.getInputFilePathFromCommandLine();
        this.outputFilePath = argumentsManager.getOutputFilePathFileFromCommandLine();
        this.rotate = argumentsManager.getRotationStatus();
        this.threadNumber = argumentsManager.getThreadNumberFromCommandLine();
        puzzlePieceValidators = new PuzzlePieceValidators(rotate);
        reportList = new ArrayList<>();
        solutionStructures = new ArrayList<>();
        fileInputParser = new FileInputParser();
        this.errorsManager = new ErrorsManager();
        puzzle = new Puzzle(errorsManager, rotate);
        threadsManager = new ThreadsManager(this.threadNumber);

    }


    /////////////////////////////////////////   class methods


    /**
     * producing puzzle pieces array from file
     *
     * @throws IOException
     */
    public void loadPuzzle() {
        puzzle.getPuzzlePiecesArray(fileInputParser, readFromFile(Paths.get(inputFilePath)), puzzlePieceValidators);
        if (puzzle.getPuzzlePieces() == null) {
            reportErrors();
            throw new  RuntimeException ("cannot continue with the process.");
        }

      puzzle.indexingPuzzlePiecesToTree(puzzle.getPuzzlePieces(), argumentsManager.getRotationStatus());

    }

    /**
     * generate possible puzzle solution
     * report to file: generated solution / fatal errors
     *
     * @throws IOException
     */
    public void playPuzzle(){
        solutionStructures = PuzzleSolver.calculateSolutionStructure(puzzlePieceValidators, puzzle.getPuzzlePieces().size(), rotate);
        if (reportList.size() > 0) {
            reportData(reportList, "file");

        }
        solution = PuzzleSolver.calculatePuzzleSolution(solutionStructures, threadsManager, puzzle);

        if (solution != null) {
            preparePuzzleSolutionToPrint(solution);
            reportData(reportList, "file");
        } else if (puzzle.getErrorsManager().hasFatalErrors()) {
            reportErrors();
        }
    }

    /**
     * report solution to general array list
     *
     * @param solution
     */
    private void preparePuzzleSolutionToPrint(PuzzleSolution solution) {
        PuzzlePieceIdentity[][] winnerSolution = solution.getSolution();
        for ( int i = 0; i < winnerSolution.length; i++ ) {
            reportList.add(convertPuzzlePiecesToString(winnerSolution[i]).trim());
        }

    }

    /**
     * convert pieces array to String, in order to report it to the output file
     *
     * @param puzzlePieces - array containing solution's puzzle pieces.
     * @return
     */
    private String convertPuzzlePiecesToString(PuzzlePieceIdentity[] puzzlePieces) {

        StringBuilder builder = new StringBuilder();
        for ( PuzzlePieceIdentity piece : puzzlePieces ) {
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


        try (FileWriter fw = new FileWriter(outputFilePath, true); BufferedWriter bw = new BufferedWriter(fw)) {

            if (isDirectory(outputFilePath)) {
                outputFilePath = outputFilePath + "output_" + getTimeStamp() + ".txt";

            }


            for ( String dataLine : dataList ) {
                switch (reportMethod) {
                    case "file":
                        writeToFile(dataLine, bw);
                        break;

                    default:
                        System.out.println("no type selected sorry");
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
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

    private void writeToFile(String data, BufferedWriter bw) {

        try {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
    }


    /**
     * send errors to reportData method.
     *
     * @throws IOException
     */
    private void reportErrors() {
        if (puzzle.getErrorsManager().hasFatalErrors()) {
            reportData(puzzle.getErrorsManager().getFatalErrorsList(), "file");
//            reportData(puzzle.getErrorsManager().getNonFatalErrorsList(), "file");
        }
        if (puzzle.getErrorsManager().hasNonFatalErrors()) {
            reportData(puzzle.getErrorsManager().getNonFatalErrorsList(), "file");
        }
    }

    /**
     * generated timestamp, if wish to generate string with dedicated timestamp
     *
     * @return
     */
    public static String getTimeStamp() {
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH.mm.ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return df.format(cal.getTime());
    }


    /**
     * delete file is exists.
     *
     * @param filePath file to delete.
     */
    public void deleteFile(String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }


    }


    /**
     * @return
     */

    public boolean isRotate() {
        return rotate;
    }

    public int getThreadNumber() {
        return threadNumber;
    }


    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }


    public boolean isDirectory(String filePath) {
        File file = new File(filePath);

        if (file.isDirectory()) {
            return true;
        }
        return false;

    }
}