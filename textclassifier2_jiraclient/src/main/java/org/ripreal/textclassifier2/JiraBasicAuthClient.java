package org.ripreal.textclassifier2;

import bad.robot.http.CommonHttpClient;
import bad.robot.http.HttpClient;
import bad.robot.http.HttpClients;
import bad.robot.http.HttpResponse;
import bad.robot.http.configuration.BasicAuthCredentials;
import bad.robot.http.configuration.Password;
import bad.robot.http.configuration.Proxy;
import bad.robot.http.configuration.Username;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
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
import org.apache.http.impl.client.HttpClients;
import org.ripreal.textclassifier2.model.ClassifiableFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.ripreal.textclassifier2.PropertiesClient.JIRA_HOME;
import static org.ripreal.textclassifier2.PropertiesClient.PROXY;

@Slf4j
public class JiraBasicAuthClient implements JiraClient{

    private final CloseableHttpClient httpclient;
    private Map<String, String> properties;
    private final String JIRA_ERROR = "Jira error";

    public JiraBasicAuthClient(PropertiesClient propertiesClient) {

        this.properties = propertiesClient.getPropertiesOrDefaults();

        HttpHost target = new HttpHost( this.properties.get(PropertiesClient.JIRA_HOME),
                7890, "http");

        HttpHost proxy = new HttpHost(
                properties.get(PropertiesClient.PROXY_SERVER),
                Integer.parseInt(properties.get(PropertiesClient.PROXY_PORT)));

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(target.getHostName(), target.getPort()),
            new UsernamePasswordCredentials(this.properties.get(PropertiesClient.LOGIN), PropertiesClient.PASSOWRD));

        this.httpclient = HttpClients.custom()
            .setProxy(proxy)
            .setDefaultCredentialsProvider(credsProvider).build();
    }


    @Override
    public String GET(String url, Map<String, String> params) throws IOException {

        URIBuilder builder = new URIBuilder();

        params.entrySet().forEach(param -> builder.setParameter(
                param.getKey(), param.getValue()));

        String result = "";
        try {
            HttpGet httpget = new HttpGet(builder.build());
            httpclient.execute(httpget).co;
        } catch (URISyntaxException e) {
            throw  new IOException(e);
        }

    }

    @Override
    public String GET(String url) {
        return null;
    }
}