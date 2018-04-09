package org.ripreal.textclassifier2.storage.testdata;

import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.storage.data.entities.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.testdata.TestDataReader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AutogenerateTestDataReader implements TestDataReader {

    // ITERATOR

    private boolean hasNext = true;

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public TestDataReader.ClassifiableData next() {
        hasNext = false;
        List<ClassifiableText> classifiableTexts = getTextTestData();
        Set<Characteristic> characteristics = getCharacteristicTestData();
        Set<CharacteristicValue> characteristicValues = getCharacteristicValueTetData(classifiableTexts);
        return new TestDataReader.ClassifiableData(classifiableTexts, characteristics, characteristicValues);
    }

    @Override
    public ClassifiableData readAll() throws IOException {
        return next();
    }

    @Override
    public void close() {
    }

    // GENERATING TEXTS

    private List<ClassifiableText> getTextTestData() {

        Set<Characteristic> characteristics = getCharacteristicTestData();

        MongoCharacteristic characteristic1 = characteristics.toArray(new MongoCharacteristic[0])[0];

        Set<MongoCharacteristicValue> vals = new HashSet<>();
        MongoCharacteristicValue auto = new MongoCharacteristicValue("Автосалон", 0, characteristic1);
        vals.add(auto);
        MongoCharacteristicValue agro = new MongoCharacteristicValue("Агро", 1, characteristic1);
        vals.add(agro);
        MongoCharacteristicValue logistic = new MongoCharacteristicValue("Логистика", 1, characteristic1);
        vals.add(logistic);

        MongoCharacteristic characteristic2 = characteristics.toArray(new MongoCharacteristic[0])[1];

        Set<MongoCharacteristicValue> vals2 = new HashSet<>();
        MongoCharacteristicValue it = new MongoCharacteristicValue("Техподдержка", 0, characteristic2);
        vals2.add(it);
        MongoCharacteristicValue dev = new MongoCharacteristicValue("Разработчики", 1, characteristic2);
        vals2.add(dev);
        MongoCharacteristicValue analytics = new MongoCharacteristicValue("Методисты", 1, characteristic2);
        vals2.add(analytics);

        MongoClassifiableText text1 = new MongoClassifiableText("Требуется починить телефон",
            new HashSet<>(Arrays.asList(it, auto)
        ));

        MongoClassifiableText text2 = new MongoClassifiableText("Требуется починить заказы клиента в 1с",
            new HashSet<>(Arrays.asList(dev, agro)
        ));

        MongoClassifiableText text3 = new MongoClassifiableText("Как оформить списание основного средства в ПП Агроинвест",
                new HashSet<>(Arrays.asList(analytics, agro)
        ));

        return Arrays.asList(text1, text2, text3);
    }

    public Set<VocabularyWord> getVocabTestData() {
        return new HashSet<>(Arrays.asList(
            new MongoVocabularyWord("треб", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString()),
            new MongoVocabularyWord("найти", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString())
        ));
    }

    private Set<CharacteristicValue> getCharacteristicValueTetData(List<ClassifiableText> texts) {
        return texts.stream()
            .flatMap(text ->  text.getCharacteristics().stream())
            .map(value -> (MongoCharacteristicValue) value)
            .collect(Collectors.toSet());
    }

    private Set<Characteristic> getCharacteristicTestData() {
        // order has meaning
        Set<Characteristic> characteristics = new HashSet<>();
        characteristics.add(new MongoCharacteristic("Отдел"));
        characteristics.add(new MongoCharacteristic("Тип"));
        return characteristics;
    }
}
