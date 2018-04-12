package com.att.biq.dst.jigsaw.MainPlayer;

import com.att.biq.dst.jigsaw.puzzleManager.PuzzleManager;

public class Main {
    public static void main(String[] args) {

                PuzzleManager puzzleManager = new PuzzleManager("C:\\BIQ\\jigsaw\\jigsaw\\jigsaw_project\\src\\main\\resources\\input\\AdvancedPuzzleTests\\input\\test6.in","c:\\temp\\test12343"+"_"+System.currentTimeMillis()+".txt");

                puzzleManager.loadPuzzle();
                puzzleManager.playPuzzle();

            }


}
