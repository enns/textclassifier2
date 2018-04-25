package org.ripreal.textclassifier2.storage.testdata;

import lombok.NonNull;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.testdata.TestDataReader;

import java.io.IOException;
import java.util.*;

public class MongoTestDataReader implements TestDataReader {

    @NonNull
    private ClassifiableMapper textMapper;
    @NonNull
    private ClassifiableService textService;

    private boolean hasNext = true;
    private int upperLimit = -1;

    public MongoTestDataReader(ClassifiableService textService,  ClassifiableMapper textMapper, int upperLimit) {
        this.textService = textService;
        this.upperLimit = upperLimit;
    }

    public MongoTestDataReader(ClassifiableService textService, ClassifiableMapper textMapper) {
        this(textService, textMapper, -1);
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public ClassifiableData next() throws IOException {
        hasNext = false;

        List<MongoClassifiableText> classifiableTexts = new ArrayList<>();
        if (upperLimit == -1)
            classifiableTexts = getTextTestData();
        else
            classifiableTexts = getBoundedTestData();

        if (classifiableTexts.size() != 0) {
            Set<MongoCharacteristicValue> characteristicValues = getCharacteristicValueTestData();
            Set<MongoCharacteristic> characteristics = getCharacteristicsTestData();

            return new TestDataReader.ClassifiableData(
                    textMapper.toClassifiableText(classifiableTexts),
                    textMapper.toCharacteristic(characteristics),
                    textMapper.toCharacteristicValues(characteristicValues)
            );
        }
        else {
            return TestDataReader.ClassifiableData.empty();
        }
    }

    @Override
    public ClassifiableData readAll() throws IOException {
        return next();
    }

    @Override
    public void close() throws Exception {

    }

    private List<MongoClassifiableText> getTextTestData() {
        return textService.findAllTexts().collect(
                ArrayList<MongoClassifiableText>::new, List::add)
            .block();
    }

    private List<MongoClassifiableText> getBoundedTestData() {
        Iterator<MongoClassifiableText> iterator = textService.findAllTexts().toIterable().iterator();
        List<MongoClassifiableText> texts = new ArrayList<>();
        while (iterator.hasNext()
            && (upperLimit == -1 || upperLimit > texts.size())) {
            texts.add(iterator.next());
        }
        return texts;
    }

    private Set<MongoCharacteristic> getCharacteristicsTestData() {
        Set<MongoCharacteristic> target = new HashSet<>();
        textService.findAllCharacteristics().toIterable().forEach(target::add);
        return target;
    }

    private Set<MongoCharacteristicValue> getCharacteristicValueTestData() {
        return textService.findAllCharacteristicValues()
                .collect(HashSet<MongoCharacteristicValue>::new, HashSet::add)
                .block();
    }

}
