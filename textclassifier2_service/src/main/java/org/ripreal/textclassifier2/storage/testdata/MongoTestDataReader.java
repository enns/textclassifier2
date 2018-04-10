package org.ripreal.textclassifier2.storage.testdata;

import lombok.RequiredArgsConstructor;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.ripreal.textclassifier2.storage.data.mapper.EntitiesConverter;
import org.ripreal.textclassifier2.storage.service.ClassifiableService;
import org.ripreal.textclassifier2.storage.service.decorators.LoggerClassifiableTextService;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MongoTestDataReader implements TestDataReader {

    private ClassifiableService textService;

    private boolean hasNext = true;
    private int upperLimit = -1;
    private List<ClassifiableText> textTestData;
    private Set<Characteristic> characteristicTestData;

    public MongoTestDataReader(ClassifiableService textService, int upperLimit) {
        this.textService = new LoggerClassifiableTextService(textService);
        this.upperLimit = upperLimit;
    }

    public MongoTestDataReader(ClassifiableService textService) {
        this(textService, -1);
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public ClassifiableData next() throws IOException {
        hasNext = false;

        List<ClassifiableText> classifiableTexts = new ArrayList<>();
        if (upperLimit == -1)
            classifiableTexts = getTextTestData();
        else
            classifiableTexts = getBoundedTestData();

        if (classifiableTexts.size() != 0) {
            Set<CharacteristicValue> characteristicValues = getCharacteristicValueTestData();
            Set<Characteristic> characteristics =  getCharacteristicsTestData();
            return new TestDataReader.ClassifiableData(classifiableTexts, characteristics, characteristicValues);
        }
        return TestDataReader.ClassifiableData.empty();
    }

    @Override
    public ClassifiableData readAll() throws IOException {
        return next();
    }

    @Override
    public void close() throws Exception {

    }

    private List<ClassifiableText> getTextTestData() {
        return textService.findAllTexts().collect(
                ArrayList<ClassifiableText>::new, List::add)
            .block();
    }

    private List<ClassifiableText> getBoundedTestData() {
        Iterator<MongoClassifiableText> iterator = textService.findAllTexts().toIterable().iterator();

        List<ClassifiableText> texts = new ArrayList<>();
        while (iterator.hasNext()
            && (upperLimit == -1 || upperLimit > texts.size())) {
            texts.add((ClassifiableText)iterator.next());
        }
        return texts;
    }

    private Set<Characteristic> getCharacteristicsTestData() {
        return textService.findAllCharacteristics().collect(
                HashSet<Characteristic>::new, HashSet::add)
                .block();
    }

    private Set<CharacteristicValue> getCharacteristicValueTestData() {
        return textService.findAllCharacteristicValues().collect(
                HashSet<CharacteristicValue>::new, HashSet::add)
                .block();
    }

}
