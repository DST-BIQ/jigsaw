package com.att.biq.dst.jigsaw.fileUtils;

import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileManagerTest {

    TemporaryFolder temp;
    File file;
    FileReader    fr;
    BufferedReader   br;
    /**
     * create temporary file
     */


//    @Before
//    public void SetUp() {
//
//        Path path = Paths.get("./src/resources/testFileValidFormat.txt");
//
//
//
//    }



    @Test
    public void verifyFileContent(){

        Path path = Paths.get("./src/resources/testFileValidFormat.txt");

        try {
            assertEquals(FileManager.readFromFile(path), Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void verifyNoContentRead() {
        Path path = Paths.get("./src/resources/testFileValidFormat.txt");
        FileManager.readFromFile(path);
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            throw new IllegalArgumentException("your file is empty");
        });
        assertEquals("a message", exception.getMessage());

//        assertEquals(FileManager.readFromFile(file), br.lines());



    }

    @Test
    public void fileNotFound() {
        Throwable exception = assertThrows(NoSuchFileException.class, () -> {
            throw new NoSuchFileException("[There is no file as you specified, please check again]");
        });

        assertEquals("[There is no file as you specified, please check again]", exception.getMessage());

//        assertEquals(FileManager.readFromFile(file), br.lines());



    }

    @Test
    public void openFileNotExists() {

    }



    @Test
    public void unableToReadFromFilePermissionDenied() {

    }

    @Test
    public void unableToWriteToFilePermissionDenied() {

    }

    @Test
    public void WriteToFile() {

    }


}