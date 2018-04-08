package org.ripreal.textclassifier2;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentMatchers;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@Slf4j
public class JiraClientTest {

    private JiraClient client;
    private final DefClassifiableFactory textFactory = new DefClassifiableFactory();

    @Before
    public void init() throws Exception {
        PropertiesClient properties = spy(PropertiesClient.class);
        when(properties.getPropertiesOrDefaults()).thenReturn(PropertiesClient.DEFAULT_PROPERTY_VALUES);

        this.client = spy(JiraBasicAuthClient.class);
        doReturn(
            IOUtils.toString(
                    new FileInputStream(new File("./resources/IssueTypeExample.json")),
                    StandardCharsets.UTF_8)
        ).when(this.client).GET(JiraIssueReader.ISSUE_TYPES);
        doReturn(
                IOUtils.toString(
                        new FileInputStream(new File("./resources/ProjectTypeExample.json")),
                        StandardCharsets.UTF_8)
        ).when(this.client).GET(JiraIssueReader.PROJECT_TYPES);
        doReturn(
                IOUtils.toString(
                        new FileInputStream(new File("./resources/IssueExample.json")),
                        StandardCharsets.UTF_8)
        ).when(this.client).GET(eq(JiraIssueReader.ISSUE_PATTERN), ArgumentMatchers.any());
    }

    @Test
    public void testJiraIssueReader() throws IOException {
        JiraIssueReader reader = client.issueReader(2, textFactory);
        reader.setUpperLimit(2);
        while (reader.next()) {
            log.info("request's result: {}", reader.getTexts());
            assertTrue("texts size equal to size from json response", reader.getTexts().size() == 2);
        }
    }

    @Test
    public void testJiraIssueWriter() throws IOException {
        JiraIssueWriter writer = client.issueWriter();
        Path uploadedTextsPath = Files.createTempFile(null, null);
        writer.write(
            client.issueReader(2, textFactory),
            new FileOutputStream(new File(uploadedTextsPath.toUri()))
        );

    }
}