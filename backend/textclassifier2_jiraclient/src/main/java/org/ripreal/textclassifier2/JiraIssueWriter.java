package org.ripreal.textclassifier2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.javafx.collections.ObservableSequentialListWrapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JiraIssueWriter {

    private final JiraClient client;
    private final ObjectMapper mapper;

    public boolean write(@NonNull JiraIssueReader reader, @NonNull OutputStream out) throws Exception {

        JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(out);
        //for pretty printing
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

        jsonGenerator.writeStartArray();
        while(reader.next()) {
            List<ClassifiableText> texts = reader.getTexts();
            for(ClassifiableText singleText : texts) {

                singleText.getCharacteristics().forEach(
                    chr -> chr.getCharacteristic().getPossibleValues().clear());

                jsonGenerator.writeObject(singleText);
            }
            System.out.println("written " + reader.getPosition());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.close();
        reader.close();
        return true;

    }

}
