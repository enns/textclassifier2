package org.ripreal.textclassifier2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

    public boolean write(@NonNull JiraIssueReader reader, @NonNull OutputStream out) throws IOException {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        while(reader.next()) {
            List<ClassifiableText> texts = reader.getTexts();
            log.info("Read {} texts", reader.getPosition());
            ow.writeValue(out, texts);
        }
        return true;
    }
}
