package com.att.biq.dst.jigsaw.puzzleManager;

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

public class PuzzleManager {

    /**
     * Puzzle manager should handle the puzzle creation process. Including, reading from file, error handling, finind solution and reporting
     */

    /////////////////////////////////////////   Class members

    private PuzzleSolution solution;
    private PuzzlePieceValidators puzzlePieceValidators;
    private Puzzle puzzle;
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

    }


    /////////////////////////////////////////   class methods




    public void loadPuzzle() throws IOException {
        puzzlePieces = puzzle.getPuzzle(readFromFile(Paths.get(inputFilePath)),  puzzlePieceValidators);
        if (puzzlePieces==null){
            reportErrors("A FATAL Error has occurred, cannot load Puzzle ");
        }

    }

    public void playPuzzle() throws IOException {
        solutionStructures = puzzle.calculateSolutionStructure(puzzlePieceValidators, puzzlePieces.size());
        if (reportList.size() > 0) {
            reportData(reportList, "file");

        }
        solution = puzzle.calculatePuzzleSolution(puzzlePieces, solutionStructures);


        if (solution != null) {
            preparePuzzleSolutionToPrint(solution);
            reportData(reportList, "file");
        }else if(puzzle.getErrorsManager().hasFatalErrors()){
            reportErrors("A FATAL Error has occurred, cannot solve Puzzle");
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
            reportList.add(convertPuzzlePiecesToString(finalSolution[i]).trim());
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
        BufferedWriter bw=null;
        try{
             fw = new FileWriter(outputFilePath , true);
             bw = new BufferedWriter(fw);
             bw.write(data);
             bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
        finally {
            if (bw!=null){bw.close();}
        }
    }

    private void reportErrors(String message) throws IOException {
        if (puzzle.getErrorsManager().hasFatalErrors()){
            reportData(puzzle.getErrorsManager().getFatalErrorsList(),"file");
            reportData(puzzle.getErrorsManager().getNonFatalErrorsList(),"file");
            throw new RuntimeException(message);
        }else if (puzzle.getErrorsManager().hasNonFatalErrors()){
            reportData(puzzle.getErrorsManager().getNonFatalErrorsList(),"file");
        }
    }

    public static String getTimeStamp() {
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH.mm.ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return df.format(cal.getTime());
    }


    public void deleteFile(String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }


    }
}