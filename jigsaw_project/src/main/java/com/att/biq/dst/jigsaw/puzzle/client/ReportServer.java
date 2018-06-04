package com.att.biq.dst.jigsaw.puzzle.client;

import com.google.gson.Gson;

/** author: dorit
 * main class for reporting output of the server. can be used  for errors and solution
 */


public class ReportServer {

    private Object puzzleSolution;

    public ReportServer(Object puzzleSolution) {
        this.puzzleSolution = puzzleSolution;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(puzzleSolution);
    }
}
