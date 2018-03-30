package org.ripreal.textclassifier2;

import bad.robot.http.CommonHttpClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.modelimp.DefCharacteristicValue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JiraIssueReader implements AutoCloseable {

    private final String ISSUE_PATTERN = "/rest/api/2/search";
    private final String ISSUE_TYPES = "/rest/api/2/issuetype";
    private final String PROJECT_TYPES = "/rest/api/2/issuetype";
    private final String Projects = "AI,IC,IT,AGR";
    private final String fields = "summary,project,reporter,issuetype,description";
    private final ObjectMapper mapper = new ObjectMapper();

    private final CommonHttpClient http;
    private final int maxResult;
    private final String JIRA_HOME;
    private final ClassifiableFactory textFactory;

    //The index into the buffer currently held by the Reader
    private int position = 0;

    @Getter
    private List<ClassifiableText> texts;
    @Getter
    private Set<Characteristic> characteristics;

    public JiraIssueReader(CommonHttpClient http, int maxResult, String jira_home, ClassifiableFactory textFactory) {
        this.http = http;
        this.maxResult = maxResult;
        JIRA_HOME = jira_home;
        this.textFactory = textFactory;

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    }

    public boolean next() throws IOException {

        String jsonIssueTypes = queryIssueTypes();
        Characteristic issue = parseJsonAsIssueCharacteristic(jsonIssueTypes);

        String jsonProjectTypes = queryProjectTypes();
        Characteristic project = parseJsonAsIssueCharacteristic(jsonIssueTypes);

        String issuesJson = queryIssues();
        parseJsonAsClassifiableText(issue, project, issuesJson);

        position+=maxResult;

        return true;
    }

    private void parseJsonAsClassifiableText(Characteristic issueChar, Characteristic projectChar, String json) throws
            IOException {

        JsonNode jsonNode = mapper.readTree(json);
        JsonNode issues = jsonNode.get("issues");

        for (JsonNode issue : issues) {
            JsonNode fields = issue.get("fields");

            String issueType = fields.get("issueType").get("name").toString();
            CharacteristicUtils.findByValue(issueChar.getPossibleValues(), issueType, (val) -> textFactory.newCharacteristicValue(
                val, 0, issueChar));

            ClassifiableText text = textFactory.newClassifiableText("");
        }
        textFactory.newClassifiableText(jsonNode.get("color").asText());

    }

    @Override
    public void close() throws Exception {
        http.shutdown();
    }

    private String queryIssues() throws MalformedURLException {

        List<String> params = new ArrayList<>();
        params.add("jql=project%20in%20(" + Projects + ")");
        params.add("fields=" + fields);
        params.add("maxResults=" + maxResult);
        params.add("startAt=" + position);

        String paramsLine = "?" + String.join("&", params);

        return http.get(new URL(JIRA_HOME + ISSUE_PATTERN + paramsLine))
            .getContent().asString();
    }

    private String queryIssueTypes() throws MalformedURLException {
         return http.get(new URL(JIRA_HOME + ISSUE_TYPES))
                .getContent().asString();
    }

    private String queryProjectTypes() throws MalformedURLException {
        return http.get(new URL(JIRA_HOME + ISSUE_TYPES))
                .getContent().asString();
    }

    private Characteristic parseJsonAsIssueCharacteristic(String json) throws IOException {
        Characteristic characteristic = textFactory.newCharacteristic("IssueType");
        JsonNode charValues = mapper.readTree(json);
        for(JsonNode val : charValues) {
            characteristic.addPossibleValue(
                textFactory.newCharacteristicValue(
                    val.get("name").toString(), 0, characteristic)
            );
        }

        int orderNumber = 0;
        for (CharacteristicValue val : characteristic.getPossibleValues())
            val.setOrderNumber(++orderNumber);

        return characteristic;
    }
}
