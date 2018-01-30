package org.ripreal.textclassifier2.classifier.textreaders;

import org.ripreal.textclassifier2.model.ClassifiableText;

import java.io.IOException;
import java.util.List;

public interface ClassifiableReader {
    List<ClassifiableText>  toClassifiableTexts() throws IOException, DataSourceException;
}
