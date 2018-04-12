package com.att.biq.dst.jigsaw.fileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

//TODO javadoc desc
public class FileInputParser {

    private static int numberOfElements;
    /**
     * get number of elements by reading the first line in the file.
     * if there is an error on the first line, return -1, and report to file.
     *
     * @param list
     * @return int containing number of elements
     */
    public static int getNumberOfElements(List<String> list, ErrorsManager errorsManager) {
// TODO verify the array contains 2 elements only, verify syntax
        String firstLine = list.get(0).trim();
        String[] firstLineArr = firstLine.split("\\s*=\\s*");
        try {
//            tempStr[1] = tempStr[1].replace(" ", "");
            if ((firstLineArr[0].equals("NumElements")) && (firstLineArr.length == 2)) {
                try {
                    numberOfElements = Integer.valueOf(firstLineArr[1]);
                    return numberOfElements  ;
                } catch (NumberFormatException e) {
                    errorsManager.addFatalErrorsList("Number of elements does not indicate number");
                    return -1;

                    //TODO consider exception instead of -1
                }
            } else {
                errorsManager.addFatalErrorsList("Either string does not contains the prefix \"NumElements\" or any other error on the first line");
                return -1;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
// TODO error
    return  -1;
        }


    }


    /**
     * verify if line on the list is empty - report error and return indication
     *
     * @param line from input file
     * @return true if line is empty
     */
    public static boolean isLineEmpty(String line) {
        return line.isEmpty();

    }


    /**
     * recieves list of lines from the file, return valid lines for puzzle
     *
     * @param list list of lines from file
     * @return list of puzzle pieces
     */
//TODO STIIL NOT SURE HOW TO Report an Error (direct;y to file or aggreate in an array
    public static ArrayList<int[]> produceArrayForPuzzle(List<String> list, ErrorsManager errorsManager) {
        int indexLines = 0;
        List<String> wrongElementIDs = new ArrayList<>();
        List<Integer> missingElementsIDs = new ArrayList<>();
        ArrayList<int[]> puzzlePieceList = new ArrayList<>();
// TODO move to sseperate method
        for (String line : list) {
            int countErrors = 0;
            if (!line.contains("Num"))  // ignore first line
            {
                if (isLineContainsOnlySpaces(line) || isLineEmpty(line)) {
                    countErrors += 1;
                    errorsManager.addNonFatalErrorsList("lineNumber:  " + indexLines + "  contains only spaces or is empty ");

                }

                if (isLineBeginswithDash(line)) {
                    countErrors += 1;
                    errorsManager.addFatalErrorsList("lineNumber:  " + indexLines + "  begins with dash, ignore ");

                }


                if (!idInRange(list, line, errorsManager)) {
                    countErrors += 1;
                    wrongElementIDs.add(String.valueOf(getElementID(line)));
                    errorsManager.addFatalErrorsList("Puzzle of size " + getNumberOfElements(list, errorsManager) + " cannot have the following IDs:" + wrongElementIDs);
                }

                if (isWrongElementFormat(line)) {
                    countErrors += 1;
                    errorsManager.addFatalErrorsList("Puzzle ID <" + indexLines + "> as wrong data: <" + getPuzzlePieceData(line));


                }

//            Puzzle ID <id> has wrong data: <complete line from file including ID>
                //check if line is valid

                if (countErrors == 0) {

                    int[] puzzlePieceArray = new int[5]; // create new array in list size - max number of lines
                    //parse line to prepare to enter to array
                    String temp = trimRedundantSpacesFromLine(line);
                    String[] tempLine = temp.split(" ");

                    for (int i = 0; i <= 4; i++) {

                        puzzlePieceArray[i] = Integer.valueOf(tempLine[i]);
                    }

                    puzzlePieceList.add(puzzlePieceArray);


                }

            }

            indexLines = +1;
        }

        if (!validateMissingIds(puzzlePieceList, missingElementsIDs)) {
            errorsManager.addFatalErrorsList("Puzzle of size " + getNumberOfElements(list,errorsManager ) + " is missing the following IDs:" + missingElementsIDs);

        }

        return puzzlePieceList;
    }




    /**
     * get the puzzlePiece part for validation
     *
     * @param line from file input
     * @return string contains the puzzle piece
     */
    static String getPuzzlePieceData(String line) {
        try {
            String lineTemp = trimRedundantSpacesFromLine(line);
            String[] lineArr = lineTemp.split(" ");

            String piece = "";
            for (int i = 1; i < lineArr.length; i++) {

                piece = piece + lineArr[i] + " ";

            }
            return piece.substring(0, piece.lastIndexOf(" "));
        } catch (IndexOutOfBoundsException e) {
            return "-1";
        }
    }

    /**
     * is the ID in the range of the number of elements expected??
     *
     * @param list of lines from file
     * @param line line to inspect
     * @return true/false
     */
    static boolean idInRange(List<String> list, String line, ErrorsManager errorsManager) {

        int numberOfElements = getNumberOfElements(list,errorsManager);
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
     * @param line from file
     * @return true/false
     */
    private static int getElementID(String line) {


        return getLinePuzzlePieceID(line);


    }

    /**
     * does this line contains only spaces
     *
     * @param line from file
     * @return true/false
     */
    static boolean isLineContainsOnlySpaces(String line) {
        if (line.length() == line.chars().filter(ch -> ch == ' ').count()) {
            return true;
        }
        return false;
    }


    /**
     * get the file ID range
     *
     * @param list of lines from file
     * @return String
     */

    static String getFileRange(List<String> list, ErrorsManager errorsManager) {
        int numberOfElements = getNumberOfElements(list, errorsManager);

        if (numberOfElements == 1) return "1";
        if (numberOfElements == (-1)) return "N/A";
        return "1-" + numberOfElements;
    }

    /**
     * get line puzzle piece ID
     *
     * @param line from file
     * @return pieceID
     */
    static int getLinePuzzlePieceID(String line) {
        try {
            String tempLine = trimRedundantSpacesFromLine(line);
            String[] lineArr = tempLine.split(" ");
            return Integer.valueOf(lineArr[0]);

        } catch (NumberFormatException e) {
            return -1;
        }

    }

    /**
     * is this line begins with # (should be reported and ignored)
     *
     * @param line from file
     * @return true/false
     */

    static boolean isLineBeginswithDash(String line) {

//        line = line.replace("  ", " ");
        line = trimRedundantSpacesFromLine(line);

        if (line.startsWith("#")) {
            return true;
        }
        return false;
    }

    /**
     * go over the final list of valid elements, compare to range, if ID is not on the final list report missing
     *
     * @param numberOfElements - number of elements suppose to be in the file
     * @param pieceArray       - missing elements in the file
     * @return set for print the IDs of missing elements
     */
    static SortedSet<Integer> listMissingElementInInputFile(int[][] pieceArray, int numberOfElements) {

        SortedSet<Integer> missingIDs = new TreeSet<>();
        SortedSet<Integer> existingIDs = new TreeSet<>();
        try {
            // insert existing IDs to set
            for (int j = 0; j < pieceArray.length; j++) {
                existingIDs.add(pieceArray[j][0]);
            }

            for (int i = 1; i <= numberOfElements; i++) {
                if (!existingIDs.contains(i)) {
                    missingIDs.add(i);
                }

            }
        } catch (NullPointerException e) {

            for (int i = 1; i <= numberOfElements; i++) {
                missingIDs.add(i);
            }

        }

        return missingIDs;
    }


    /**
     * if the line has more than 5 parts (ID + 4 edges) or if the edges are not 1,0,-1 return wront element
     *
     * @param line from file
     * @return true/false
     */
    public static boolean isWrongElementFormat(String line) {
        line = trimRedundantSpacesFromLine(line);
        String[] tempLine = line.split(" ");

        if (tempLine.length != 5) {
            return true;
        }

        for (int i = 1; i <= 4; i++) {

            if (!(tempLine[i].equals("1") || tempLine[i].equals("-1") || tempLine[i].equals("0"))) {
                return true;
            }

        }
        return false;
    }

    /**
     * valids list of missing IDs
     *
     * @param text
     * @param missingIds missing IDs list
     * @return true/false
     */
    public static boolean validateMissingIds(List<int[]> text, List<Integer> missingIds) {

        List<Integer> ids = new ArrayList<>();

        int numberOfElements = text.size();
        for (int i = 0; i < numberOfElements; i++) {
            ids.add(text.get(i)[0]);
        }
        boolean result = true;
        for (int j = 1; j <= numberOfElements; j++) {
            if (!ids.contains(j)) {
                missingIds.add(j);
                result = false;
            }
        }

        return result;
    }




    protected static String trimRedundantSpacesFromLine(String line) {
// todo replace with regex
        String after = line.trim().replaceAll(" +", " ");
        return after;
    }
}
