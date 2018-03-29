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
import org.ripreal.textclassifier2.model.ClassifiableFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.ripreal.textclassifier2.PropertiesClient.JIRA_HOME;
import static org.ripreal.textclassifier2.PropertiesClient.PROXY;

@Slf4j
public class JiraBasicAuthClient {

    private final CommonHttpClient http = HttpClients.anApacheClient();
    private final Map<String, String> properties;
    private final String JIRA_ERROR = "Jira error";
    private final ClassifiableFactory factory;

    public JiraBasicAuthClient(ClassifiableFactory factory, PropertiesClient propertiesClient) {
        this.properties = propertiesClient.getPropertiesOrDefaults();
        this.factory = factory;
    }

    private void connectJira() throws MalformedURLException {
        http.with(Proxy.proxy(new URL((properties.get(PROXY)))))
            .with(BasicAuthCredentials.basicAuth(Username.username(properties.get(PropertiesClient.LOGIN)),
                    Password.password(properties.get(PropertiesClient.PASSOWRD)), new URL(properties.get(JIRA_HOME))
            ));
    }

    public String getCLassifiableTexts() throws Exception {
        connectJira();
        return http.get(new URL(properties.get(JIRA_HOME) + "/rest/api/2/issue/ag-52"))
            .getContent().asString();

    }

    public JiraIssueReader reader(int limit) throws Exception {
        connectJira();
        return new JiraIssueReader(http, limit, properties.get(JIRA_HOME), factory);
    }

}