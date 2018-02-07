package org.ripreal.textclassifier2;

import org.junit.Test;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.textreaders.ExcelFileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExcelFileReaderTest {
    private final ExcelFileReader excelFileReader = new ExcelFileReader(new File("./test_db/test.xlsx"), 1);

    @Test
    public void xlsxToClassifiableTexts() throws Exception {
        List<ClassifiableText> classifiableTexts = excelFileReader.toClassifiableTexts();

        assertEquals(classifiableTexts.size(), 3);

        assertEquals(classifiableTexts.get(0).getText(), "test1 test1");
        assertEquals(classifiableTexts.get(0).getCharacteristics().size(), 2);
        assertEquals(classifiableTexts.get(0).getCharacteristics().get(new Characteristic("Characteristic 1")).getValue(), "val1");
        assertEquals(classifiableTexts.get(0).getCharacteristics().get(new Characteristic("Characteristic 2")).getValue(), "val3");

        assertEquals(classifiableTexts.get(1).getText(), "test2 test2");
        assertEquals(classifiableTexts.get(1).getCharacteristics().size(), 2);
        assertEquals(classifiableTexts.get(1).getCharacteristics().get(new Characteristic("Characteristic 1")).getValue(), "val2");
        assertEquals(classifiableTexts.get(1).getCharacteristics().get(new Characteristic("Characteristic 2")).getValue(), "val4");

        assertEquals(classifiableTexts.get(2).getText(), "test3 test3");
        assertEquals(classifiableTexts.get(2).getCharacteristics().size(), 2);
        assertEquals(classifiableTexts.get(2).getCharacteristics().get(new Characteristic("Characteristic 1")).getValue(), "val5");
        assertEquals(classifiableTexts.get(2).getCharacteristics().get(new Characteristic("Characteristic 2")).getValue(), "val6");
    }

    @Test(expected = FileNotFoundException.class)
    public void xlsxToClassifiableTextsNotExistingFile() throws Exception {
        excelFileReader.toClassifiableTexts();
    }

    /*
    @Test(expected = IllegalArgumentException.class)
    public void xlsxToClassifiableTextsNotExistingFile1() throws Exception {
        excelFileReader.toClassifiableTexts();
    }
    */
}