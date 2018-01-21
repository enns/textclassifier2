package org.ripreal.textclassifier2.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.ripreal.textclassifier2.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassifiableTextTestDataHelper {

    public static List<ClassifiableText> getTextTestData(){
        Characteristic characteristic1 = new Characteristic("Отдел");

        Set<CharacteristicValue> vals = new HashSet<>();
        CharacteristicValue auto = new CharacteristicValue("Автосалон", 0, characteristic1);
        vals.add(auto);
        CharacteristicValue agro = new CharacteristicValue("Агро", 1, characteristic1);
        vals.add(agro);
        CharacteristicValue logistic = new CharacteristicValue("Логистика", 1, characteristic1);
        vals.add(logistic);

       // characteristic1.setPossibleValues(vals);

        Characteristic characteristic2 = new Characteristic("Тип");

        Set<CharacteristicValue> vals2 = new HashSet<>();
        CharacteristicValue it = new CharacteristicValue("Техподдержка", 0, characteristic2);
        vals2.add(it);
        CharacteristicValue dev = new CharacteristicValue("Разработчики", 1, characteristic2);
        vals2.add(dev);
        CharacteristicValue analytics = new CharacteristicValue("Методисты", 1, characteristic2);
        vals2.add(analytics);

     //   characteristic2.setPossibleValues(vals2);

        ClassifiableText text1 = new ClassifiableText("Требуется починить телефон");
        text1.setCharacteristics(new HashSet<CharactValuePair>(Arrays.asList(
                new CharactValuePair(characteristic2, it),
                new CharactValuePair(characteristic1, auto)
        )));

        ClassifiableText text2 = new ClassifiableText("Требуется починить заказы клиента в 1с");
        text2.setCharacteristics(new HashSet<CharactValuePair>(Arrays.asList(
                new CharactValuePair(characteristic2, dev),
                new CharactValuePair(characteristic1, agro)
        )));

        return Arrays.asList(text1, text2);
    }

    public static List<VocabularyWord> getVocabTestData() {
        return Arrays.asList(
                new VocabularyWord("треб"),
                new VocabularyWord("найти")
        );
    }

    public static String ConvertObjectToJson(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(data);
    }
}
