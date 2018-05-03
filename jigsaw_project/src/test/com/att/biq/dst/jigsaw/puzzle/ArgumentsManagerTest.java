package com.att.biq.dst.jigsaw.puzzle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * author = dorit
 */
class ArgumentsManagerTest {
    ArgumentsManager argumentsManager = new ArgumentsManager();

    @Test

    public void getRorationFromArguments() {


        String[] args = {"-" + GlobalParameters.OPTION_ROTATE};
        argumentsManager.setOptions(args);
        argumentsManager.getRotationStatus();
        assertTrue(argumentsManager.getRotationStatus());
//        assertEquals(argumentsManager.getThreadNumberFromCommandLine(),4);

    }



    @Test

    public void ThreadsArguments() {


        String[] args = {"-" + GlobalParameters.OPTION_THREADS+"=5"};
        argumentsManager.setOptions(args);
        argumentsManager.getThreadNumberFromCommandLine();

        assertEquals(argumentsManager.getThreadNumberFromCommandLine(),5);

    }


    @Test
    public void getInputFile(){

        String[] args = {"-" + GlobalParameters.OPTION_THREADS+"=5"};
        argumentsManager.setOptions(args);

    }

    @Test
    public void getOutputFile(){

    }


    //TODO add parameterized test for combinations

}