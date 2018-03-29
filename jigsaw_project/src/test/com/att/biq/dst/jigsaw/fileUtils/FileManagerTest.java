package com.att.biq.dst.jigsaw.fileUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileManagerTest {

    /**
     * create temporary file
     */
   static String basePath="C:\\git_clone\\bit_std\\jigsaw\\jigsaw_project\\src\\resources\\";
   static String fileToCreate ="testFile.txt";
   File file;
   FileManager fm;


   @BeforeEach
   public void setUp(){
       Path path = Paths.get(basePath+fileToCreate);
       file = new File (basePath+fileToCreate);
       if (!Files.exists(path)){
//            file = new File (basePath+fileToCreate);
           try {
               file.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       fm = new FileManager();

   }


   @AfterEach
   public void tearDown(){
       Path path = Paths.get(basePath+fileToCreate);
       if (Files.exists(path)) {
           try {
               Files.delete(path);
           } catch (AccessDeniedException e){
               System.out.println("please check accessability rights for this file" + basePath+fileToCreate);
           } catch (IOException e) {
               e.printStackTrace();
           }


       }
   }



    @Test
    public void verifyFileContent(){

        Path path = Paths.get(basePath+"testFileValidFormat.txt");

        try {
            assertEquals(FileManager.readFromFile(path), Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void fileNotFound() {
        Path path = Paths.get(basePath+"NONEXISTINGNAME.txt");
        assertEquals(FileManager.readFromFile(path),null);
    }


    //TODO
    @Test
    public void unableToReadFromFilePermissionDenied() {
        Path path = Paths.get(basePath+"NONEXISTINGNAME.txt");

//        FileManager.readFromFile(path);


    }

    //TODO
    @Test
    public void unableToWriteToFilePermissionDenied() {
//        File file = new File (basePath+fileToCreate);
        file.setReadOnly();


        Throwable exception = assertThrows(IOException.class,        () -> {

        }
    );
        fm.writeToFile("3,1,6,7,8", file);
        assertTrue(exception.getMessage().contains("Access is denied"));

        file.setWritable(true);

    }


    /**
     * write line to file. the file is empty
     */
    @Test
    public void WriteToFileEmptyFile() {
        String lineToCompare = null;
        File file = new File(basePath + fileToCreate);
        fm.writeToFile("3,1,6,7,8", file);

        try(BufferedReader  br = new BufferedReader(new FileReader(file))) {


          do {
              lineToCompare = br.readLine();


          } while (!(br.readLine()==null));
      }
      catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }


        assertEquals("3,1,6,7,8",lineToCompare);

    }

    /**
     * write line to file. the file already has content
     */
    @Test
    public void WriteToFileWithContent() {
        String lineToCompare = null;
        String lastLine=null;
        File file = new File(basePath + fileToCreate);
        try(FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw))
        {
        bw.write("3,1,6,7,8");
            bw.newLine();
        } catch (IOException e1) {
            System.out.println(e1.getMessage());;
        }


        fm.writeToFile("3,1,6,7,7", file);

        try(BufferedReader  br = new BufferedReader(new FileReader(file))) {

            do {
                lastLine =lineToCompare;

            } while ((lineToCompare=br.readLine())!=null);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("3,1,6,7,7",lastLine);

    }

}