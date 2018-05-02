package com.att.biq.dst.jigsaw.puzzle;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {


    @Test
    public void treeContainsPieceShape() {
        Puzzle puzzle = new Puzzle();
        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(1, 1, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(2, 0, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(3, -1, -1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(4, 1, 0, -1, -1));
        puzzle.addNodesToTreeStructure(puzzlePieceList);
        PieceShape ps = new PieceShape(1,0,-1,-1);
        assertTrue(puzzle.getTreeMap().containsKey(ps));
        ps = new PieceShape(1,1,0,0);
        assertTrue(puzzle.getTreeMap().containsKey(ps));
        ps = new PieceShape(0,1,0,0);
        assertTrue(puzzle.getTreeMap().containsKey(ps));

        ps = new PieceShape(1,0,-1,-1);
        assertTrue(puzzle.getTreeMap().containsKey(ps));


    }


    @Test
    public void treeContainsPieceShapeNegative() {
        Puzzle puzzle = new Puzzle();
        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(1, 1, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(2, 0, 1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(3, -1, -1, 0, 0));
        puzzlePieceList.add(new PuzzlePiece(4, 1, 0, -1, -1));
        puzzle.addNodesToTreeStructure(puzzlePieceList);
        PieceShape ps = new PieceShape(1,1,-1,-1);
        assertFalse(puzzle.getTreeMap().containsKey(ps));



    }


    @Test
    public void getSelectedPieceShapeNotEmpty(){
        Puzzle puzzle = new Puzzle();
        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(4, 1,1,-1,-1));
        puzzle.addNodesToTreeStructure(puzzlePieceList);
        PieceShape ps = new PieceShape(1,1,-1,-1);
        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);

        assertTrue(!currentPiecesList.isEmpty());


    }


    @Test
    public void getSelectedPieceShapeListSize(){
        Puzzle puzzle = new Puzzle();
        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(4, 1,1,-1,-1));
        puzzlePieceList.add(new PuzzlePiece(5, 1,1,-1,-1));
        puzzle.addNodesToTreeStructure(puzzlePieceList);
        PieceShape ps = new PieceShape(1,1,-1,-1);
        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);

        assertEquals(2,currentPiecesList.size());


    }

    @Test
    public void getSelectedPieceShapeListContent(){

        Puzzle puzzle = new Puzzle();
        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(4, 1,1,-1,-1));
        puzzlePieceList.add(new PuzzlePiece(5, 1,1,-1,-1));
        puzzle.addNodesToTreeStructure(puzzlePieceList);
        PieceShape ps = new PieceShape(1,1,-1,-1);
        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);

        assertEquals(new PuzzlePiece(4, 1,1,-1,-1),currentPiecesList.get(0));

    }


    @Test
    public void getSelectedPieceShapeListContentNegative(){

        Puzzle puzzle = new Puzzle();
        List<PuzzlePiece> puzzlePieceList = new ArrayList<>();
        puzzlePieceList.add(new PuzzlePiece(4, 1,1,-1,-1));
        puzzlePieceList.add(new PuzzlePiece(5, 1,1,-1,-1));
        puzzle.addNodesToTreeStructure(puzzlePieceList);
        PieceShape ps = new PieceShape(1,1,-1,-1);
        ArrayList<PuzzlePiece> currentPiecesList = puzzle.getTreeMap().get(ps);

        assertNotEquals(new PuzzlePiece(4, 2,1,-1,-1),currentPiecesList.get(0));

    }

}