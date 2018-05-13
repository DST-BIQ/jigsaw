package com.att.biq.dst.jigsaw.puzzle;

// TODO handle wrong input file in produce array
/**
 * author: dorit
 * main runner of the puzzle application
 * in order to run the application put the following arguments on the command line:
 *
 *
 *  hasRotate - if the parameter exists  - rotation is available, else it won't be calculated using rotation.
 *  numThreads - Number of threads to use. in case this parameter is not provided number will be 4.
 *  inputFilePath - input file location e.g. c:\temp\input.txt (this  parameter is mandatory)
 *  outputFilePath - output file  e.g. c:\temp\myoutput.txt (this parameter is mandatory)
 *
 * there is no importance to the order of the parameters.
 *
 * usage example on the main program arguments:
 * basic:                -inputFilePath ./src/main/resources/input/SimplePuzzleTests/input/test1.in -outputFilePath C:\temp\puzzleOutput
 * including rotation:   -hasRotate -inputFilePath ./src/main/resources/input/SimplePuzzleTests/input/test1.in -outputFilePath C:\temp\puzzleOutput
 *

 */
public class Main {
    public static void main(String[] args) {

        PuzzleManager puzzleManager = new PuzzleManager(args);
        puzzleManager.loadPuzzle();
        puzzleManager.playPuzzle();

    }
}
