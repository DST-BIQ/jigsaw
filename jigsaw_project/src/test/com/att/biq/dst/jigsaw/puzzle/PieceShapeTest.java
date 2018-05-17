package com.att.biq.dst.jigsaw.puzzle;
import com.att.biq.dst.jigsaw.puzzle.server.PieceShape;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceShapeTest {

    @Test
    public void testEquals() {

        PieceShape ps = new PieceShape(1, -1, 1, -1);

        assertTrue(ps.equals(new PieceShape(2,-1,1,-1)));
        assertTrue(ps.equals(new PieceShape(1,2,1,-1)));
        assertTrue(ps.equals(new PieceShape(1,-1,2,-1)));
        assertTrue(ps.equals(new PieceShape(1,-1,1,2)));
        assertTrue(ps.equals(new PieceShape(1,-1,2,2)));
        assertTrue(ps.equals(new PieceShape(1,-1,1,-1)));
    }




    @Test
    public void testNotEqualsWithJoker() {

        PieceShape ps = new PieceShape(1, -1, 1, -1);

        assertFalse(ps.equals(new PieceShape(2,0,1,-1)));
    }





    @Test
    public void testNotEquals() {

        PieceShape ps = new PieceShape(1, -1, 1, -1);

        assertFalse(ps.equals(new PieceShape(0,0,1,-1)));
    }



}