package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.puzzle.server.Puzzle;
import com.att.biq.dst.jigsaw.puzzle.server.PuzzlePieceIdentity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.att.biq.dst.jigsaw.puzzle.server.PuzzleSolution;

import java.util.ArrayList;

public class ReportFormatter {

private PuzzleSolution puzzleSolution;
private ErrorsManager errorsManager;
private Puzzle puzzle;
//private PuzzleSolution1 puzzleSolution1;

public String ReportFormatter(PuzzleSolution puzzleSolution){ // TODO in case puzzle has solution

    this.puzzleSolution=puzzleSolution;



    return convertPuzzlePiecesToJson(new PuzzleSolution1(puzzleSolution));
}


    public String ReportFormatter(ErrorsManager errorsManager){ // TODO in case puzzle has errors

        this.errorsManager=errorsManager;
        return convertPuzzlePiecesToJson(new PuzzleSolution1(errorsManager.getFatalErrorsList(),errorsManager.getNonFatalErrorsList()));
    }



    public String convertPuzzlePiecesToJson(PuzzleSolution1 solution) {
        Gson gson = new Gson();
        gson.toJson(solution);
//        JsonObject outputReport = new JsonObject();
//        JsonObject puzzle = new JsonObject();
//
//        JsonObject solution = new JsonObject();
////        JsonObject errors = new JsonObject();
//        JsonArray solutionPieces;
//
//
//        outputReport.add("PuzzleSolution", puzzle);
//        puzzle.addProperty("SolutionExists", this.puzzle.isSolved());
//        if (this.puzzle.isSolved()) {
//            puzzle.add("PuzzleSolution", solution);
//            solution.addProperty("Rows", puzzlePieces.length);
//            solutionPieces = createSolutionPiecesArray(puzzlePieces);//TODO here insert solution
//            solution.add("SolutionPieces", solutionPieces);
//        } else {
//
//            puzzle.add("Errors", createSolutionErrorsArray(errorsManager));
//        }
        System.out.println(gson.toJson(solution));
        return gson.toJson(solution);

    }

    /**
     * create solution pieces array, in case there is a solution
     *
     * @param puzzlePieces
     * @return JsonArray
     */
    private JsonArray createSolutionPiecesArray(PuzzlePieceIdentity[][] puzzlePieces,boolean rotate) {

        JsonArray solutionPieces = new JsonArray();

        JsonArray rowSolutionPieces = new JsonArray();
        for (int i=0;i<puzzlePieces.length;i++){

            for ( PuzzlePieceIdentity ppi : puzzlePieces[i] ) {
                JsonObject piece = new JsonObject();
                piece.addProperty("id", ppi.getPuzzlePieceID());
                if (rotate) {
                    piece.addProperty("rotate", ppi.getRotation());
                }
                rowSolutionPieces.add(piece);
            }
            solutionPieces.add(rowSolutionPieces);
//            rowSolutionPieces=null;
        }


        return solutionPieces;
    }

    /**
     * Create error json objects
     * *
     *
     * @return JsonArray
     */
    private JsonArray createSolutionErrorsArray(ErrorsManager errorsManager) {


        JsonArray errors = new JsonArray();
        if (!errorsManager.getFatalErrorsList().isEmpty()) {
            for ( String error : errorsManager.getFatalErrorsList() ) {

                errors.add(error);

            }
        }
        if (!errorsManager.getNonFatalErrorsList().isEmpty()) {
            for ( String error : errorsManager.getNonFatalErrorsList() ) {

                errors.add(error);

            }
        }

        return errors;
    }


    public class PuzzleSolution1{
        boolean solutionExists;
        ArrayList<String> Errors;
        int id;
        int rotate;

        public PuzzleSolution1(ArrayList<String> fatalerrors,ArrayList<String> nonFatalErrors){
            solutionExists=false;

        }


        public PuzzleSolution1(PuzzleSolution solution){
            solutionExists=true;

        }


    }
}


