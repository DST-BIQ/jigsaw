package com.att.biq.dst.jigsaw.fileUtils;

import com.att.biq.dst.jigsaw.puzzleManager.PuzzlePiece;

import java.util.*;

public class FileInputParser {



    /**
     * get number of elements by realing the first line in the file.
     * if there is an error on the first line, return -1, and report to file.
     *
     * @param list
     * @return int containing number of elements
     */
    public int getNumberOfElements(List<String> list, FileManager fm) {

        String firstLine = list.get(0);
        String[] tempStr = firstLine.split("=");
        tempStr[1] = tempStr[1].replace(" ", "");
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
//TODO STIIL NOT SURE HOW TO Report an Error (direct;y to file or aggreate in an array
    public List<PuzzlePiece> produceArrayForPuzzle(List<String> list, FileManager fm) {
        int indexLines = 0;
        List<String> wrongElementIDs = new ArrayList<>();
        List<Integer> missingElementsIDs = new ArrayList<>();
        ArrayList<int[]> puzzlePieceList = new ArrayList<>();

        for ( String line : list ) {
            int countErrors = 0;
            if (!line.contains("Num"))  // ignore first line
            {
                if (isLineContainsOnlySpaces(line) || isLineEmpty(line)) {
                    countErrors += 1;
                    fm.reportError("lineNumber:  " + indexLines + "  contains only spaces or is empty ");

                }

                if (isLineBeginswithDash(line)) {
                    countErrors += 1;
                    fm.reportError("lineNumber:  " + indexLines + "  begins with dash, ignore ");

                }


                if (!idInRange(list, line, fm)) {
                    countErrors += 1;
                    wrongElementIDs.add(String.valueOf(getElementID(line)));
                    fm.reportError("Puzzle of size " + getNumberOfElements(list, fm) + " cannot have the following IDs:" + wrongElementIDs);
                }

                if (isWrongElementFormat(line)) {
                    countErrors += 1;
                    fm.reportError("Puzzle ID <" + indexLines + "> as wrong data: <" + getPuzzlePieceData(line));


                }

//            Puzzle ID <id> has wrong data: <complete line from file including ID>
                //check if line is valid

                if (countErrors == 0) {

                    int[] puzzlePieceArray = new int[5]; // create new array in list size - max number of lines
                    //parse line to prepare to enter to array
                    String[] tempLine = line.split(",");

                    for ( int i = 0; i <= 4; i++ ) {
                        puzzlePieceArray[i] = Integer.valueOf(tempLine[i].replace(" ",""));
                    }

                    puzzlePieceList.add(puzzlePieceArray);


                }else{
                    fm.writeToFile(fm.getErrorReportList().toString());
                    return null;
                }

            }

            indexLines =+1;
        }

        if (!validateMissingIds(puzzlePieceList, missingElementsIDs)){
            fm.reportError("Puzzle of size " + getNumberOfElements(list, fm) + " is missing the following IDs:" + missingElementsIDs);
            return null;
        }

        return convertPuzzleArray(puzzlePieceList);
    }


    public List<PuzzlePiece> convertPuzzleArray(List<int []> puzzleArray ){
        List<PuzzlePiece> puzzlePiecesList = new ArrayList<>();
        for (int [] puzzlePiece:puzzleArray){
            PuzzlePiece pp = new PuzzlePiece(puzzlePiece[0],puzzlePiece[1],puzzlePiece[2],puzzlePiece[3],puzzlePiece[4]);
            puzzlePiecesList.add(pp);
        }
        return puzzlePiecesList;
    }


    /**
     * get the puzzlePiece part for validation
     *
     * @param line
     * @return string contains the puzzle piece
     */
    public String getPuzzlePieceData(String line) {
        try {
            String[] lineArr = line.split(",");

            String piece = "";
            for ( int i = 1; i < lineArr.length; i++ ) {

                piece = piece + lineArr[i] + ",";

            }
            return piece.substring(0, piece.lastIndexOf(",")).replace(" ", "");
        }
        catch(IndexOutOfBoundsException e){
            return "-1";
        }
    }

    /**
     * is the ID in the range of the number of elements expected??
     *
     * @param list
     * @param line
     * @return true/false
     */
    public boolean idInRange(List<String> list, String line, FileManager fm) {

        int numberOfElements = getNumberOfElements(list, fm);
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
     *
     * @param list
     * @return String
     */

    public String getFileRange(List<String> list, FileManager fm) {
        int numberOfElements = getNumberOfElements(list, fm);
        if (numberOfElements == 1) return "1";
        if (numberOfElements == (-1)) return "N/A";
        return "1-" + numberOfElements;
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
     *
     * @param line
     * @return
     */

    public boolean isLineBeginswithDash(String line) {
        line = line.replace(" ", "");

        if (line.startsWith("#")) {
            return true;
        }

        return false;
    }

    /**
     * go over the final list of valid elements, compare to range, if ID is not on the final list report missing
     *
     * @return set for print the IDs of missing elements
     */
    public SortedSet<Integer> listMissingElementInInputFile(int[][] pieceArray, int numberOfElements) {

        SortedSet<Integer> missingIDs = new TreeSet<>();
        SortedSet<Integer> existingIDs = new TreeSet<>();
        try {
            // insert existing IDs to set
            for ( int j = 0; j < pieceArray.length; j++ ) {
                existingIDs.add(pieceArray[j][0]);
            }

            for ( int i = 1; i <= numberOfElements; i++ ) {
                if (!existingIDs.contains(i)) {
                    missingIDs.add(i);
                }

            }
        } catch (NullPointerException e) {

            for ( int i = 1; i <= numberOfElements; i++ ) {
                missingIDs.add(i);
            }

        }


        return missingIDs;
    }

    //TODO + TESTS

    /**
     * if the line has more than 5 parts (ID + 4 edges) or if the edges are not 1,0,-1 return wront element
     * @param line
     * @return
     */
    public boolean isWrongElementFormat(String line) {


            String[] tempLine;
            String lineWithNoSpaces= line.replace(" ","");
            tempLine = lineWithNoSpaces.split(",");

            if (tempLine.length!=5) {return true;}

            for (int i=1;i<=4;i++){

                if (!(tempLine[i].equals("1")||tempLine[i].equals("-1")||tempLine[i].equals("0") )){
                    return true;
                }

            }



        return false;
    }




    public static boolean validateMissingIds(List<int[]> text, List<Integer> missingIds) {

        List<Integer> ids = new ArrayList<>();

        int numberOfElements = text.size();
        for (int i = 0; i< numberOfElements; i++){
            ids.add(text.get(i)[0]);
        }
        boolean result = true;
        for (int j=1; j<=numberOfElements;j++){
            if (!ids.contains(j)){
                missingIds.add(j);
                result = false;
            }
        }

        return result;
    }
}
