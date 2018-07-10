package org.ripreal.textclassifier2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.slf4j.Logger;

import java.io.OutputStream;
import java.util.List;

public class JiraIssueWriter {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JiraIssueWriter.class);
    private final JiraClient client;
    private final ObjectMapper mapper;

    @java.beans.ConstructorProperties({"client", "mapper"})
    public JiraIssueWriter(JiraClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public boolean write(JiraIssueReader reader, OutputStream out) throws Exception {

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
