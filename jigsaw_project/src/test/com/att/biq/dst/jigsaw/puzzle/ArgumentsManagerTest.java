package com.att.biq.dst.jigsaw.puzzle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * author = dorit
 */
class ArgumentsManagerTest {



    @Test

    public void getArgumentsClient() {


        String[] args = {"-"+GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-"+GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test2.in",
                "-"+GlobalParameters.OPTION_ROTATE,
        "-"+GlobalParameters.OPTION_IP,"2.2.2.2",
                "-"+GlobalParameters.OPTION_PORT,"2222"
        };
        ArgumentsManager argumentsManager = new ArgumentsManager(args,false);
        argumentsManager.setOptionsFromArguments();
        assertTrue(argumentsManager.getRotationStatus());
        assertEquals(argumentsManager.getInputFilePathFromCommandLine(), "./src/main/resources/input/SimplePuzzleTests/input/test1.in");
        assertEquals(argumentsManager.getOutputFilePathFileFromCommandLine(), "./src/main/resources/input/SimplePuzzleTests/input/test2.in");
        assertEquals(argumentsManager.getIPFromCommandLine(),"2.2.2.2");
        assertEquals(argumentsManager.getPortFromCommandLine(),"2222");
    }

    @Test

    public void getArgumentsServer() {


        String[] args = {

                "-"+GlobalParameters.OPTION_THREADS,"4",
                "-"+GlobalParameters.OPTION_PORT,"2222"
        };
        ArgumentsManager argumentsManager = new ArgumentsManager(args,true);
        argumentsManager.setOptionsFromArguments();

        assertEquals(4,argumentsManager.getThreadNumberFromCommandLine());
        assertEquals("2222",argumentsManager.getPortFromCommandLine());
    }
    @Test

    public void ThreadsArguments() {
        String[] args = {
                "-" + GlobalParameters.OPTION_THREADS , "5" };
        ArgumentsManager argumentsManager = new ArgumentsManager(args,true);

        argumentsManager.setOptionsFromArguments();
        assertEquals(5,argumentsManager.getThreadNumberFromCommandLine());
    }


    @Test
    public void getInputFile() {

        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in"};

        ArgumentsManager argumentsManager = new ArgumentsManager(args,false);
        argumentsManager.setOptionsFromArguments();
        assertEquals("./src/main/resources/input/SimplePuzzleTests/input/test1.in",argumentsManager.getInputFilePathFromCommandLine());
    }

    @Test
    public void getOutputFile() {
        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/ForTestOutput/myfile.txt"};
        ArgumentsManager argumentsManager = new ArgumentsManager(args,false);
        argumentsManager.setOptionsFromArguments();
        assertEquals("./src/main/resources/ForTestOutput/myfile.txt",argumentsManager.getOutputFilePathFileFromCommandLine());
    }

    @Test

    public void defaultValuePort(){
        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/ForTestOutput/myfile.txt"};
        ArgumentsManager argumentsManager = new ArgumentsManager(args,false);

        argumentsManager.setOptionsFromArguments();
        assertEquals( "7095",argumentsManager.getPortFromCommandLine());
    }

    @Test
    public void defaultValueIP(){
        String[] args = {"-" + GlobalParameters.OPTION_INPUTFILE ,"./src/main/resources/input/SimplePuzzleTests/input/test1.in",
                "-" + GlobalParameters.OPTION_OUTPUTFILE ,"./src/main/resources/ForTestOutput/myfile.txt"};
        ArgumentsManager argumentsManager = new ArgumentsManager(args,false);

        argumentsManager.setOptionsFromArguments();
        assertEquals("127.0.0.1",argumentsManager.getIPFromCommandLine() );


    }

    @Test
    public void defaultValueThreads(){
        String[] args = {
                };
        ArgumentsManager argumentsManager = new ArgumentsManager(args,true);

        argumentsManager.setOptionsFromArguments();
        assertEquals(4,argumentsManager.getThreadNumberFromCommandLine());

    }




}