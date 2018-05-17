package com.att.biq.dst.jigsaw.puzzle;

/**
 * @ author Tal
 * creates demo puzzles
 */

import com.att.biq.dst.jigsaw.puzzle.server.PuzzlePiece;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Generator {
    private int rows;
    private int cols;
    private String dir;
    private PuzzlePiece[][] board;
    List<PuzzlePiece> list = new ArrayList<>();

    public Generator(int rows, int cols, String dir) {
        this.rows = rows;
        this.cols = cols;
        this.dir = dir;
        this.board = new PuzzlePiece[rows][cols];
        prepareList();
        board = generatePuzzle();
        printToFile();
    }

    private void prepareList() {

        List<Integer> ids = IntStream.rangeClosed(1, rows * cols)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(ids);
        for (int id : ids) {
            list.add(new PuzzlePiece(id, createPuzzleShape()[0],createPuzzleShape()[1],createPuzzleShape()[2],createPuzzleShape()[3]));
        }

    }

    private int[] createPuzzleShape() {
        //populate the array with random values
        int[] edges = new int[4];
        for (int i = 0; i < 4; i++) {
            Random randomNo = new Random();
            int num = randomNo.nextInt(3);
            if (num == 2) {
                num = -1;
            }
            edges[i] = num;
        }
        return edges;
    }

    public PuzzlePiece[][] generatePuzzle() {
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                PuzzlePiece current = list.get(index);

                if (i == 0) {
                    current.setTop(0);

                }
                if (i == rows - 1) {
                    current.setBottom(0);
                }
                if (j == 0) {
                    current.setLeft(0);
                }
                if (j == cols - 1) {
                    current.setRight(0);
                }
                if (j >= 1) {
                    current.setLeft(0 - board[i][j - 1].getRight());
                }
                if (i >= 1) {
                    current.setTop(0 - board[i - 1][j].getBottom());
                }
//                current.setShape();
                board[i][j] = current;
                index++;
            }

        }
        return board;
    }
    private void printToFile() {

        try {
            FileWriter fw = new FileWriter(dir + "/puzzle" + rows * cols + "Pieces.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.toString());
//            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NumElements = " + rows*cols + "\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                PuzzlePiece p = board[i][j];
                sb.append(p.getId() +" " +p.getLeft()+" " +p.getTop()+" "+ p.getRight()+" "+ p.getBottom()+"\n");
            }
        }
        return sb.toString();
    }
}

