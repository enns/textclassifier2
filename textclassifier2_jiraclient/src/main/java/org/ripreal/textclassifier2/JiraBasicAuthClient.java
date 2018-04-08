package org.ripreal.textclassifier2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JiraBasicAuthClient implements JiraClient{

    private  Map<String, String> properties;
    private final ObjectMapper mapper = new ObjectMapper().
        configure(SerializationFeature.WRAP_ROOT_VALUE, false);

    public JiraBasicAuthClient(@NonNull PropertiesClient propertiesClient) {

       this.properties = propertiesClient.getPropertiesOrDefaults();

        if (!(properties.get(PropertiesClient.PROXY_PORT).isEmpty()
            && properties.get(PropertiesClient.PROXY_SERVER).isEmpty())) {

            Unirest.setProxy(new HttpHost(properties.get(PropertiesClient.PROXY_SERVER),
                Integer.parseInt(properties.get(PropertiesClient.PROXY_PORT))));
        }
    }

    public JiraBasicAuthClient() throws Exception {
        new JiraBasicAuthClient(new PropertiesClient());
    }

    @Override
    public JiraIssueReader issueReader(int maxResult, @NonNull ClassifiableFactory textFactory) {
        return new JiraIssueReader(this, mapper, maxResult, textFactory);
    }

    @Override
    public JiraIssueWriter issueWriter() {
        return new JiraIssueWriter(this, mapper);
    }

    @Override
    public void close() throws Exception {
        Unirest.shutdown();
    }

    // REQUESTS

    @Override
    public String GET(@NonNull String url, Map<String, String> params) throws IOException {
        try {
            GetRequest request = Unirest.get(properties.get(PropertiesClient.JIRA_HOME) + url);
            params.forEach(request::queryString);
            return request.basicAuth(properties.get(PropertiesClient.LOGIN),
                properties.get(PropertiesClient.PASSWORD)).asString().getBody();
        } catch (UnirestException e) {
            throw new IOException(e);
        }
    }

    @Override
    public String GET(@NonNull String url) throws IOException {
        return GET(url, new HashMap<>());
    }

    @Override
    public String test(String t) {
        return "prod";
    }
}