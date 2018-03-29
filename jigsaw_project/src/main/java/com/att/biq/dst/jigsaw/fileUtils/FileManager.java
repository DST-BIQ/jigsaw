package com.att.biq.dst.jigsaw.fileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class FileManager {

//   static BufferedReader br;
//   static FileReader fr;


    /**
     * recieves file as parameter, open it for read/write
     *
     * @param file
     * @return
     */
    public void openFile(File file) throws SecurityException {
        file.canRead();


    }

    public static List<String> readFromFile(Path path) {

        try {
             return readAllLines(path);

        } catch (IOException|NullPointerException ex) {

            System.out.println(ex.getMessage()); //handle an exception here

        }
        finally {
            //TODO close file
        }

return null; // nothing to return.

    }
}



//
//        try {
//            fr = new FileReader(file);
//          br  = new BufferedReader(fr);
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//
//        System.out.println(br.readLine());
//        return null;
//    }
//}
