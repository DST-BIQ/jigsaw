package com.att.biq.dst.jigsaw.puzzle.server;

import com.att.biq.dst.jigsaw.puzzle.ErrorsManager;
import com.att.biq.dst.jigsaw.puzzle.client.FileInputParser;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PuzzleTest {



    @Test
    public void convertJsonToPuzzlePieceTest(){
        Puzzle puzzle= new Puzzle(new ErrorsManager(),true);
        FileInputParser fip = new FileInputParser();
        ArrayList<int[]> pieceList = new ArrayList<>();
        int[] piece = {1, 1, 1, 1, 1};
        int[] piece1 = {2, 1, 0, 0, -1};

        pieceList.add(piece);
        pieceList.add(piece1);

        fip.setPuzzlePieceList(pieceList);
        JsonObject object = fip.createJsonObjectFromPuzzlePieceList(true);
       List<PuzzlePiece> puzzlePieces = puzzle.convertJsonToPuzzlePiece(object);
       assertNotNull(puzzlePieces);
    }
}