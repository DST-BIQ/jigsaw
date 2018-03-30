package com.att.biq.dst.jigsaw.fileUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class FileManager {




    /**
     * Read lines from file
     * return List of strings
     * @param path
     * @return List<String></String>
     */
    public static List<String> readFromFile(Path path) {

        try {
             return readAllLines(path);

        } catch (IOException|NullPointerException ex) {

            System.out.println(ex.getMessage()); //handle an exception here

        }
        finally {
            //TODO close file
        }

return null; // if fails and nothing to return.

    }

    /**
     * Append data to file
     * @param data - string line to insert to file
     *             @param - file - the file to write into
     *
     */

    public void writeToFile(String data, File file){

        try(FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {

            System.out.println("you have any error accessing your file:  "+e.getMessage());

        }

    }
}


