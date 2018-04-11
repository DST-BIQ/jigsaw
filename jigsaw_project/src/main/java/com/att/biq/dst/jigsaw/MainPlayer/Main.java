package com.att.biq.dst.jigsaw.MainPlayer;

import com.att.biq.dst.jigsaw.puzzleManager.PuzzleManager;

public class Main {
    public static void main(String[] args) {

        PuzzleManager puzzleManager = new PuzzleManager(args[0],args[1]);

         puzzleManager.loadPuzzle();
         puzzleManager.playPuzzle();

    }
}
