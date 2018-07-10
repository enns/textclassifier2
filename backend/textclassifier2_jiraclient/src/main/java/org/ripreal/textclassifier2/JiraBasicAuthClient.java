package org.ripreal.textclassifier2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpHost;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP client to access Jira via basic authentication.
 * <p>
 * Notice within jira basic authentication must be allowed
*/
public class JiraBasicAuthClient implements JiraClient{

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JiraBasicAuthClient.class);
    private  Map<String, String> properties;
    private final ObjectMapper mapper = new ObjectMapper().
        configure(SerializationFeature.WRAP_ROOT_VALUE, false);

    /**
    * Create instance with PropertiesClient. The valid properties are:
    * <br> JIRA_HOME (for example server:port)
    * <br> LOGIN
    * <br> PASSWORD
    */
    public JiraBasicAuthClient(PropertiesClient propertiesClient) {

       this.properties = propertiesClient.getPropertiesOrDefaults();

        if (!(properties.get(PropertiesClient.PROXY_PORT).isEmpty()
            && properties.get(PropertiesClient.PROXY_SERVER).isEmpty())) {

            Unirest.setProxy(new HttpHost(properties.get(PropertiesClient.PROXY_SERVER),
                Integer.parseInt(properties.get(PropertiesClient.PROXY_PORT))));
        }
    }

    /**
    * Create a instance with default PropertiesClient.
    */
    public JiraBasicAuthClient() throws Exception {
        new JiraBasicAuthClient(new PropertiesClient());
    }

    /**
    * Sream reader for reading jira data to output stream
    *
    * @return newIssueReader instance supplied with this jirta client/
    */
    @Override
    public JiraIssueReader newIssueReader(int maxResult, ClassifiableFactory textFactory) {
        return new JiraIssueReader(this, mapper, maxResult, textFactory);
    }

    /**
    * Sream writer that write data from issue reader to output stream
    *
    * @return newIssueReader instance supplied with this jirta client/
    */
    @Override
    public JiraIssueWriter newIssueWriter() {
        return new JiraIssueWriter(this, mapper);
    }

    @Override
    public void close() throws Exception {
        Unirest.shutdown();
    }

    // REQUESTS

     /**
     * Send a http GET request.
     *
     * @param url accessing resource without base jira host
     * @param key-value pair parameterers passed within url string
     *
     * @return json string containing in a response body.
     */
    @Override
    public String GET(String url, Map<String, String> params) throws IOException {
        try {
            GetRequest request = Unirest.get(properties.get(PropertiesClient.JIRA_HOME) + url);
            params.forEach(request::queryString);
            return request.basicAuth(properties.get(PropertiesClient.LOGIN),
                properties.get(PropertiesClient.PASSWORD)).asString().getBody();
        } catch (UnirestException e) {
            throw new IOException(e);
        }
    }

     /**
     * Send a http GET request with no parameters
     *
     * @return json string containing in a response body.
     */

    @Override
    public String GET(String url) throws IOException {
        return GET(url, new HashMap<>());
    }

}
