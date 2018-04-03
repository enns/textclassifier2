package org.ripreal.textclassifier2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface JiraClient {
    String GET(String url, Map<String,String> params) throws URISyntaxException, IOException;
    String GET(String url);
}
