package com.att.biq.dst.jigsaw.puzzleManager;

import com.att.biq.dst.jigsaw.fileUtils.FileInputParser;
import com.att.biq.dst.jigsaw.fileUtils.FileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.att.biq.dst.jigsaw.fileUtils.FileManager.readFromFile;


public class Puzzle {



//







    PuzzleSolution calculatePuzzleSolution(List<PuzzlePiece> puzzlePieces, List<int[]> puzzleStructures) {

        for(int[] structure:puzzleStructures){
            PuzzleSolution solution = new PuzzleSolution(structure[0],structure[1]);
            PuzzleSolution possibleSolution = solve(solution, puzzlePieces);
            if (possibleSolution!=null && possibleSolution.isValid())
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
    public  ArrayList<PuzzlePiece> getPuzzle(String filePath, FileManager fileManager, PuzzlePieceValidators puzzlePieceValidators){
        Path inputFilePath = Paths.get(filePath);
        FileInputParser fileInputParser = new FileInputParser();
        ArrayList<PuzzlePiece> puzzle = (ArrayList<PuzzlePiece>) fileInputParser.produceArrayForPuzzle(readFromFile(inputFilePath), fileManager);

        if (puzzle==null || !puzzlePieceValidators.validatePuzzle(puzzle, fileManager)){
            for(String error: fileManager.getErrorReportList()){
                fileManager.writeToFile(error);
            }
            throw new RuntimeException("Puzzle input is invalid");
        }
        return puzzle;
    }

    public  boolean checkGivenSolutionAndPuzzleFiles(String puzzleFilePath, String solutionFilePath){
        FileManager fileManager = new FileManager();
        ArrayList<PuzzlePiece> puzzlePieces = getPuzzle(puzzleFilePath,fileManager,new PuzzlePieceValidators());
        return checkPuzzleSolution(puzzlePieces,generateSolutionFromFile(solutionFilePath, puzzlePieces));
    }

    private  PuzzleSolution generateSolutionFromFile(String solutionFilePath, List<PuzzlePiece> puzzlePieces) {
        List<String> solutionFileData = readFromFile(Paths.get(solutionFilePath));
        PuzzleSolution solution = new PuzzleSolution(solutionFileData.size(), solutionFileData.get(0).length());
        //TODO - parse List<String> to List<Integer> and generate solution
        return solution;
    }

    public  boolean checkPuzzleSolution(ArrayList<PuzzlePiece> puzzle, PuzzleSolution solution ){

        if (verifySolutionSize(puzzle, solution)) return false;
        for (PuzzlePiece puzzlePiece: puzzle){
            if (!solution.contains(puzzlePiece)){
                return false;
            }
        }
        if (!solution.isValid()){ return false;}

        return true;
    }

    private  boolean verifySolutionSize(ArrayList<PuzzlePiece> puzzle, PuzzleSolution solution) {
        if (puzzle.size()==(solution.getSize())){
            return true;
        }
        return false;
    }


    public List<int[]> calculateSolutionStructure(PuzzlePieceValidators puzzlePieceValidator, int puzzleSize){
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

    private  List<PuzzlePiece> getMatch(int left, int top, int right, int bottom, List<PuzzlePiece> puzzlePieceArray ){
        List<PuzzlePiece> matchedPieces = new ArrayList<>();
        for (PuzzlePiece piece : puzzlePieceArray){
            boolean isValidPiece = true;
            if (left!=2&& left != piece.getLeft()){isValidPiece=false;}
            if (top!=2 && top!=piece.getTop()){isValidPiece=false;}
            if (right!=2 && right!=piece.getRight()){isValidPiece=false;}
            if (bottom!=2 && bottom!=piece.getBottom()){isValidPiece=false;}
             if (isValidPiece){matchedPieces.add(piece);}
            }
        return matchedPieces;
    }

    private  PuzzleSolution solve(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces){
        if (puzzlePieces.size()==0 && solution.isValid()) {return solution;}
        else if (puzzlePieces.size()==0){return null;}
            List<PuzzlePiece> foundPieces =null;
            if (solution.getCurRow() == 0) {
                if (solution.getCurCol() == 0) {
                    foundPieces = getMatch(0, 0, 2, 2, puzzlePieces);
                } else if (solution.getCurCol() < solution.getColumns()-1) {
                     foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0, 2, 2, puzzlePieces);
                }else if (solution.getCurCol() == solution.getColumns()-1){
                    foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0, 0, 2, puzzlePieces);
                }
            }else if (solution.getCurRow() == solution.getRows()-1){
                if (solution.getCurCol() == 0) {
                    foundPieces = getMatch(0, 0-solution.getAbovePiece().getBottom(), 2, 0, puzzlePieces);
                } else if (solution.getCurCol() < solution.getColumns()-1) {
                     foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0-solution.getAbovePiece().getBottom(), 2, 0, puzzlePieces);
                }else if (solution.getCurCol() == solution.getColumns()-1){
                     foundPieces = getMatch(0 - solution.getFormerPiece().getRight(), 0-solution.getAbovePiece().getBottom(), 0, 0, puzzlePieces);
                }else{
                    if (solution.getCurCol()==0){
                         foundPieces = getMatch(0 , 0-solution.getAbovePiece().getBottom(), 2, 0, puzzlePieces);
                    }else if (solution.getCurCol()==solution.getColumns()-1){
                        foundPieces = getMatch(0-solution.getFormerPiece().getRight() , 0-solution.getAbovePiece().getBottom(), 0, 2, puzzlePieces);
                    }else{
                        foundPieces = getMatch(0-solution.getFormerPiece().getRight() , 0-solution.getAbovePiece().getBottom(), 2, 2, puzzlePieces);
                    }
                }
            }
            if (foundPieces!=null) {
                PuzzleSolution possibleSolution = findSolution(solution, puzzlePieces, foundPieces);
                if (possibleSolution != null) return possibleSolution;
            }
       return solution;
    }

    private  PuzzleSolution findSolution(PuzzleSolution solution, List<PuzzlePiece> puzzlePieces, List<PuzzlePiece> foundPieces) {
        for (PuzzlePiece piece : foundPieces) {
            PuzzleSolution possibleSolution = solve(cloneSolution(solution, piece), clonePuzzlePiecesList(puzzlePieces, piece));
            if (possibleSolution != null) {
                return possibleSolution;
            }
        }
        return null;

    }

    private  PuzzleSolution cloneSolution(PuzzleSolution curSolution, PuzzlePiece enteredPiece){
        PuzzleSolution newSolution = new PuzzleSolution(curSolution.getRows(),curSolution.getColumns());
        newSolution.setSolution(curSolution.getSolution());
        newSolution.setCurRow(curSolution.getCurRow());
        newSolution.setCurCol(curSolution.getCurCol());
        newSolution.insertPiece(enteredPiece);

        return newSolution;
    }

    private  List<PuzzlePiece> clonePuzzlePiecesList(List<PuzzlePiece> puzzlePieces, PuzzlePiece removedPiece){
        List<PuzzlePiece> newPuzzlePiecesList = new ArrayList<>();
        for (PuzzlePiece piece: puzzlePieces){
            if (!piece.equals(removedPiece)){
                newPuzzlePiecesList.add(piece);
            }
        }
        return newPuzzlePiecesList;
    }

}
