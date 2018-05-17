package com.att.biq.dst.jigsaw.puzzle.server;

import com.att.biq.dst.jigsaw.puzzle.ErrorsManager;
import com.att.biq.dst.jigsaw.puzzle.client.FileInputParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 *
 * @ author Dorit, Stav, Tal
 * The puzzle class holds the list of the PuzzlePieces after converting from input file
 * and indexing them to treeMap of PuzzlePieceIdentity.
 * the methods of the class is converting the puzzle content to PuzzlePieces and indexing
 */

public class Puzzle {
    private ErrorsManager errorsManager;
    private List<PuzzlePiece> puzzlePieces;
    private Map<PieceShape, ArrayList<PuzzlePieceIdentity>> treeMap = new HashMap();

    private boolean rotate;
    private AtomicBoolean isSolved = new AtomicBoolean(false);


    public Puzzle(ErrorsManager errorsManager, boolean rotate) {
        this.errorsManager = errorsManager;
        this.rotate=rotate;
    }

    /**
     * creates available list of puzzlePieces
     * @param puzzleContent - list of strings, represent the valid file input lines
     * @param puzzlePieceValidators - object that it's purpose to validate the puzzle piece
     * @return Array list of PuzzlePieces
     */
    public List<PuzzlePiece> getPuzzlePiecesArray(FileInputParser fim, List<String> puzzleContent, PuzzlePieceValidators puzzlePieceValidators) {

        ArrayList<int[]> puzzleArray = fim.produceArrayForPuzzle(puzzleContent, errorsManager);
        if (puzzleArray == null) {
            return null;
        }

        puzzlePieces = convertPuzzleArray(puzzleArray);


        if (puzzlePieces == null || !puzzlePieceValidators.validatePuzzle(puzzlePieces, errorsManager)) {

            return null;

        }

        return puzzlePieces;
    }

    public List<PuzzlePiece> getPuzzlePieces() {
        return puzzlePieces;

    }

    /**
     * convert puzzle array from list of integers to list of puzzlePieces
     * @param puzzleArray - puzzle pieces as int
     * @return
     */
    public List<PuzzlePiece> convertPuzzleArray(List<int[]> puzzleArray) {
        puzzlePieces = new ArrayList<>();
        for (int[] puzzlePiece : puzzleArray) {
            PuzzlePiece pp = new PuzzlePiece(puzzlePiece[0], puzzlePiece[1], puzzlePiece[2], puzzlePiece[3], puzzlePiece[4]);
            puzzlePieces.add(pp);
            if (rotate) {
                //TODO here? or dismiss to somewhere else?
//                rotatePiece(pp);
            }
        }
        return puzzlePieces;
    }


    public int getStraightEdgesSum () {
        int totalStraightEdges = 0;
        for (PuzzlePiece pp:puzzlePieces) {
            if (pp.getTop()==0 || pp.getLeft()==0 || pp.getRight()==0 || pp.getBottom()==0){
                totalStraightEdges++;
            }
        }
        return totalStraightEdges;
    }

    public synchronized void setSolved() {
        isSolved.set(true);
    }

    public ErrorsManager getErrorsManager() {
        return errorsManager;
    }

    public boolean isSolved() {
        return isSolved.get();
    }





    /**
     * creates tree structure + related puzzle pieces that match each criteria
     *
     * @param puzzlePieces - list of current puzzle pieces
     */
    public void indexingPuzzlePiecesToTree(List<PuzzlePiece> puzzlePieces, boolean isRotate) {
        PuzzlePieceIdentity ppi;

        for ( PuzzlePiece puzzlePiece : puzzlePieces ) {
            if (!isRotate) {
                ppi = createIdentityToPiece(puzzlePiece, createShapeFromPiece(puzzlePiece));
                putPuzzlePieceIdentityInTreeMap(ppi,puzzlePiece);
            }
            else{
                for (int i=1;i<=3;i++){
                    puzzlePiece.rotate(1);
                    ppi = createIdentityToPiece(puzzlePiece, createShapeFromPiece(puzzlePiece));
                    putPuzzlePieceIdentityInTreeMap(ppi,puzzlePiece);
                }
            }

        }


    }

    private PieceShape createShapeFromPiece(PuzzlePiece puzzlePiece) {

        return new PieceShape(puzzlePiece.getLeft(), puzzlePiece.getTop(), puzzlePiece.getRight(), puzzlePiece.getBottom());
    }

    private void putPuzzlePieceIdentityInTreeMap(PuzzlePieceIdentity ppi, PuzzlePiece puzzlePiece ){

        ArrayList<PuzzlePieceIdentity> puzzleIdentityArray;
        if (!treeMap.containsKey(puzzlePiece.getEdgesFromPiece())) {
            puzzleIdentityArray = new ArrayList<>();
            puzzleIdentityArray.add(ppi);
            treeMap.put(puzzlePiece.getEdgesFromPiece(),puzzleIdentityArray);
        } else {

            treeMap.get(puzzlePiece.getEdgesFromPiece()).add(ppi);

        }
    }

    private PuzzlePieceIdentity createIdentityToPiece(PuzzlePiece puzzlePiece, PieceShape shape) {
        return new PuzzlePieceIdentity(puzzlePiece.getId(),puzzlePiece.getRotation(), shape);
    }

    public Map<PieceShape, ArrayList<PuzzlePieceIdentity>> getTreeMap() {
        return this.treeMap;
    }

    public void  setTreeMap(Map<PieceShape, ArrayList<PuzzlePieceIdentity>> treemap) {
        this.treeMap = treemap;
    }
}
