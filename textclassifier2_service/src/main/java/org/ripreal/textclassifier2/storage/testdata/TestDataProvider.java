package org.ripreal.textclassifier2.storage.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoVocabularyWord;
import sun.util.resources.cldr.st.CalendarData_st_LS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TestDataProvider extends AutoCloseable {

    TestDataProvider.ClassifiableData next() throws IOException;

    @RequiredArgsConstructor
    public final class ClassifiableData{
        @Getter
        private final List<MongoClassifiableText> classifiableTexts;
        @Getter
        private final Set<MongoCharacteristic> characteristics;
        @Getter
        private final Set<MongoCharacteristicValue> characteristicValues;
        @Getter
        private final Set<MongoVocabularyWord> vocabulary;

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

