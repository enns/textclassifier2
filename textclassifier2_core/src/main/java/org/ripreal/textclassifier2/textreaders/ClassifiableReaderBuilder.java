package org.ripreal.textclassifier2.textreaders;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.CharacteristicFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.io.File;
import java.util.List;

// FACTORY
@RequiredArgsConstructor
public class ClassifiableReaderBuilder {

    private final CharacteristicFactory characteristicFactory;

    public static ClassifiableReaderBuilder builder(CharacteristicFactory characteristicFactory) {
        return new ClassifiableReaderBuilder(characteristicFactory);
    }

    public ClassifiableReader newExcelFileReader(File file, int sheetNumber) {
        return new ExcelFileReader(file, sheetNumber, characteristicFactory);
    }

}
