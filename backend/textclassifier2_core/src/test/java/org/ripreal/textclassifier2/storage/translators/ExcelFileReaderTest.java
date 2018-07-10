package org.ripreal.textclassifier2.storage.translators;

import org.junit.Test;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;
import org.ripreal.textclassifier2.testdata.ExcelFileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExcelFileReaderTest {
    private final ExcelFileReader excelFileReader = new ExcelFileReader(new File("./test_db/test.xlsx"), 1, new DefClassifiableFactory());
    private final ClassifiableFactory characteristicFactory = new DefClassifiableFactory();

    @Test
    public void xlsxToClassifiableTexts() throws Exception {
        List<ClassifiableText> classifiableTexts = excelFileReader.toClassifiableTexts();

        assertEquals(classifiableTexts.size(), 18);

        assertEquals(classifiableTexts.get(0).getText(), "Требуется починить телефон");
        assertEquals(classifiableTexts.get(0).getCharacteristics().size(), 2);
        assertTrue(classifiableTexts.get(0).getCharacteristics().stream().anyMatch(
                value -> value.getValue().equals("Автосалон")));
        assertTrue(classifiableTexts.get(0).getCharacteristics().stream().anyMatch(
                value -> value.getValue().equals("Техподдержка")));

        assertEquals(classifiableTexts.get(1).getText(), "У меня не проводится документ");
        assertEquals(classifiableTexts.get(1).getCharacteristics().size(), 2);
        assertTrue(classifiableTexts.get(1).getCharacteristics().stream().anyMatch(
                value -> value.getValue().equals("Бухгалтерия")));
        assertTrue(classifiableTexts.get(1).getCharacteristics().stream().anyMatch(
                value -> value.getValue().equals("Разработчики")));

    }


    @Test(expected = IOException.class)
    public void nonExistentFile() throws IOException {
        ExcelFileReader reader = new ExcelFileReader(new File("incorrect_path"), 1, characteristicFactory);
        reader.next().getClassifiableTexts();
    }

    @Test
    public void IncorrectSheetNumber() throws IOException {
        ExcelFileReader reader = new ExcelFileReader(new File("./test_db/test.xlsx"), 2, characteristicFactory);
        reader.next().getClassifiableTexts();
    }
}
