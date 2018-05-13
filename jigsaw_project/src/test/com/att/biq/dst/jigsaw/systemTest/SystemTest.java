package com.att.biq.dst.jigsaw.systemTest;

import com.att.biq.dst.jigsaw.puzzle.PuzzleManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//TODO polish error messages on system tests.
/**
 * Parameterized test to verify E2E flow from input to output
 */
public class SystemTest {
    String expectedOutputFilePath;
    String outputFilePath;
    File outputFile;
    String baseOutputFolder="./src/main/resources/ForTestOutput/";

    public void preparation(String filePath,boolean rotate,int numberOfThreads,String expectedFilesLocations) {

        PuzzleManager puzzleManager1 = new PuzzleManager();
        String[] args =new String[7];

        String[] takeStringForFilePath = filePath.split("Input/test");
        takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3);

        outputFilePath = baseOutputFolder + takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3) + ".txt";
        puzzleManager1.deleteFile(outputFilePath);
        expectedOutputFilePath = expectedFilesLocations + "test" + takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3) + ".out";

        outputFile = new File(outputFilePath);

        args[0] = "-outputFilePath";
        args[1] = outputFilePath;
        args[2] = "-inputFilePath";
        args[3] = filePath;
        args[4] = "-numThreads";
        args[5] = String.valueOf(numberOfThreads);
        if (rotate) { args[6]="-hasRotate";}
        else {args[6]="";}



        PuzzleManager puzzleManager = new PuzzleManager(args);

        puzzleManager.loadPuzzle();
        puzzleManager.playPuzzle();

    }


    @ParameterizedTest(name = "System Test for puzzles - no rotation and one thread. solution is available")
    @ValueSource(strings = {
             "./src/main/resources/input/AdvancedPuzzleTests/Input/test1.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test2.in",
             "./src/main/resources/input/AdvancedPuzzleTests/Input/test9.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test11.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test10.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test12.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test13.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test14.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test15.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test16.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test17.in",

    })

    public void noRotationOneThread(String filePath) throws IOException, InterruptedException {

        preparation(filePath,false,4,"./src/main/resources/input/AdvancedPuzzleTests/Output/");
            assertEquals(FileUtils.readFileToString(new File(expectedOutputFilePath), Charset.forName("UTF-8")), FileUtils.readFileToString(new File(outputFilePath), Charset.forName("UTF-8")));

    }

    @ParameterizedTest(name = "System Test for puzzles - no rotation and one thread. solution is available")
    @ValueSource(strings = {
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test1.in"
            ,
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test2.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test9.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test11.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test10.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test12.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test13.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test14.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test15.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test16.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test17.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test18.in"
    })
    public void useRotation(String filePath) throws IOException {
        preparation(filePath,true,4,"./src/main/resources/input/AdvancedPuzzleTests/OutputRotation/");
        assertEquals(FileUtils.readFileToString(new File(expectedOutputFilePath), Charset.forName("UTF-8")), FileUtils.readFileToString(new File(outputFilePath), Charset.forName("UTF-8")));

    }


    @ParameterizedTest(name = "System Test for puzzles")
    @ValueSource(strings = {
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test3.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test4.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test5.in"
            ,
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test6.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test7.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test8.in",

 })

    public void notAbleToLoadPuzzle(String filePath){

        assertThrows(RuntimeException.class,() -> {
            preparation(filePath,false,1,"./src/main/resources/input/AdvancedPuzzleTests/Output/");
        }
    );
        try {
            assertEquals( FileUtils.readFileToString(new File(expectedOutputFilePath), Charset.forName("UTF-8")),FileUtils.readFileToString(new File(outputFilePath), Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.getMessage();
        }

    }










}
