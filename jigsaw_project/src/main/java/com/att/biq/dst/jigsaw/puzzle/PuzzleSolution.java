package com.att.biq.dst.jigsaw.puzzle;

public class PuzzleSolution {


    private PuzzlePiece[][] solution;
    private int size;
    private int rows;
    private int columns;
    private int curRow = 0;
    private int curCol = 0;

    public PuzzleSolution(int rows, int columns) {
        size = rows * columns;
        this.rows = rows;
        this.columns = columns;
        solution = new PuzzlePiece[rows][columns];

    }


    public PuzzlePiece[][] getSolution() {
        return solution;
    }


    public boolean contains(PuzzlePiece puzzlePiece) {

        for ( int row = 0; row < solution.length; row++ ) {
            for ( int column = 0; column <= solution[row].length; column++ ) {
                if (solution[row][column].equals(puzzlePiece)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public boolean isValid() {

        if (hasNullPieces()) {
            return false;
        }

        if (!verifyRows()) {
            return false;
        }
        if (!verifyColumns()) {
            return false;
        }
        return true;
    }

    private boolean hasNullPieces() {
        for ( int i = 0; i < solution.length; i++ ) {
            for ( int j = 0; j < solution[i].length; j++ ) {
                if (solution[i][j] == null) {
                    return true;
                }
            }
        }
        return false;
    }



    public void insertPiece(PuzzlePiece piece) {
        solution[curRow][curCol] = piece;
        moveSolutionForward();
    }


    public void removePiece() {
        solution[curRow][curCol] = null;
        moveSolutionBackward();
    }


    private void moveSolutionBackward() {
        if (curCol == 0 && !(curRow == 0)) {
            curRow--;
            curCol = columns - 1;
        } else {
            curCol--;
        }
    }


    private void moveSolutionForward() {
        if (curCol == columns - 1) {
            if (curRow != rows - 1) {
                curRow++;
                curCol = 0;
            } else {
                return;
            }
        } else {
            curCol++;
        }
    }


    private boolean verifyColumns() {
        for ( int column = 0; column < columns; column++ ) {
            for ( int row = 0; row < rows; row++ ) {
                if (row == 0) {
                    if (solution[row][column].getTop() != 0) {
                        return false;
                    }
                    if (row != rows - 1 && solution[row][column].getBottom() + solution[row + 1][column].getTop() != 0) {
                        return false;
                    }
                } else if (row == rows - 1) {
                    if (solution[row][column].getBottom() != 0) {
                        return false;
                    }
                    if (solution[row][column].getTop() + solution[row - 1][column].getBottom() != 0) {
                        return false;
                    }
                } else {
                    if (solution[row][column].getTop() + solution[row - 1][column].getBottom() != 0 ||
                            solution[row][column].getBottom() + solution[row + 1][column].getTop() != 0) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private boolean verifyRows() {
        for ( int row = 0; row < rows; row++ ) {
            for ( int column = 0; column < columns; column++ ) {
                if (column == 0) {
                    if (solution[row][column].getLeft() != 0) {
                        return false;
                    }
                    if (column != columns - 1 && solution[row][column].getRight() + solution[row][column + 1].getLeft() != 0) {
                        return false;
                    }
                } else if (column == columns - 1) {
                    if (solution[row][column].getRight() != 0) {
                        return false;
                    }
                    if (solution[row][column].getLeft() + solution[row][column - 1].getRight() != 0) {
                        return false;
                    }
                } else {
                    if (solution[row][column].getLeft() + solution[row][column - 1].getRight() != 0 ||
                            solution[row][column].getRight() + solution[row][column + 1].getLeft() != 0) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getCurRow() {
        return curRow;
    }

    public int getCurCol() {
        return curCol;
    }

    public void setSolution(PuzzlePiece[][] solution) {
        this.solution = solution;
    }

    public PuzzlePiece getFormerPiece() {
        if (curCol == 0 && curRow > 0) {
            return solution[curRow - 1][columns - 1];
        } else if (curCol == 0 && curRow == 0) {
            return null;
        } else return solution[curRow][curCol - 1];

    }

    public PuzzlePiece getAbovePiece() {
        if (curRow == 0) {
            return null;
        }
        return solution[curRow - 1][curCol];
    }

    public void setCurRow(int curRow) {
        this.curRow = curRow;
    }

    public void setCurCol(int curCol) {
        this.curCol = curCol;
    }
}
