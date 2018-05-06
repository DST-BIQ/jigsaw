package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.parameters.ArgumentsManager;
import com.att.biq.dst.jigsaw.parameters.GlobalParameters;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * author = dorit
 */
class ArgumentsManagerTest {
    ArgumentsManager argumentsManager = new ArgumentsManager();


    @Test

    public void getArguments() {
        String[] args = {"-"+GlobalParameters.OPTION_THREADS , "5",
                "-"+GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-"+GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test2.in",
                "-"+GlobalParameters.OPTION_ROTATE};

        argumentsManager.setOptions(args);
        assertTrue(argumentsManager.getRotationStatus());
        assertEquals(argumentsManager.getThreadNumberFromCommandLine(), 5);
        assertEquals(argumentsManager.getInputFilePathFromCommandLine(), "./src/main/resources/input/SimplePuzzleTests/input/test1.in");
        assertEquals(argumentsManager.getOutputFilePathFileFromCommandLine(), "./src/main/resources/input/SimplePuzzleTests/input/test2.in");
    }


    @Test

    public void ThreadsArguments() {
        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_THREADS , "5" };
//        String[] args = {"-" + GlobalParameters.OPTION_THREADS + "=5"};
        argumentsManager.setOptions(args);
        assertEquals(argumentsManager.getThreadNumberFromCommandLine(), 5);
    }


    @Test
    public void getInputFile() {

        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in"};


        argumentsManager.setOptions(args);
        assertEquals(argumentsManager.getInputFilePathFromCommandLine(), "./src/main/resources/input/SimplePuzzleTests/input/test1.in");
    }

    @Test
    public void getOutputFile() {
        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/ForTestOutput/myfile.txt"};
        argumentsManager.setOptions(args);
        assertEquals(argumentsManager.getOutputFilePathFileFromCommandLine(), "./src/main/resources/ForTestOutput/myfile.txt");
    }





}