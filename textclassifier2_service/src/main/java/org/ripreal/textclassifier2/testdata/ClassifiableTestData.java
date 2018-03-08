package org.ripreal.textclassifier2.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.ripreal.textclassifier2.entries.*;
import org.ripreal.textclassifier2.model.*;
import org.ripreal.textclassifier2.ngram.NGramStrategy;

import java.util.*;

public class ClassifiableTestData {

    public static List<PersistClassifiableText> getTextTestData() {

        List<PersistCharacteristic> characteristics = getCharacteristicTestData();

        PersistCharacteristic characteristic1 = characteristics.get(0);

        Set<PersistCharacteristicValue> vals = new HashSet<>();
        PersistCharacteristicValue auto = new PersistCharacteristicValue("Автосалон", 0, characteristic1);
        vals.add(auto);
        PersistCharacteristicValue agro = new PersistCharacteristicValue("Агро", 1, characteristic1);
        vals.add(agro);
        PersistCharacteristicValue logistic = new PersistCharacteristicValue("Логистика", 1, characteristic1);
        vals.add(logistic);

        // characteristic1.setPossibleValues(vals);

        PersistCharacteristic characteristic2 = characteristics.get(1);

        Set<PersistCharacteristicValue> vals2 = new HashSet<>();
        PersistCharacteristicValue it = new PersistCharacteristicValue("Техподдержка", 0, characteristic2);
        vals2.add(it);
        PersistCharacteristicValue dev = new PersistCharacteristicValue("Разработчики", 1, characteristic2);
        vals2.add(dev);
        PersistCharacteristicValue analytics = new PersistCharacteristicValue("Методисты", 1, characteristic2);
        vals2.add(analytics);

        PersistClassifiableText text1 = new PersistClassifiableText("Требуется починить телефон",
            new HashSet<>(Arrays.asList(
                new PersistCharactValuePair(characteristic2, it),
                new PersistCharactValuePair(characteristic1, auto)
        )));

        PersistClassifiableText text2 = new PersistClassifiableText("Требуется починить заказы клиента в 1с",
            new HashSet<>(Arrays.asList(
                new PersistCharactValuePair(characteristic2, dev),
                new PersistCharactValuePair(characteristic1, agro)
        )));

        return Arrays.asList(text1, text2);
    }

    public static List<PersistVocabularyWord> getVocabTestData() {
        return Arrays.asList(
                new PersistVocabularyWord("треб", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString()),
                new PersistVocabularyWord("найти", NGramStrategy.NGRAM_TYPES.FILTERED_UNIGRAM.toString())
        );
    }

    public static List<PersistCharacteristic> getCharacteristicTestData() {
        // order has meaning
        List<PersistCharacteristic> characteristics = new ArrayList<>();
        characteristics.add(new PersistCharacteristic("Отдел"));
        characteristics.add(new PersistCharacteristic("Тип"));
        return characteristics;
    }

    public static String ConvertObjectToJson(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(data);
    }
}
