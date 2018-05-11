package com.att.biq.dst.jigsaw.puzzle;

/**
 *
 * @ author Tal
 * Manages the error collecting. holds array list for fatal and non-fatal errors.
 */

import java.util.ArrayList;

public class ErrorsManager {
    private ArrayList<String> fatalErrorsList = new ArrayList<>();
    private ArrayList<String> nonFatalErrorsList = new ArrayList<>();

    public ArrayList<String> getFatalErrorsList() {
        return fatalErrorsList;
    }

    public ArrayList<String> getNonFatalErrorsList() {
        return nonFatalErrorsList;
    }

    public void addFatalErrorsList(String error) {
        this.fatalErrorsList.add(error);
    }

    public void addNonFatalErrorsList(String error) {
        this.nonFatalErrorsList.add(error);
    }

    public boolean hasFatalErrors() {

        return (!fatalErrorsList.isEmpty());
    }

    public boolean hasNonFatalErrors() {

        return (!nonFatalErrorsList.isEmpty());
    }

}
