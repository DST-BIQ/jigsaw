package com.att.biq.dst.jigsaw.PuzzleUtils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadsManager {

    private  ThreadPoolExecutor threadPoolExecutor;


    public ThreadsManager(int numOfThreads){
        threadPoolExecutor = new ThreadPoolExecutor(numOfThreads,numOfThreads,1, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    }

    public  ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
