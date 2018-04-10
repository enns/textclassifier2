package org.ripreal.textclassifier2.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

public interface TestDataReader extends AutoCloseable {

    boolean hasNext();

    TestDataReader.ClassifiableData next() throws IOException;

    TestDataReader.ClassifiableData readAll() throws IOException;

    static Set<CharacteristicValue> getCharacteristicValueTetData(List<ClassifiableText> texts) {
        return texts.stream()
                .flatMap(text ->  text.getCharacteristics().stream())
                .collect(toSet());
    }

    static Set<Characteristic> getCharacteristicTestData(Set<CharacteristicValue> charVals) {
        //TODO: may get possible CharacteristicValue set from database
        Map<Characteristic, Set<CharacteristicValue>> map = charVals.stream()
            .collect(Collectors.groupingBy(
                    CharacteristicValue::getCharacteristic,
                    HashMap::new,
                    mapping((charVal) -> charVal, toSet())
        ));
        Set<Characteristic> chars =map.entrySet().stream()
            .map((entry) -> {
                    Characteristic entryChar = entry.getKey();
                    entryChar.setPossibleValues(entry.getValue());
                    return entryChar;
                })
            .collect(Collectors.toSet());
        // if you take a slice from characteristic val set you need to recalculate order
        for(Characteristic characteristic : chars) {
            int order = 1;
            for(CharacteristicValue charVal : characteristic.getPossibleValues())
                charVal.setOrderNumber(order++);
        }
        return chars;
    }

    @RequiredArgsConstructor
    public final class ClassifiableData{
        @Getter
        private final List<ClassifiableText> classifiableTexts;
        @Getter
        private final Set<Characteristic> characteristics;
        @Getter
        private final Set<CharacteristicValue> characteristicValues;

        public boolean isEmpty() {
            return classifiableTexts.size() == 0
                && characteristics.size() == 0
                && characteristicValues.size() == 0;
        }

        public static ClassifiableData empty() {
            return new ClassifiableData(new ArrayList<>(),new HashSet<>(), new HashSet<>());
        }
    }
}

