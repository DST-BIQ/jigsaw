package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.PuzzleUtils.ThreadsManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ThreadsManager threadsManager;
        PuzzleManager puzzleManager = new PuzzleManager(args[0],args[1]);
                if (args.length>=4 &&args[3]!=null){
                    threadsManager = new ThreadsManager(Integer.valueOf(args[3]));
                }else
                    {threadsManager = new ThreadsManager(4);}
                puzzleManager.loadPuzzle();
                puzzleManager.playPuzzle(threadsManager);

            }
}
