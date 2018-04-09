package org.ripreal.textclassifier2.storage.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TestDataReader extends AutoCloseable {

    public boolean hasNext();

    TestDataReader.ClassifiableData next() throws IOException;

    @RequiredArgsConstructor
    public final class ClassifiableData{
        @Getter
        private final List<ClassifiableText> classifiableTexts;
        @Getter
        private final Set<Characteristic> characteristics;
        @Getter
        private final Set<CharacteristicValue> characteristicValues;
        @Getter
        private final Set<VocabularyWord> vocabulary;

        public boolean isEmpty() {
            return classifiableTexts.size() == 0
                && characteristics.size() == 0
                && characteristicValues.size() == 0
                && vocabulary.size() == 0;
        }

        public static ClassifiableData empty() {
            return new ClassifiableData(new ArrayList<>(),new HashSet<>(), new HashSet<>(), new HashSet<>());
        }
    }
}
