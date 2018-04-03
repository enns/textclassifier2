package org.ripreal.textclassifier2;

import org.ripreal.textclassifier2.model.ClassifiableFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface JiraClient extends AutoCloseable {
    JiraIssueReader issueReader(int maxResult, ClassifiableFactory textFactory);
    String GET(String url, Map<String,String> params) throws IOException;
    String GET(String url) throws IOException;
}
