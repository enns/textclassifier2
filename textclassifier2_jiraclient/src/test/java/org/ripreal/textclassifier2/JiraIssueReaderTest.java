package org.ripreal.textclassifier2;

import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;

import static org.junit.Assert.*;

public class JiraIssueReaderTest {

    private JiraClient client;

    @Before
    public void init() throws Exception {
        client = new JiraBasicAuthClient(new PropertiesClient());
    }

    @Test
    public void testGetTextForJira() {
        try (JiraIssueReader reader = client.issueReader(2, new DefClassifiableFactory())) {
            while (reader.next()) {
                System.out.println(reader.getTexts());
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }

}