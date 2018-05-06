package com.att.biq.dst.jigsaw.systemTest;

import com.att.biq.dst.jigsaw.puzzle.ArgumentsManager;
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



    String outputFilePath = "";
    File outputFile;
    String expectedOutputFilePath;
    String expectedFilesLocations = "./src/main/resources/input/AdvancedPuzzleTests/Output/";
    PuzzleManager puzzleManager = new PuzzleManager();
    ArgumentsManager argumentsManager = new ArgumentsManager();



    @ParameterizedTest(name = "System Test for puzzles")
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
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test17.in"
    })

    public void AbleToLoadPuzzle(String filePath) throws IOException {
        preparation(filePath);

        try {
            assertEquals(FileUtils.readFileToString(new File(outputFilePath), Charset.forName("UTF-8")), FileUtils.readFileToString(new File(expectedOutputFilePath), Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test18.in"
 })

    public void notAbleToLoadPuzzle(String filePath) throws IOException {

//        preparation(filePath);
        Throwable exception = assertThrows(RuntimeException.class,() -> {
            preparation(filePath);
        }
    );

        try {
            assertEquals( FileUtils.readFileToString(new File(expectedOutputFilePath), Charset.forName("UTF-8")),FileUtils.readFileToString(new File(outputFilePath), Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }




    }



    public void preparation(String filePath) throws IOException {


        String[] takeStringForFilePath = filePath.split("Input/test");
        takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3);

        outputFilePath = "./src/main/resources/ForTestOutput/" + takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3) + ".txt";
        puzzleManager.deleteFile(outputFilePath);
        expectedOutputFilePath = expectedFilesLocations + "test" + takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3) + ".out";

        outputFile = new File(outputFilePath);
        String[] args =new String[4];
        args[0] = "-outputFilePath";
        args[1] = outputFilePath;
        args[2] = "-inputFilePath";
        args[3] = filePath;
//        args[2] = "numThreads 4";
//        args[3] = "hasRotate";



        PuzzleManager puzzleManager = new PuzzleManager(args);

        puzzleManager.loadPuzzle();
        puzzleManager.playPuzzle();

    }


}
