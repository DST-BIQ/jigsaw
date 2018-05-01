package com.att.biq.dst.jigsaw.puzzle;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

                PuzzleManager puzzleManager = new PuzzleManager(args[0],args[1]);
                puzzleManager.loadPuzzle();
                puzzleManager.playPuzzle();

            }
}
