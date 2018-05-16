package com.att.biq.dst.jigsaw.puzzleUtils;

import com.att.biq.dst.jigsaw.puzzle.Generator;
import org.junit.jupiter.api.Test;

public class GeneratorTest {

    @Test
    public void testGenerator(){
        Generator generator = new Generator(10,10,"./src/main/resources/generatedPuzzles");

    }
}
