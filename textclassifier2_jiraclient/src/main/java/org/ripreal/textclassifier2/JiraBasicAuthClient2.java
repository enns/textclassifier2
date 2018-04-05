package org.ripreal.textclassifier2;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.*;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JiraBasicAuthClient2 implements JiraClient{

    private  Map<String, String> properties;

    public JiraBasicAuthClient2(@NonNull PropertiesClient propertiesClient) {

       this.properties = propertiesClient.getPropertiesOrDefaults();

        if (!(properties.get(PropertiesClient.PROXY_PORT).isEmpty()
            && properties.get(PropertiesClient.PROXY_SERVER).isEmpty())) {

            Unirest.setProxy(new HttpHost(properties.get(PropertiesClient.PROXY_SERVER),
                Integer.parseInt(properties.get(PropertiesClient.PROXY_PORT))));
        }
    }

    @Override
    public JiraIssueReader issueReader(int maxResult, @NonNull ClassifiableFactory textFactory) {
        return new JiraIssueReader(this, maxResult, textFactory);
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

}