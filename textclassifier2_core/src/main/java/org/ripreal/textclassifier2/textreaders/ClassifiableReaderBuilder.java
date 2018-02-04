package org.ripreal.textclassifier2.textreaders;

import java.io.File;
// BUILDER
public class ClassifiableReaderBuilder {

    public static ClassifiableReaderBuilder builder() {
        return new ClassifiableReaderBuilder();
    }

    public ClassifiableReader newExcelFileReader(File file, int sheetNumber) {
        return new ExcelFileReader(file, sheetNumber);
    }
}
