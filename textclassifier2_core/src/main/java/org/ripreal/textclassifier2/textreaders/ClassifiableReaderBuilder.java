package org.ripreal.textclassifier2.textreaders;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.ClassifiableFactory;

import java.io.File;

// FACTORY
@RequiredArgsConstructor
public class ClassifiableReaderBuilder {

    private final ClassifiableFactory characteristicFactory;

    public static ClassifiableReaderBuilder builder(ClassifiableFactory characteristicFactory) {
        return new ClassifiableReaderBuilder(characteristicFactory);
    }

    public ClassifiableReader newExcelFileReader(File file, int sheetNumber) {
        return new ExcelFileReader(file, sheetNumber, characteristicFactory);
    }

}
