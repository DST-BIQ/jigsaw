package com.att.biq.dst.jigsaw.systemTest;

import com.att.biq.dst.jigsaw.puzzleManager.PuzzleManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Parameterized test to verify E2E flow from input to output
 */
public class SystemTestAbleToLoadPuzzle {

    String outputFilePath = "";
    File outputFile;
    String expectedOutputFilePath;
    String expectedFilesLocations = "./src/main/resources/input/AdvancedPuzzleTests/Output/";

    @ParameterizedTest(name = "System Test for puzzles")
    @ValueSource(strings = {
// "./src/main/resources/input/AdvancedPuzzleTests/Input/test1.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test2.in",
//             "./src/main/resources/input/AdvancedPuzzleTests/Input/test9.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test11.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test10.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test12.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test13.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test14.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test15.in",
//            "./src/main/resources/input/AdvancedPuzzleTests/Input/test16.in",
            "./src/main/resources/input/AdvancedPuzzleTests/Input/test17.in"})

    @Test
    public void systemTest(String filePath) {

        setUp(filePath);

// split the string to create new file


        PuzzleManager puzzleManager = new PuzzleManager(filePath, outputFilePath);

        puzzleManager.loadPuzzle();
        puzzleManager.playPuzzle();


        try {
            assertEquals(FileUtils.readFileToString(new File(outputFilePath), Charset.forName("UTF-8")), FileUtils.readFileToString(new File(expectedOutputFilePath), Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void setUp(String filePath) {

        PuzzleManager pm = new PuzzleManager();
        String[] takeStringForFilePath = filePath.split("Input/test");
        takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3);
        // if ourput exists - delete

        outputFilePath = "C:\\temp\\puzzleOutput\\puzzleTest" + takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3) + ".txt";
        pm.deleteFile(outputFilePath);
        expectedOutputFilePath = expectedFilesLocations + "test" + takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3) + ".out";

        outputFile = new File(outputFilePath);

//        expectedOutputFilePath = new File(expectedFilesLocations + "test" + takeStringForFilePath[1].substring(0, takeStringForFilePath[1].length() - 3) + ".out");
    }


}
