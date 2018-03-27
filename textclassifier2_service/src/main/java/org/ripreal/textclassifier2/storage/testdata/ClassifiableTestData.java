package org.ripreal.textclassifier2.storage.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.ripreal.textclassifier2.storage.data.entities.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class ClassifiableTestData {

    public static List<MongoClassifiableText> getTextTestData() {

        List<MongoCharacteristic> characteristics = getCharacteristicTestData();

        MongoCharacteristic characteristic1 = characteristics.get(0);

        Set<MongoCharacteristicValue> vals = new HashSet<>();
        MongoCharacteristicValue auto = new MongoCharacteristicValue("Автосалон", 0, characteristic1);
        vals.add(auto);
        MongoCharacteristicValue agro = new MongoCharacteristicValue("Агро", 1, characteristic1);
        vals.add(agro);
        MongoCharacteristicValue logistic = new MongoCharacteristicValue("Логистика", 1, characteristic1);
        vals.add(logistic);

        MongoCharacteristic characteristic2 = characteristics.get(1);

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

    public static List<MongoVocabularyWord> getVocabTestData() {
        return Arrays.asList(
                new MongoVocabularyWord("треб", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString()),
                new MongoVocabularyWord("найти", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString())
        );
    }

    public static List<MongoCharacteristicValue> getCharacteristicValueTetData() {
        return getTextTestData().stream()
            .flatMap(text ->  text.getCharacteristics().stream())
            .map(value -> (MongoCharacteristicValue) value)
            .collect(Collectors.toList());
    }

    public static List<MongoCharacteristic> getCharacteristicTestData() {
        // order has meaning
        List<MongoCharacteristic> characteristics = new ArrayList<>();
        characteristics.add(new MongoCharacteristic("Отдел"));
        characteristics.add(new MongoCharacteristic("Тип"));
        return characteristics;
    }

    public static String ConvertObjectToJson(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(data);
    }
}
