package com.att.biq.dst.jigsaw.fileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
//TODO javadoc desc
public class FileManager {


    private   File outputFile;
    FileWriter fw;
    BufferedWriter bw;

    private ArrayList<String> errorReportList = new ArrayList<>();

    public FileManager(String filePath){
        outputFile = new File(filePath + "output_" +System.currentTimeMillis()+ ".txt");
    }

    public FileManager(){
        outputFile = new File("c:/output_"+ System.currentTimeMillis()+".txt");
    }

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

    public void writeToFile(String data){


        try(FileWriter fw = new FileWriter(outputFile, true);
            BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {

            System.out.println("you have any error accessing your file:  "+e.getMessage());

        }

    }

    //Adds to an arrayList a list of errors
    public void reportError(String s) {

        errorReportList.add(s);

    }

    public ArrayList<String> getErrorReportList() {
        return errorReportList;
    }
}


