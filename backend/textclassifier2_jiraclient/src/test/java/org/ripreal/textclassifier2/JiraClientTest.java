package org.ripreal.textclassifier2;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class JiraClientTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JiraClientTest.class);
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
        JiraIssueReader reader = client.newIssueReader(2, textFactory);
        reader.setUpperLimit(2);
        while (reader.next()) {
            log.info("request's result: {}", reader.getTexts());
            assertTrue("texts size equal to size from json response", reader.getTexts().size() == 2);
        }
    }

    @Test
    public void testJiraIssueWriter() throws Exception {
        JiraIssueWriter writer = client.newIssueWriter();
        JiraIssueReader reader = client.newIssueReader(2, textFactory);
        reader.setUpperLimit(2);
        Path uploadedTextsPath = Files.createTempFile(null, null);
        assertTrue(writer.write(
            reader,
            new FileOutputStream(new File(uploadedTextsPath.toUri()))
        ));
        assertTrue(Files.exists(uploadedTextsPath));
        Files.delete(uploadedTextsPath);
    }
}
