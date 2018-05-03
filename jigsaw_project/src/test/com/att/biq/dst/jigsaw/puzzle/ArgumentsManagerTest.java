package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.Parameters.GlobalParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * author = dorit
 */
class ArgumentsManagerTest {
    ArgumentsManager argumentsManager = new ArgumentsManager();


    @Test

    public void getArguments() {
        String[] args = {GlobalParameters.OPTION_THREADS + " 5",GlobalParameters.OPTION_ROTATE,GlobalParameters.OPTION_INPUTFILE + " c:\\temp\\1.tzt",GlobalParameters.OPTION_OUTPUTFILE + " c:\\temp\\rr.ttx"};
//        String[] args = {"-" + GlobalParameters.OPTION_ROTATE};
        argumentsManager.setOptions(args);
        assertTrue(argumentsManager.getRotationStatus());
        assertEquals(argumentsManager.getThreadNumberFromCommandLine(), 5);
        assertEquals(argumentsManager.getInputFilePathFromCommandLine(), "c:\\temp\\1.tzt");
        assertEquals(argumentsManager.getOutputFilePathFileFromCommandLine(), "c:\\temp\\rr.ttx");
    }


    @Test

    public void ThreadsArguments() {
        String[] args = {"-" + GlobalParameters.OPTION_THREADS + "=5"};
        argumentsManager.setOptions(args);
        assertEquals(argumentsManager.getThreadNumberFromCommandLine(), 5);
    }


    @Test
    public void getInputFile() {
        //TODO under resources
        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE + "=c:\\temp\\1.tzt"};
        argumentsManager.setOptions(args);
        assertEquals(argumentsManager.getInputFilePathFromCommandLine(), "c:\\temp\\1.tzt");
    }

    @Test
    public void getOutputFile() {
        String[] args = {"-" + GlobalParameters.OPTION_OUTPUTFILE + "=c:\\temp\\rr.ttx"};
        argumentsManager.setOptions(args);
        assertEquals(argumentsManager.getOutputFilePathFileFromCommandLine(), "c:\\temp\\rr.ttx");
    }


    //TODO add parameterized test for combinations
    //TODO print usage
    //TODO no mandatory parameters on command line

}