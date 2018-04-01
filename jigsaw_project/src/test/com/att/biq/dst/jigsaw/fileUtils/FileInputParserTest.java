package File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileInputParserTest {

    @Test
    public void positiveTestParserValidateIds(){
        Assertions.assertTrue(File.FileInputParser.validateIds(new Integer[][]{{1},{2}, {3}, {4}}), "ID validation passed unexpectedly");
    }


    @Test
    public void testParserValidateIdsWithMissingId(){
        Assertions.assertFalse(File.FileInputParser.validateIds(new Integer[][]{{1}, {3}, {4}}), "ID validation failed unexpectedly");
    }

    @Test
    public void testParserValidateIdsWithUnOrderedIds(){
        Assertions.assertTrue(File.FileInputParser.validateIds(new Integer[][]{{1}, {3}, {2},{4}}), "ID validation failed unexpectedly");
    }



}