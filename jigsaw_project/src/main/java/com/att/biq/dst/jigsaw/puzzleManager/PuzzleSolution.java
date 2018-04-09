package com.att.biq.dst.jigsaw.puzzleManager;

public class PuzzleSolution {


    private PuzzlePiece[][] solution;
    private int size;
    private int rows;
    private int columns;

    public PuzzleSolution(int rows, int columns){
        size = rows*columns;
        this.rows=rows;
        this.columns=columns;
        solution = new PuzzlePiece[rows-1][columns-1];
    }


    public PuzzlePiece[][] getSolution() {
        return solution;
    }


    public boolean contains(PuzzlePiece puzzlePiece) {

        for (int row = 0; row< solution.length; row++){
            for (int column = 0; column<= solution[row].length; column++){
                if (solution[row][column].equals(puzzlePiece)){
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public boolean isValid(){

        if (!verifyRows()){
            return false;
        }
        if (!verifyColumns()){
            return false;
        }
        return true;
    }

    private boolean verifyColumns() {
        for (int column=0;column<columns;column++){
            for (int row = 0;row<rows;row++){
                if (row==0){
                    if (solution[row][column].getTop()!=0){return false;}
                    if (solution[row][column].getBottom()+solution[row+1][column].getTop()!=0){return false;}
                }else if (row==rows-1){
                    if (solution[row][column].getBottom()!=0){return false;}
                    if (solution[row][column].getTop()+solution[row-1][column].getBottom()!=0){return false;}
                }else {
                    if (solution[row][column].getTop()+solution[row-1][column].getBottom()!=0 ||
                            solution[row][column].getBottom()+solution[row+1][column].getTop()!=0){return false;}
                }

            }
        }
        return true;
    }

    private boolean verifyRows() {
        for (int row=0;row<rows;row++){
            for (int column = 0;column<columns;column++){
                if (column==0){
                    if (solution[row][column].getLeft()!=0){return false;}
                    if (solution[row][column].getRight()+solution[row][column+1].getLeft()!=0){return false;}
                }else if (column==columns-1){
                    if (solution[row][column].getRight()!=0){return false;}
                    if (solution[row][column].getLeft()+solution[row][column-1].getRight()!=0){return false;}
                }else {
                    if (solution[row][column].getLeft()+solution[row][column-1].getRight()!=0 ||
                            solution[row][column].getRight()+solution[row][column+1].getLeft()!=0){return false;}
                }

            }
        }
        return true;
    }
}
