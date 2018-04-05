package org.ripreal.textclassifier2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;
import org.ripreal.textclassifier2.model.modelimp.DefCharacteristicValue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class JiraIssueReader implements AutoCloseable {

    private final String ISSUE_PATTERN = "/rest/api/2/search";
    private final String ISSUE_TYPES = "/rest/api/2/issuetype";
    private final String PROJECT_TYPES = "/rest/api/2/project";
    private final String Projects = "AI,IC,IT,AGR";
    private final String fields = "summary,project,reporter,issuetype,description";
    private final ObjectMapper mapper = new ObjectMapper();

    private final JiraClient client;
    private final int maxResult;
    private final ClassifiableFactory textFactory;

    //The index into the buffer currently held by the Reader
    @Setter
    private int position = 0;
    @Setter
    private int upperLimit = -1;
    @Getter
    private List<ClassifiableText> texts = new ArrayList<>();

    public JiraIssueReader(JiraClient client, int maxResult, ClassifiableFactory textFactory) {
        this.client = client;
        this.maxResult = maxResult;
        this.textFactory = textFactory;
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    }

    public boolean next() throws IOException {

        try {
            String jsonIssueTypes = queryIssueTypes();
            Characteristic issue = parseJsonAsCharacteristic(jsonIssueTypes, "issueType");

            String jsonProjectTypes = queryProjectTypes();
            Characteristic project = parseJsonAsCharacteristic(jsonProjectTypes, "projectType");

            String issuesJson = queryIssues();

            if (!parseJsonAsClassifiableText(issue, project, issuesJson))
                return false;

            position += maxResult;
        } catch (Exception e) {
            // skip elements
            position += maxResult;
            throw new IOException(e);
        }
        return upperLimit == -1 || position > upperLimit;
    }

    @Override
    public void close() throws Exception {
        client.close();
    }

    private boolean parseJsonAsClassifiableText(Characteristic issueChar, Characteristic projectChar, String json) throws
            IOException {

        JsonNode jsonNode = mapper.readTree(json);
        JsonNode issues = jsonNode.get("issues");

        texts.clear();

        for (JsonNode issue : issues) {

            String descr = issue.get("fields").get("description").textValue();
            if (descr == null)
                continue; // issue description can be empty

            Set<CharacteristicValue> characteristics = new HashSet<>();
            characteristics.add(characteristicFromIssue(issue, "issuetype", issueChar));
            characteristics.add(characteristicFromIssue(issue, "project", projectChar));

            ClassifiableText text = textFactory.newClassifiableText(
                    descr, characteristics);

            texts.add(text);
        }

        return (issues.size() > 0);
    }

    private Characteristic parseJsonAsCharacteristic(String json, String charName) throws IOException {
        Characteristic characteristic = textFactory.newCharacteristic(charName);
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

    private CharacteristicValue characteristicFromIssue(JsonNode issue, String issueField,
                                                        Characteristic characteristic) throws IOException {

        JsonNode fields = issue.get("fields");

        if (fields == null)
            throw new IOException("There is no \"fields\" property in the json");

        String issueType = fields.get(issueField).get("name").toString();

        if (issueType == null)
            throw new IOException("There is no \"name\" property in the json");

        CharacteristicValue issueValue = CharacteristicUtils.findByValue(
                characteristic.getPossibleValues(), issueType,
                (val) -> textFactory.newCharacteristicValue(val, 0, characteristic));
        return (issueValue != null) ? issueValue : textFactory.newCharacteristicValue(issueType, 0, characteristic);
    }

    private String queryIssues() throws IOException {

        Map<String, String> params = new HashMap<>();

        params.put("jql", "project in (" + Projects + ")");
        params.put("fields", fields);
        params.put("maxResults", String.valueOf(maxResult));
        params.put("startAt", String.valueOf(position));

        return client.GET(ISSUE_PATTERN, params);
    }

    private String queryIssueTypes() throws IOException {
        return client.GET(ISSUE_TYPES);
    }

    private String queryProjectTypes() throws IOException {
        return client.GET(PROJECT_TYPES);
    }
}
