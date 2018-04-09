package org.ripreal.textclassifier2;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.ripreal.textclassifier2.model.ClassifiableFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface JiraClient extends AutoCloseable {
    JiraIssueReader newIssueReader(int maxResult, ClassifiableFactory textFactory);
    JiraIssueWriter newIssueWriter();
    String GET(String url, Map<String,String> params) throws IOException;
    String GET(String url) throws IOException;
    String test(String t);
}
