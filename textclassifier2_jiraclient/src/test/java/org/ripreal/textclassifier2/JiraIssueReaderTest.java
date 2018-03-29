package org.ripreal.textclassifier2;

import org.junit.Before;
import org.junit.Test;
import org.ripreal.textclassifier2.model.modelimp.DefClassifiableFactory;

import static org.junit.Assert.*;

public class JiraIssueReaderTest {

    private JiraBasicAuthClient client;

    @Before
    public void init() throws Exception {
        PropertiesClient propertes = new PropertiesClient();
        client = new JiraBasicAuthClient(new DefClassifiableFactory(), propertes);
    }

    @Test
    public void testGetTextForJira() {
        try (JiraIssueReader reader = client.reader(2)) {
            while (reader.next()) {
                System.out.println(reader.getResult());
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }

}