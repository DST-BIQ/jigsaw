package com.att.biq.dst.jigsaw.MainPlayer;

import com.att.biq.dst.jigsaw.puzzleManager.PuzzleManager;

public class Main {
    public static void main(String[] args) {

                PuzzleManager puzzleManager = new PuzzleManager(".\\src\\main\\resources\\input\\AdvancedPuzzleTests\\input\\test18.in",".\\src\\main\\resources\\output\\");

                puzzleManager.loadPuzzle();
                puzzleManager.playPuzzle();

            }
}
