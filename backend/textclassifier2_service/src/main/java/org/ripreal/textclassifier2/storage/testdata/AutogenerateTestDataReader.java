package org.ripreal.textclassifier2.storage.testdata;

import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.storage.data.entities.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;
import org.ripreal.textclassifier2.testdata.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AutogenerateTestDataReader implements TestDataReader {

    private ClassifiableMapper mapper;

    // ITERATOR

    private boolean hasNext = true;

    public AutogenerateTestDataReader(ClassifiableMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public TestDataReader.ClassifiableData next() {
        hasNext = false;
        List<MongoClassifiableText> classifiableTexts = getTextTestData();
        Set<MongoCharacteristic> characteristics = getCharacteristicTestData();
        Set<MongoCharacteristicValue> characteristicValues = getCharacteristicValueTetData(classifiableTexts);

        return new TestDataReader.ClassifiableData(
                mapper.toClassifiableText(classifiableTexts),
                mapper.toCharacteristic(characteristics),
                mapper.toCharacteristicValues(characteristicValues)
        );
    }

    @Override
    public ClassifiableData readAll() throws IOException {
        return next();
    }

    @Override
    public void close() {
    }

    // GENERATING TEXTS

    private List<MongoClassifiableText> getTextTestData() {

        Set<MongoCharacteristic> characteristics = getCharacteristicTestData();

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

    public Set<MongoVocabularyWord> getVocabTestData() {
        return new HashSet<>(Arrays.asList(
            new MongoVocabularyWord("треб", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString()),
            new MongoVocabularyWord("найти", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString())
        ));
    }

    private Set<MongoCharacteristicValue> getCharacteristicValueTetData(List<MongoClassifiableText> texts) {
        return texts.stream()
            .flatMap(text ->  text.getCharacteristics().stream())
            .collect(Collectors.toSet());
    }

    private Set<MongoCharacteristic> getCharacteristicTestData() {
        // order has meaning
        Set<MongoCharacteristic> characteristics = new HashSet<>();
        characteristics.add(new MongoCharacteristic("Отдел"));
        characteristics.add(new MongoCharacteristic("Тип"));
        return characteristics;
    }
}
