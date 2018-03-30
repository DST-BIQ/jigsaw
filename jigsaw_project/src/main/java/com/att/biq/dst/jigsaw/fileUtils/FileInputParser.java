package com.att.biq.dst.jigsaw.fileUtils;

import java.util.List;

public class FileInputParser {


    /**
     * get number of elements by realing the first line in the file.
     * if there is an error on the first line, return -1, and report to file.
     *
     * @param list
     * @return int containing number of elements
     */
    public static int getNumberOfElements(List<String> list) {

        String firstLine = list.get(0);
//if (firstLine.startsWith("NumElements="))

        String[] tempStr = firstLine.split("=");
        if ((tempStr[0].contains("NumElements") && tempStr[0].contains(" '")) && (tempStr.length == 2)) {
            try {
                return Integer.valueOf(tempStr[1]);
            } catch (NumberFormatException e) {
                reportError("Number of elements does not indicate number");
                return -1;
            }
        } else {
            reportError("Either string does not contains the prefix \"NumElements\" or any other error on the first line");
            return -1;
        }

    }

    private static void reportError(String s) {
    }

    public static boolean isLineEmpty() {


        return true;
    }


    public static boolean verifyLine() {
        if (!isLineEmpty() || !isLineContainsOnlySpaces() || !isIDInRange()) {
            return true;
        }

        return false;
    }

    private static boolean isIDInRange() {
        return true;
    }

    private static boolean isLineContainsOnlySpaces() {

        return true;
    }

    private static boolean isLineBeginswithDash() {
        return true;
    }
}
