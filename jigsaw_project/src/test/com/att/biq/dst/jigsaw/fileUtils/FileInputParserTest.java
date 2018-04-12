package com.att.biq.dst.jigsaw.fileUtils;

import com.att.biq.dst.jigsaw.puzzleManager.PuzzlePiece;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.nio.file.Files.readAllLines;
import static org.junit.jupiter.api.Assertions.*;

public class FileInputParserTest {

    static String basePath = "C:\\BIQ\\jigsaw\\jigsaw\\jigsaw_project\\src\\main\\resources\\input\\";

    FileInputParser fip = new FileInputParser();
//    FileManager fm = new FileManager();


    // *************************   Verify first line Tests
    @Test
    public void verifyCorrectFirstLine() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=4");

        assertEquals(4, FileInputParser.getNumberOfElements(list, new ErrorsManager()));
    }


    @Test
    public void convertPuzzleArrayPossitive() {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1, 0, 1, 0, -1});
        list.add(new int[]{2, 1, 0, 0, 1});

        List<PuzzlePiece> puzzlePieces = new ArrayList<>();
        puzzlePieces.add(new PuzzlePiece(1, 0, 1, 0, -1));
        puzzlePieces.add(new PuzzlePiece(2, 1, 0, 0, 1));

        assertTrue(puzzlePieces.get(0).equals(FileInputParser.convertPuzzleArray(list).get(0)));
        assertTrue(puzzlePieces.get(1).equals(FileInputParser.convertPuzzleArray(list).get(1)));


    }

    @Test
    public void missingNumnElements() {
//        Path path = Paths.get(basePath + "testFileValidFormat.txt");
        List<String> list = new ArrayList<>();
        list.add("NElements=4");

        assertEquals(-1, FileInputParser.getNumberOfElements(list, new ErrorsManager()));
    }

    @Test
    public void missingInteger() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=");

        assertEquals(-1, FileInputParser.getNumberOfElements(list, new ErrorsManager()));
    }


    @Test
    public void notIntegerOnNumberOfElements() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=o");

        assertEquals(-1, FileInputParser.getNumberOfElements(list, new ErrorsManager()));
    }

    @Test
    public void spacesBeforeNumElements() {
        List<String> list = new ArrayList<>();
        list.add("   NumElements=77");

        assertEquals(77, FileInputParser.getNumberOfElements(list, new ErrorsManager()));
    }


    @Test
    public void spacesAfterNumElements() {
        List<String> list = new ArrayList<>();
        list.add("NumElements    =77");

        assertEquals(77, FileInputParser.getNumberOfElements(list, new ErrorsManager()));
    }

    @Test
    public void firstLineParseError() throws IOException {
        Path path = Paths.get(basePath + "testFileValidFormat.txt");
        List<String> list = readAllLines(path);
        assertEquals(FileInputParser.getNumberOfElements(list, new ErrorsManager()), 4);
    }

    // *************************   Verify line contains only spaces
    @Test
    public void lineContainsOnlySpaces() {
        String line = " ";
        assertTrue(FileInputParser.isLineContainsOnlySpaces(line));

    }

    @Test
    public void lineContainsAlsoSpaces() {

        assertFalse(fip.isLineContainsOnlySpaces("1, 0,-1 ,9"));

    }

    // *************************   Verify line is empty
    @Test
    public void lineIsEmptyNegative() {

          assertFalse(fip.isLineEmpty("34, 1, 0,-1 ,9"));


    }

    @Test
    public void lineIsEmpty() {
       assertTrue(fip.isLineEmpty(""));

    }


    @Test
    public void lineIsEmptyOneSpace() {

        assertFalse(fip.isLineEmpty(" "));

    }


    // *************************   Verify line idInRange


    @Test
    public void IDInRange() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        list.add("34  1  0 -1  1 1");
        list.add("24  1  0 -1  1 1");
        list.add("100  1  0 -1  1 1");
        list.add("101  1  0 -1  1 1");
        list.add("109  1  0 -1  1 1");

        assertTrue(FileInputParser.idInRange(list, list.get(1), new ErrorsManager()));
        assertTrue(FileInputParser.idInRange(list, list.get(2), new ErrorsManager()));
        assertTrue(FileInputParser.idInRange(list, list.get(3), new ErrorsManager()));


    }


    @Test
    public void IDNOTInRange() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        list.add("34  1  0 -1  1 1");
        list.add("24  1  0 -1  1 1");
        list.add("100  1  0 -1  1 1");
        list.add("101  1  0 -1  1 1");
        list.add("109  1  0 -1  1 1");




    }

    // *************************   Verify getLinePuzzlePieceID
    @Test

    public void linePuzzlePieceIDValid() {

        assertEquals(34, fip.getLinePuzzlePieceID("34  1  0 -1  1 1"));
    }


    @Test

    public void linePuzzlePieceIDNotInteger() {

        assertEquals(-1, fip.getLinePuzzlePieceID("aa  1  0 -1  1 1"));
    }

// *************************   Verify getLinePuzzlePieceID
//getPuzzlePieceData(String line)

    @Test
    public void getValidPuzzlePiece() {

        assertEquals("0 -1 1 1", fip.getPuzzlePieceData("5 0 -1 1 1"));
    }


    @Test
    public void getValidPuzzlePieceWithSpaces() {

        assertEquals("0 -1 1 1", FileInputParser.getPuzzlePieceData("5  0 -1  1 1"));
    }

    // *************************   Verify getFileRange
    @Test
    public void getFileRangeValid() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        assertEquals("1-100", FileInputParser.getFileRange(list, new ErrorsManager()));
    }


    @Test
    public void getFileRangeOneRange() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=1");
        assertEquals("1", FileInputParser.getFileRange(list, new ErrorsManager()));
    }


    @Test
    public void getFileRangeNonValid() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=t");
        assertEquals("N/A", FileInputParser.getFileRange(list, new ErrorsManager()));
    }

    // *************************   Verify isLineBeginswithDash


    @Test
    public void lineBeginsWithDash() {

        assertTrue(FileInputParser.isLineBeginswithDash("#NumElements=t"));

        assertTrue(FileInputParser.isLineBeginswithDash("#5  0 -1  1 1"));
    }

    @Test
    public void lineBeginsWithDash_ignoreSpaces() {

        assertTrue(FileInputParser.isLineBeginswithDash("   #NumElements=t"));
    }


    @Test
    public void lineBeginsWithNoDash() {

        assertFalse(FileInputParser.isLineBeginswithDash("NumElements=t"));
    }

    @Test
    public void lineBeginsWithNoDashWithSpaces() {

        assertFalse(FileInputParser.isLineBeginswithDash("  NumElements=t"));
    }

    // *************************   Verify listMissingElementInInputFile


    @Test
    public void listMissingElementInInputFile() {

        int numberOfElements = 6;
        int[][] pieceArray = {{2, 0, 0, 1, -1}, {4, 1, 0, 0, 1}, {6, 0, 1, 1, 0}, {1, -1, 1, 0, 0}};

        SortedSet<Integer> expected = new TreeSet<>();
        expected.add(3);
        expected.add(5);
        assertEquals(expected, FileInputParser.listMissingElementInInputFile(pieceArray, numberOfElements));

    }


    @Test
    public void listMissingElementInInputFileEmptyList() {

        int numberOfElements = 4;
        int[][] pieceArray = {{1, 0, 0, 1, -1}, {4, 1, 0, 0, 1}, {3, 0, 1, 1, 0}, {2, -1, 1, 0, 0}};

        SortedSet<Integer> expected = new TreeSet<>();

        assertEquals(expected, FileInputParser.listMissingElementInInputFile(pieceArray, numberOfElements));

    }

    @Test
    public void listMissingElementInInputFileEmptyPieceArray() {

        int numberOfElements = 4;
        int[][] pieceArray = null;

        SortedSet<Integer> expected = new TreeSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, FileInputParser.listMissingElementInInputFile(pieceArray, numberOfElements));

    }

    // *************************   Verify isWrongElementFormat

    @Test
    public void isWrongElementFormatNotWrong() {

        List<String> list = new ArrayList<>();
        list.add("2 1 0 0 -1");

        assertFalse(FileInputParser.isWrongElementFormat(list.get(0)));


    }


    @Test
    public void isWrongElementFormat() {

        List<String> list = new ArrayList<>();
        list.add("2 2 0 0 -1");

        assertTrue(FileInputParser.isWrongElementFormat(list.get(0)));


    }

    @Test
    public void isWrongElementFormat1() {

        List<String> list = new ArrayList<>();
        list.add("2 1 0 0 99");

        assertTrue(FileInputParser.isWrongElementFormat(list.get(0)));


    }


    @Test
    public void isWrongElementFormatNotNumber() {

        List<String> list = new ArrayList<>();
        list.add("2 1 0 0 a");

        assertTrue(FileInputParser.isWrongElementFormat(list.get(0)));


    }

    @Test
    public void isWrongElementFormatIgnoreSpaces() {

        List<String> list = new ArrayList<>();
        list.add("2 1 0   0 -1");

        assertFalse(FileInputParser.isWrongElementFormat(list.get(0)));


    }


    // Produce Array For puzzle test


    @Test
    public void oneLineWithWrongID() {

        List<String> list = new ArrayList<>();
        list.add("NumElements=4");
        list.add("2 1 0 0 -1");
        list.add("3 1 0 0 -1");
        list.add("4 1 0 0 -1");
        list.add("77 1 0 0 -1");

        ArrayList<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(2, 1, 0, 0, -1));
        puzzlePieceList.add(new PuzzlePiece(3, 1, 0, 0, -1));
        puzzlePieceList.add(new PuzzlePiece(4, 1, 0, 0, -1));

        ArrayList<int[]> actual =  FileInputParser.produceArrayForPuzzle(list, new ErrorsManager());

        assertNull(actual);

    }

    @Test
    public void oneLineOnlySpaces() {

        List<String> list = new ArrayList<>();
        list.add("NumElements=4");
        list.add("4 1 0 0 -1");
        list.add("    ");

        ArrayList<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(4, 1, 0, 0, -1));

        ArrayList<int[]> actual =  FileInputParser.produceArrayForPuzzle(list, new ErrorsManager());

        assertNull(actual);
    }

    @Test
    public void positiveTestParserValidateIds() {
        List<int[]> inputPuzzlePiecesList = new ArrayList<>();
        inputPuzzlePiecesList.add(new int[]{1});
        inputPuzzlePiecesList.add(new int[]{2});
        inputPuzzlePiecesList.add(new int[]{3});
        inputPuzzlePiecesList.add(new int[]{4});
        Assertions.assertTrue(FileInputParser.validateMissingIds(inputPuzzlePiecesList, new ArrayList<>()), "ID validation passed unexpectedly");
    }


    @Test
    public void testParserValidateIdsWithMissingId() {
        List<int[]> inputPuzzlePiecesList = new ArrayList<>();
        inputPuzzlePiecesList.add(new int[]{1});
        inputPuzzlePiecesList.add(new int[]{2});
        inputPuzzlePiecesList.add(new int[]{4});
        Assertions.assertFalse(FileInputParser.validateMissingIds(inputPuzzlePiecesList, new ArrayList<>()), "ID validation failed unexpectedly");
    }

    @Test
    public void testParserValidateIdsWithUnOrderedIds() {
        List<int[]> inputPuzzlePiecesList = new ArrayList<>();
        inputPuzzlePiecesList.add(new int[]{1});
        inputPuzzlePiecesList.add(new int[]{3});
        inputPuzzlePiecesList.add(new int[]{2});
        inputPuzzlePiecesList.add(new int[]{4});
        Assertions.assertTrue(FileInputParser.validateMissingIds(inputPuzzlePiecesList, new ArrayList<>()), "ID validation failed unexpectedly");
    }


}