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
        List<PuzzlePiece> puzzlePieces = getPuzzle(inputFilePath, fm, puzzlePieceValidators);
        List<int[]> puzzleStructures = calculateSolutionStructure(puzzlePieceValidators, puzzlePieces.size());
        PuzzleSolution solution = calculatePuzzleSolution(puzzlePieces,puzzleStructures);
        printSolution(solution, fm);

    }

    private static void printSolution(PuzzleSolution solution, FileManager fileManager) {
        PuzzlePiece[][] finalSolution = solution.getSolution();
        for (int i=0;i<finalSolution[0].length;i++){
            fileManager.writeToFile(finalSolution[i].toString());
        }
    }

    private static PuzzleSolution calculatePuzzleSolution(List<PuzzlePiece> puzzlePieces, List<int[]> puzzleStructures) {

        for(int[] structure:puzzleStructures){
            PuzzleSolution solution = new PuzzleSolution(structure[0],structure[1]);
            PuzzleSolution possibleSolution = solve(solution, puzzlePieces);
            if (possibleSolution!=null)
            {return possibleSolution;}
        }

        return null;
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


    private static List<int[]> calculateSolutionStructure(PuzzlePieceValidators puzzlePieceValidator, int puzzleSize){
        List<int[]> structureOptions = new ArrayList<>();
        for (int rows=1; rows<=puzzleSize;rows++){
            int columns;
            if (puzzleSize%rows==0){
                columns = puzzleSize/rows;
                if (columns<=puzzlePieceValidator.getMinTopBottom() && rows<=puzzlePieceValidator.getMinLeftRigh() ){
                    structureOptions.add(new int[]{rows,columns});
                }
            }

        }
        return structureOptions;
    }
    private PuzzlePiece getMatch(int left, int top, int right, int bottom, ArrayList<PuzzlePiece> puzzlePieceArray ){

        for (PuzzlePiece piece : puzzlePieceArray){

            if (left == piece.getLeft()&& top ==piece.getTop()&& right == piece.getRight()&& bottom==piece.getBottom()){
                return piece;
            }
        }

        return null;

    }

    private static PuzzleSolution solve(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces){

    }

}
