package org.ripreal.textclassifier2.storage.data.mapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristic;
import org.ripreal.textclassifier2.storage.data.entities.MongoCharacteristicValue;
import org.ripreal.textclassifier2.storage.data.entities.MongoClassifiableText;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//
 //       SimpleModule module = new SimpleModule(
     //       "CustomDeserializer",
   //         new Version(1, 0, 0, null, null, null))
            //.addDeserializer(MongoCharacteristicValue.class, new CharacteristicValueDeserializer())
            //.addDeserializer(MongoCharacteristic.class, new CharacteristicDeserializer())
            //.addDeserializer(VocabularyWord.class, new VocabularyWordDeserializer())
            //.addDeserializer(MongoClassifiableText.class, new ClassifiableTextDeserializer());

       // mapper.registerModule(module);

        return mapper;
    }
}
