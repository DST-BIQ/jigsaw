package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.PuzzleUtils.FileInputParser;
import org.apache.commons.cli.*;

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
 * Puzzle manager should handle the puzzle creation process. Including, reading from file, error handling, finind solution and reporting
 */

public class PuzzleManager {


    /////////////////////////////////////////   Class members

    private PuzzleSolution solution;
    private PuzzlePieceValidators puzzlePieceValidators;
    private Puzzle puzzle;
    private String inputFilePath; // input file path
    private String outputFilePath; // output filepath
    private ArrayList<String> reportList; // all reports to file will be written to this list
    List<int[]> solutionStructures;
    private FileInputParser fileInputParser;
    private ArrayList<Integer> piecesID = new ArrayList<>(); // list of all IDs from file
    private ArrayList<int[]> puzzlePieceList = new ArrayList<>();
    private ArgumentsManager argumentsManager = new ArgumentsManager();
    private boolean rotate;
    private int threadNumber;

    /////////////////////////////////////////   class constructors

    public PuzzleManager() {

    }

    public PuzzleManager(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        puzzlePieceValidators = new PuzzlePieceValidators();
        puzzle = new Puzzle();
        reportList = new ArrayList<>();
        solutionStructures = new ArrayList<>();
        fileInputParser = new FileInputParser(piecesID, puzzlePieceList);
    }


    /////////////////////////////////////////   class methods


    /**
     * producing puzzle pieces array from file
     *
     * @throws IOException
     */
    public void loadPuzzle() throws IOException {
        puzzle.getPuzzle(fileInputParser, readFromFile(Paths.get(inputFilePath)), puzzlePieceValidators);
        if (puzzle.getPuzzlePieces() == null) {
            reportErrors("A FATAL Error has occurred, cannot load Puzzle ");
        }

    }

    /**
     * generate possible puzzle solution
     * report to file: generated solution / fatal errors
     *
     * @throws IOException
     */
    public void playPuzzle() throws IOException {
        solutionStructures = puzzle.calculateSolutionStructure(puzzlePieceValidators, puzzle.getPuzzlePieces().size());
        if (reportList.size() > 0) {
            reportData(reportList, "file");

        }
        solution = puzzle.calculatePuzzleSolution(puzzle.getPuzzlePieces(), solutionStructures);

        if (solution != null) {
            preparePuzzleSolutionToPrint(solution);
            reportData(reportList, "file");
        } else if (puzzle.getErrorsManager().hasFatalErrors()) {
            reportErrors("A FATAL Error has occurred, cannot solve Puzzle");
        }
    }

    /**
     * report solution to general array list
     *
     * @param solution
     */
    private void preparePuzzleSolutionToPrint(PuzzleSolution solution) {
        PuzzlePiece[][] winnerSolution = solution.getSolution();
        for ( int i = 0; i < winnerSolution.length; i++ ) {
            reportList.add(convertPuzzlePiecesToString(winnerSolution[i]).trim());
        }

    }

    /**
     * convert pieces array to String, in order to report it to the output file
     *
     * @param puzzlePieces - array containint solution's puzzle pieces.
     * @return
     */
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
    private void reportData(ArrayList<String> dataList, String reportMethod) throws IOException {

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

    private void writeToFile(String data) throws IOException {
        FileWriter fw;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(outputFilePath, true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }


    /**
     * send errors to reportData method.
     *
     * @param message
     * @throws IOException
     */
    private void reportErrors(String message) throws IOException {
        if (puzzle.getErrorsManager().hasFatalErrors()) {
            reportData(puzzle.getErrorsManager().getFatalErrorsList(), "file");
            reportData(puzzle.getErrorsManager().getNonFatalErrorsList(), "file");
            throw new RuntimeException(message);
        } else if (puzzle.getErrorsManager().hasNonFatalErrors()) {
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
//
//    /**
//     * select run options using the apache common CLI
//     * Using a boolean option
//     * <p>
//     * A boolean option is represented on a command line by the presence of the option, i.e. if the option is found then the option value is true, otherwise the value is false.
//     */
//
//
//    public void handleCommandLineOptions(String[] args) {
//        setOptions(args);
//        getRotationStatus();
//        getThreadNumberFromCommandLine();
//        getInputFilePathFromCommandLine();
//        getOutputFilePathFileFromCommandLine();
//
//        // todo print usage in case of failure
//    }
//
//    /**
//     * Set the options we support on the puzzle project
//     */
//    private void setOptions(String[] args) {
//
//
//        options.addOption(new Option(GlobalParameters.OPTION_INPUTFILE, true, "input file location")); //TODO mandatory
//        options.addOption(new Option(GlobalParameters.OPTION_OUTPUTFILE, true, "output file location")); // todo mandatory
//        options.addOption(new Option(GlobalParameters.OPTION_ROTATE, "rotation is enabled"));
//        options.addOption(new Option(GlobalParameters.OPTION_THREADS, true, "number of threads. if 0 - no threads."));
//
//        CommandLineParser parser = new DefaultParser();
//
//        try {
//            cmd = parser.parse(options, args);
//        } catch (ParseException e) {
//            //TODO exception
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * get the rotation status (use later for select if we use rotation oe not)
//     *
//     * @return true/false
//     */
//
//    private boolean getRotationStatus() {
//
//        if (cmd.hasOption(GlobalParameters.OPTION_ROTATE)) {
//            rotate = true;
//        } else {
//            rotate = false;
//        }
//
//        return rotate;
//    }
//
//
    public boolean isRotate() {
        return argumentsManager.getRotationStatus();
    }
//
//
//    /**
//     * gets thread number from command line. if not exists, set threadNumber to 4
//     *
//     * @param
//     * @return number of threads to use
//     */
//    private int getThreadNumberFromCommandLine() {
//
//
//        if (cmd.hasOption(GlobalParameters.OPTION_THREADS)) {
//            threadNumber = Integer.valueOf((cmd.getOptionValue(GlobalParameters.OPTION_THREADS)));
//
//        } else {
//            threadNumber = 4;
//        }
//
//        return threadNumber;
//    }
//
//
    public int getThreadNumber() {
        return argumentsManager.getThreadNumberFromCommandLine();
    }

//    private String getInputFilePathFromCommandLine() {
//
//        if (cmd.hasOption(GlobalParameters.OPTION_INPUTFILE)) {
//            inputFilePath = cmd.getOptionValue(GlobalParameters.OPTION_INPUTFILE);
//
//        } else {
//            //TODO SHOW USAGE + UNIT TEST
//            System.exit(0);
//        }
//
//        return inputFilePath;
//    }
//
//
//    private String getOutputFilePathFileFromCommandLine() {
//
//        if (cmd.hasOption(GlobalParameters.OPTION_OUTPUTFILE)) {
//            outputFilePath = cmd.getOptionValue(GlobalParameters.OPTION_OUTPUTFILE);
//
//        } else {
//            //TODO SHOW USAGE + UNIT TEST
//            System.exit(0);
//        }
//
//        return outputFilePath;
//    }
//
    public String getInputFilePath() {
        return argumentsManager.getInputFilePathFromCommandLine();
    }

    public String getOutputFilePath() {
        return argumentsManager.getOutputFilePathFileFromCommandLine();
    }
}