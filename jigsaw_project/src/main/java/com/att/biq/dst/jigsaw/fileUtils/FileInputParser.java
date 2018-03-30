package com.att.biq.dst.jigsaw.fileUtils;

import java.util.ArrayList;
import java.util.List;

public class FileInputParser {

    FileManager fm = new FileManager();

    /**
     * get number of elements by realing the first line in the file.
     * if there is an error on the first line, return -1, and report to file.
     *
     * @param list
     * @return int containing number of elements
     */
    public int getNumberOfElements(List<String> list) {

        String firstLine = list.get(0);
        String[] tempStr = firstLine.split("=");
        if ((tempStr[0].contains("NumElements") || tempStr[0].contains(" ")) && (tempStr.length == 2)) {
            try {
                return Integer.valueOf(tempStr[1]);
            } catch (NumberFormatException e) {
                fm.reportError("Number of elements does not indicate number");
                return -1;
            }
        } else {
            fm.reportError("Either string does not contains the prefix \"NumElements\" or any other error on the first line");
            return -1;
        }

    }


    /**
     * verify if line on the list is empty - report error and return indication
     *
     * @param line
     * @return
     */
    public boolean isLineEmpty(String line) {

        if (line.isEmpty()) {

            return true;
        } else return false;
    }


    /**
     * recieves list of lines from the file, return valid lines for puzzle
     *
     * @param list
     * @return
     */
//TODO TESTS
    public int[][] produceArrayForPuzzle(List<String> list) {
        int indexLines = 0;
        List<String> wrongElementIDs = new ArrayList<>();
        List<String> missingElementsIDs = new ArrayList<>();

        for ( String line : list ) {
            int countErrors = 0;
            if (!wrongIDRange(list, line)) {
                countErrors += 1;
                fm.reportError("id:  " + getLinePuzzlePieceID(line) + "  in not in the range:  " + getFileRange(list));
            }

            if (isLineContainsOnlySpaces(line) || isLineEmpty(line)) {
                countErrors += 1;
                //TODO should be ignored, should we report it?
                fm.reportError("lineNumber:  " + indexLines + "  contains only spaces or is empty ");

            }

            if (isLineBeginswithDash(line)) {
                countErrors += 1;
                fm.reportError("lineNumber:  " + indexLines + "  begins with dash, ignore ");

            }
            if (isMissingElementInInputFile()) {
                countErrors += 1;
                missingElementsIDs.add(String.valueOf(getElementID(line)));


            }


            if (isMissingElementInInputFile()) {
                countErrors += 1;
                fm.reportError("Missing puzzle element(s) with the following IDs:  " + "");

            }

            if (wrongIDRange(list,  line)) {
                countErrors += 1;
                wrongElementIDs.add(String.valueOf(getElementID(line)));

            }

            if (isWrongElementFormat()) {
                countErrors += 1;
                fm.reportError("Puzzle ID <" + indexLines + "> as wrong data: <" + getPuzzlePieceData(line));


            }

//            Puzzle ID <id> has wrong data: <complete line from file including ID>
            //check if line is valid

            if (countErrors == 0) {
                //add to intArray
            }

            indexLines = +1;
        }

        fm.reportError("Puzzle of size " + getNumberOfElements(list) + " cannot have the following IDs:" + wrongElementIDs);
      //TODO sort before report
        fm.reportError("Puzzle of size " + getNumberOfElements(list) + " cannot have the following IDs:" + missingElementsIDs);

        return new int[][]{{0, 0, 1, -1}, {1, 0, 0, 1}, {0, 1, 1, 0}, {-1, 1, 0, 0}};
    }


    /**
     * get the puzzlePiece part for validation
     *
     * @param line
     * @return string contains the puzzle piece
     */
    public String getPuzzlePieceData(String line) {
        String[] lineArr = line.split(",");

        String piece = "";
        for ( int i = 1; i < lineArr.length; i++ ) {

            piece = piece + lineArr[i] + ",";

        }
        return piece.substring(0, piece.lastIndexOf(",")).replace(" ", "");

    }

    /**
     * is the ID in the range of the number of elements expected??
     *
     * @param list
     * @param line
     * @return true/false
     */
    public boolean wrongIDRange(List<String> list, String line) {

        int numberOfElements = getNumberOfElements(list);
        int puzzlePieceID = getLinePuzzlePieceID(line);
        if (puzzlePieceID <= numberOfElements) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Return rhe ID as int to create a number
     *
     * @param line
     * @return true/false
     */
    public int getElementID(String line) {


        return getLinePuzzlePieceID(line);


    }

    /**
     * does this line contains only spaces
     *
     * @param line
     * @return
     */
    public boolean isLineContainsOnlySpaces(String line) {
        if (line.length() == line.chars().filter(ch -> ch == ' ').count()) {
            return true;
        }
        return false;
    }






    /**
     * get the file ID range
     * @param list
     * @return String
     */

    public String getFileRange(List<String> list) {
        if (getNumberOfElements(list)==1) return "1";
        if (getNumberOfElements(list)==(-1)) return "N/A";
        return "1-" + getNumberOfElements(list);
    }

    /**
     * get line puzzle piece ID
     *
     * @param line
     * @return pieceID
     */
     public int getLinePuzzlePieceID(String line) {
        try {
            String[] lineArr = line.split(",");
            return Integer.valueOf(lineArr[0]);

        } catch (NumberFormatException e) {
            return -1;
        }

    }

    /**
     * is this line begins with # (should be reported and ignored)
     * @param line
     * @return
     */

    public boolean isLineBeginswithDash(String line) {
        line = line.replace(" ","");

         if(line.startsWith("#")){
             return true;
         }

        return false;
    }


    //TODO + TESTS
    private boolean isMissingElementInInputFile() {

        return true;
    }

    //TODO + TESTS
    private boolean isWrongElementFormat() {

        return true;
    }
}
