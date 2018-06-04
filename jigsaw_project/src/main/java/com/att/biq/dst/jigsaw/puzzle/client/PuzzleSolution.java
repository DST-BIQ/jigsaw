package com.att.biq.dst.jigsaw.puzzle.client;

import com.att.biq.dst.jigsaw.puzzle.server.PuzzlePieceIdentity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**author: dorit
 * this class creates the format that suites the json object for sending to server.
 */
public class PuzzleSolution {
    Solution solution;

    public PuzzleSolution(int rows, int cols, PuzzlePieceIdentity[][] winnerSolution, boolean rotate){

        this.solution=new Solution(rows,cols,winnerSolution,rotate);
    }




    public class Solution {

        int rows;
        int cols;
        String solutionPieces;

        public Solution(int rows, int cols, PuzzlePieceIdentity[][] winnerSoltion, boolean rotate) {
            this.cols = cols;
            this.rows = rows;
             SolutionPieces solutionPieces = new SolutionPieces();
             this.solutionPieces = solutionPieces.getSolutionPieces(winnerSoltion,rotate);
        }





    }

    public class SolutionPieces {






        public String getSolutionPieces(PuzzlePieceIdentity[][] puzzlePieceIdentities
                , boolean rotate){
            return createSolutionPiecesArray(puzzlePieceIdentities, rotate);
        }



        /**
         * create solution pieces array, in case there is a solution
         *
         * @param puzzlePieces
         * @return JsonArray
         */
        private String createSolutionPiecesArray(PuzzlePieceIdentity[][] puzzlePieces, boolean rotate) {



            JsonArray rowSolutionPieces = new JsonArray();
            for ( int i = 0; i < puzzlePieces.length; i++ ) {

                for ( PuzzlePieceIdentity ppi : puzzlePieces[i] ) {
                    JsonObject piece = new JsonObject();
                    piece.addProperty("id", ppi.getPuzzlePieceID());
                    if (rotate) {
                        piece.addProperty("rotate", ppi.getRotation());
                    }
                    rowSolutionPieces.add(piece);
                }


            }


            return String.valueOf(rowSolutionPieces);
        }


    }





        @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(solution);
    }

}

