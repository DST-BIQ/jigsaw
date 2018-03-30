package com.att.biq.dst.jigsaw.fileUtils;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileInputParserTest {

    static String basePath = "C:\\git_clone\\bit_std\\jigsaw\\jigsaw_project\\src\\resources\\";
//    File file;
//    FileManager fm = new FileManager();
    FileInputParser fip = new FileInputParser();
//
//    @BeforeEach
//    public void setUp() {
//
//        fm = new FileManager();
//
//    }

    // *************************   Verify first line Tests
    @Test
    public void verifyCorrectFirstLine() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=4");

        assertEquals(4, fip.getNumberOfElements(list));
    }

    @Test
    public void missingNumnElements() {
//        Path path = Paths.get(basePath + "testFileValidFormat.txt");
        List<String> list = new ArrayList<>();
        list.add("NElements=4");

        assertEquals(-1, fip.getNumberOfElements(list));
    }

    @Test
    public void missingInteger() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=");

        assertEquals(-1, fip.getNumberOfElements(list));
    }


    @Test
    public void notIntegerOnNumberOfElements() {
        List<String> list = new ArrayList<>();
        list.add("NumElements=o");

        assertEquals(-1, fip.getNumberOfElements(list));
    }

    @Test
    public void spacesBeforeNumElements() {
        List<String> list = new ArrayList<>();
        list.add("   NumElements=77");

        assertEquals(77, fip.getNumberOfElements(list));
    }


    @Test
    public void spacesAfterNumElements() {
        List<String> list = new ArrayList<>();
        list.add("NumElements    =77");

        assertEquals(77, fip.getNumberOfElements(list));
    }

    @Test
    public void firstLineParseError() {
        Path path = Paths.get(basePath + "testFileValidFormat.txt");
        List<String> list = FileManager.readFromFile(path);
        assertEquals(fip.getNumberOfElements(list), 4);
    }

    // *************************   Verify line contains only spaces
    @Test
    public void lineContainsOnlySpaces() {

        List<String> list = new ArrayList<>();
        list.add(" ");

        assertTrue(fip.isLineContainsOnlySpaces(list.get(0)));

    }

    @Test
    public void lineContainsAlsoSpaces() {

        List<String> list = new ArrayList<>();
        list.add("1, 0,-1 ,9");

        assertFalse(fip.isLineContainsOnlySpaces(list.get(0)));

    }

    // *************************   Verify line is empty
    @Test
    public void lineIsEmptyNegative(){

        List<String> list = new ArrayList<>();
        list.add("34, 1, 0,-1 ,9");

        assertFalse(fip.isLineEmpty(list.get(0)));


    }

    @Test
    public void lineIsEmpty(){

        List<String> list = new ArrayList<>();
        list.add("");

        assertTrue(fip.isLineEmpty(list.get(0)));

    }


    @Test
    public void lineIsEmptyOneSpace(){

        List<String> list = new ArrayList<>();
        list.add(" ");

        assertFalse(fip.isLineEmpty(list.get(0)));

    }


    // *************************   Verify line wrongIDRange


    @Test
    public void IDInRange(){
        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        list.add("34, 1, 0,-1 ,1,1");
        list.add("24, 1, 0,-1 ,1,1");
        list.add("100, 1, 0,-1 ,1,1");
        list.add("101, 1, 0,-1 ,1,1");
        list.add("109, 1, 0,-1 ,1,1");

assertTrue(fip.wrongIDRange(list,list.get(1)));
        assertTrue(fip.wrongIDRange(list,list.get(2)));
        assertTrue(fip.wrongIDRange(list,list.get(3)));
        assertFalse(fip.wrongIDRange(list,list.get(4)));
        assertFalse(fip.wrongIDRange(list,list.get(5)));


    }

// *************************   Verify getLinePuzzlePieceID
@Test

    public void linePuzzlePieceIDValid(){
    List<String> list = new ArrayList<>();
    list.add("NumElements=100");
    list.add("34, 1, 0,-1 ,1,1");
    assertEquals(34,fip.getLinePuzzlePieceID(list.get(1)));
}


    @Test

    public void linePuzzlePieceIDNotInteger(){

        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        list.add("aa, 1, 0,-1 ,1,1");
        assertEquals(-1,fip.getLinePuzzlePieceID(list.get(1)));
    }

// *************************   Verify getLinePuzzlePieceID
//getPuzzlePieceData(String line)

    @Test
    public void getValidPuzzlePiece(){

        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        list.add("5,0,-1,1,1");
        assertEquals("0,-1,1,1",fip.getPuzzlePieceData(list.get(1)));
    }


    @Test
    public void getValidPuzzlePieceWithSpaces(){

        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        list.add("5, 0,-1 ,1,1");
        assertEquals("0,-1,1,1",fip.getPuzzlePieceData(list.get(1)));
    }

// *************************   Verify getFileRange
@Test
    public void getFileRangeValid(){
        List<String> list = new ArrayList<>();
        list.add("NumElements=100");
        assertEquals("1-100",fip.getFileRange(list));
    }


    @Test
    public void getFileRangeOneRange(){
        List<String> list = new ArrayList<>();
        list.add("NumElements=1");
        assertEquals("1",fip.getFileRange(list));
    }


    @Test
    public void getFileRangeNonValid(){
        List<String> list = new ArrayList<>();
        list.add("NumElements=t");
        assertEquals("N/A",fip.getFileRange(list));
    }

    // *************************   Verify isLineBeginswithDash
//    private boolean isLineBeginswithDash(String line) {
//        line.replace(" ","");
//        if(line.startsWith("#")){
//            return true;
//        }
//
//        return false;
//    }

    @Test
    public void lineBeginsWithDash(){
        List<String> list = new ArrayList<>();
        list.add("#NumElements=t");
        assertTrue(fip.isLineBeginswithDash(list.get(0)));
        list.add("#5, 0,-1 ,1,1");
        assertTrue(fip.isLineBeginswithDash(list.get(1)));
    }

    @Test
    public void lineBeginsWithDash_ignoreSpaces(){

        List<String> list = new ArrayList<>();
        list.add("   #NumElements=t");
        assertTrue(fip.isLineBeginswithDash(list.get(0)));
    }



    @Test
    public void lineBeginsWithNoDash(){

        List<String> list = new ArrayList<>();
        list.add("NumElements=t");
        assertFalse(fip.isLineBeginswithDash(list.get(0)));
    }

    @Test
    public void lineBeginsWithNoDashWithSpaces(){

        List<String> list = new ArrayList<>();
        list.add("  NumElements=t");
        assertFalse(fip.isLineBeginswithDash(list.get(0)));
    }

}

