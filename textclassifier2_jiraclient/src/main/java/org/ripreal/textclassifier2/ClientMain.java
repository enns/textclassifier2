package org.ripreal.textclassifier2;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

import static bad.robot.http.configuration.BasicAuthCredentials.basicAuth;

public class ClientMain {

    final static Logger logger = Logger.getLogger(ClientMain.class);

    public static void main(String[] args) throws Exception {
        JiraBasicAuthClient client = new JiraBasicAuthClient(new PropertiesClient());
        client.getCLassifiableTexts(null);
    }
}
