package com.att.biq.dst.jigsaw.puzzle;

import com.att.biq.dst.jigsaw.puzzleUtils.ErrorsManager;
import com.att.biq.dst.jigsaw.puzzleUtils.PuzzleSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PuzzleTest {
    ErrorsManager errorsManager;
    Puzzle puzzle;
    PuzzleSolution puzzleSolution;
    PuzzleSolver puzzleSolver;

    @BeforeEach
    public void setUp() {
        errorsManager = new ErrorsManager();
        puzzle = new Puzzle(errorsManager, false);
        puzzleSolution = new PuzzleSolution(4, 4);
        puzzleSolver = new PuzzleSolver(puzzle, puzzleSolution);

    }


    @Test
    public void treeContainsPieceShape() {


        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(1, 1, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(2, 0, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(3, -1, -1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(4, 1, 0, -1, -1));


        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList,false);
        PieceShape ps = new PieceShape(1, 0, -1, -1);
        assertTrue(puzzle.getTreeMap().containsKey(ps));
        ps = new PieceShape(1, 1, 0, 0);
        assertTrue(puzzle.getTreeMap().containsKey(ps));
        ps = new PieceShape(0, 1, 0, 0);
        assertTrue(puzzle.getTreeMap().containsKey(ps));

        ps = new PieceShape(1, 0, -1, -1);
        assertTrue(puzzle.getTreeMap().containsKey(ps));


    }


    @Test
    public void treeContainsPieceShapeNegative() {

        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(1, 1, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(2, 0, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(3, -1, -1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(4, 1, 0, -1, -1));
        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList,false);
        PieceShape ps = new PieceShape(1, 1, -1, -1);
        assertFalse(puzzle.getTreeMap().containsKey(ps));


    }
//
//
//    @Test
//    public void getSelectedPieceShapeNotEmpty() {
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        puzzlePieceList.add(new PuzzlePiece(4, 1, 1, -1, -1));
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//        PieceShape ps = new PieceShape(1, 1, -1, -1);
//        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);
//
//        assertTrue(!currentPiecesList.isEmpty());
//
//
//    }
//
//
//    @Test
//    public void getSelectedPieceShapeListSize() {
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        puzzlePieceList.add(new PuzzlePiece(4, 1, 1, -1, -1));
//        puzzlePieceList.add(new PuzzlePiece(5, 1, 1, -1, -1));
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//        PieceShape ps = new PieceShape(1, 1, -1, -1);
//        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);
//
//        assertEquals(2, currentPiecesList.size());
//
//
//    }
//
//    @Test
//    public void getSelectedPieceShapeListContent() {
//
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        puzzlePieceList.add(new PuzzlePiece(4, 1, 1, -1, -1));
//        puzzlePieceList.add(new PuzzlePiece(5, 1, 1, -1, -1));
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//        PieceShape ps = new PieceShape(1, 1, -1, -1);
//        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);
//
//        assertEquals(new PuzzlePiece(4, 1, 1, -1, -1), currentPiecesList.get(0));
//
//    }
//
//
//    @Test
//    public void getSelectedPieceShapeListContentNegative() {
//
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        PuzzlePiece pp1 = new PuzzlePiece(4, 1, 1, -1, -1);
//        pp1.setRotation(0);
//        puzzlePieceList.add(pp1);
//        PuzzlePiece pp2 = new PuzzlePiece(5, 1, 1, -1, -1);
//        pp2.setRotation(0);
//        puzzlePieceList.add(pp2);
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//        PieceShape ps = new PieceShape(1, 1, -1, -1);
//        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);
//
//        PuzzlePiece pp3 = new PuzzlePiece(4, 1, 1, -1, -1);
//        pp3.setRotation(180);
//        assertNotEquals(pp3, currentPiecesList.get(0));
//
//    }
//
//
//    @Test
//    public void getMatchListJokerCondition() {
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        PuzzlePiece puzzlePiece = new PuzzlePiece(4, 1, 1, -1, -1);
//        puzzlePieceList.add(puzzlePiece);
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//        List<PuzzlePiece> listOfMatchedPieces = puzzleSolver.getMatch(2, 1, -1, -1);
//        assertTrue(listOfMatchedPieces.get(0).equals(puzzlePiece));
//
//
//    }
//
//
//    @Test
//    public void getMatchListJokerConditionMiddleLines() {
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        PuzzlePiece puzzlePiece = new PuzzlePiece(4, 0, 0, -1, -1);
//        puzzlePieceList.add(puzzlePiece);
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//        List<PuzzlePiece> listOfMatchedPieces = puzzleSolver.getMatch(0, 0, 2, 2);
//        assertTrue(listOfMatchedPieces.get(0).equals(puzzlePiece));
//
//
//    }
//
//    @Test
//    public void getMatchOneListWithTwoPieces() {
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        PuzzlePiece puzzlePiece = new PuzzlePiece(4, 1, 1, -1, -1);
//        PuzzlePiece puzzlePiece1 = new PuzzlePiece(5, 1, 1, -1, -1);
//        puzzlePieceList.add(puzzlePiece);
//        puzzlePieceList.add(puzzlePiece1);
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//
//        List<PuzzlePiece> listOfMatchedPieces = puzzleSolver.getMatch(1, 1, -1, -1);
//        assertEquals(2, listOfMatchedPieces.size());
//        assertTrue(listOfMatchedPieces.contains(puzzlePiece));
//        assertTrue(listOfMatchedPieces.contains(puzzlePiece1));
//
//    }
//
//
//    @Test
//    public void getMatchTwoListsWithTwoPieces() {
//
//
//        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
//        PuzzlePiece puzzlePiece = new PuzzlePiece(4, 1, 1, -1, -1);
//        PuzzlePiece puzzlePiece1 = new PuzzlePiece(5, 1, 2, -1, -1);
//        puzzlePieceList.add(puzzlePiece);
//        puzzlePieceList.add(puzzlePiece1);
//        puzzle.indexingPuzzlePiecesToTree(puzzlePieceList);
//
//        List<PuzzlePiece> listOfMatchedPieces = puzzleSolver.getMatch(1, 1, -1, -1);
//        assertEquals(2, listOfMatchedPieces.size());
//        assertTrue(listOfMatchedPieces.contains(puzzlePiece));
//        assertTrue(listOfMatchedPieces.contains(puzzlePiece1));
//
//    }

}