package com.att.biq.dst.jigsaw.puzzle.client;

import com.att.biq.dst.jigsaw.puzzle.ErrorsManager;
import com.google.gson.JsonArray;

public class PuzzleError {


    /**
     * @author dorit
     * report solution errors as json object
     */
    String errors;

    public PuzzleError(ErrorsManager errorsManager){

this.errors=createSolutionErrorsArray(errorsManager);
}


    private String createSolutionErrorsArray(ErrorsManager errorsManager) {


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

        return String.valueOf(errors);
    }
}
