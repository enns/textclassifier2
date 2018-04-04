package org.ripreal.textclassifier2;

import com.sun.org.apache.xpath.internal.operations.String;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.ripreal.textclassifier2.model.ClassifiableFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JiraBasicAuthClient implements JiraClient{

    private final CloseableHttpClient httpclient;
    private final String JIRA_ERROR = "Jira error";
    private final HttpHost target;

    public JiraBasicAuthClient(@NonNull PropertiesClient propertiesClient) {

        Map<String, String > properties = propertiesClient.getPropertiesOrDefaults();

        HttpClientBuilder builder = HttpClients.custom();

        if (!(properties.get(PropertiesClient.PROXY_PORT).isEmpty()
            && properties.get(PropertiesClient.PROXY_SERVER).isEmpty())) {
            HttpHost proxy = new HttpHost(
                    properties.get(PropertiesClient.PROXY_SERVER),
                    Integer.parseInt(properties.get(PropertiesClient.PROXY_PORT)));

            builder.setProxy(proxy);
        }

        this.target = new HttpHost(properties.get(PropertiesClient.JIRA_HOME),
            7890, "http");

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(target.getHostName(), target.getPort()),
            new UsernamePasswordCredentials(properties.get(PropertiesClient.LOGIN), PropertiesClient.PASSWORD));

        httpclient = builder.setDefaultCredentialsProvider(credsProvider).build();
    }

    public JiraIssueReader issueReader(int maxResult, @NonNull ClassifiableFactory textFactory) {
        return new JiraIssueReader(this, maxResult, textFactory);
    }

    @Override
    public String GET(@NonNull String url, Map<String, String> params) throws IOException {
        URIBuilder builder = new URIBuilder();
        params.forEach(builder::setParameter);
        builder.setHost(target.getHostName())
                .setPort(target.getPort())
                .setScheme(target.getSchemeName())
                .setPath(url);
        try {
            HttpGet httpget = new HttpGet(builder.build());
            HttpResponse response = httpclient.execute(httpget);
            return new String(response.getEntity().getContent());
        } catch (Exception e) {
            throw  new IOException(e);
        }
    }

    @Override
    public String GET(@NonNull String url) throws IOException {
        return GET(url, new HashMap<>());
    }

    @Override
    public void close() throws Exception {
        this.httpclient.close();
    }
}