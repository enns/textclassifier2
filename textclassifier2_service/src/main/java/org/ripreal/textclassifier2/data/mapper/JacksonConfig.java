package org.ripreal.textclassifier2.data.mapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.VocabularyWord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        SimpleModule module = new SimpleModule(
            "CustomDeserializer",
            new Version(1, 0, 0, null, null, null))
            .addDeserializer(CharacteristicValue.class, new CharacteristicValueDeserializer())
            .addDeserializer(Characteristic.class, new CharacteristicDeserializer())
            .addDeserializer(VocabularyWord.class, new VocabularyWordDeserializer())
            .addDeserializer(ClassifiableText.class, new ClassifiableTextDeserializer());

        mapper.registerModule(module);

        return mapper;
    }
}
