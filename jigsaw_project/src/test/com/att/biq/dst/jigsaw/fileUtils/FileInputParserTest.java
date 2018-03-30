package com.att.biq.dst.jigsaw.fileUtils;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileInputParserTest {

    static String basePath = "C:\\git_clone\\bit_std\\jigsaw\\jigsaw_project\\src\\resources\\";
    File file;
    FileManager fm;

    @BeforeEach
    public void setUp() {

        fm = new FileManager();

    }

    @Test
    public void verifyCorrectFirstLine(){
        Path path = Paths.get(basePath + "testFileValidFormat.txt");
        List<String> list = FileManager.readFromFile(path);
        assertEquals(FileInputParser.getNumberOfElements(list),7);
    }


    @Test
    public void firstLineParseError(){
        Path path = Paths.get(basePath + "testFileValidFormat.txt");
        List<String> list = FileManager.readFromFile(path);
        assertEquals(FileInputParser.getNumberOfElements(list),-1);
    }

    @Test
    public void lineContainsOnlySpaces(){

//        Throwable exception = assertThrows(FileParsingError.class,() ->
//
//            );
//        }
//
//    assertEquals(FileInputParser.isLineContainsOnlySpaces(),-1) {

    }

}

