package org.ripreal.textclassifier2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.ripreal.textclassifier2.model.Characteristic;
import org.ripreal.textclassifier2.model.CharacteristicValue;
import org.ripreal.textclassifier2.model.ClassifiableFactory;
import org.ripreal.textclassifier2.model.ClassifiableText;

import java.io.IOException;
import java.util.*;

public class JiraIssueReader implements AutoCloseable {


    public final static String ISSUE_PATTERN = "/rest/api/2/search";
    public final static String ISSUE_TYPES = "/rest/api/2/issuetype";
    public final static String PROJECT_TYPES = "/rest/api/2/project";
    private final String Projects = "AI,IC,IT,AGR";
    private final String fields = "summary,project,reporter,issuetype,description";

    private final ObjectMapper mapper;
    private final JiraClient client;
    private final ClassifiableFactory textFactory;
    private List<ClassifiableText> texts = new ArrayList<>();
    private Characteristic issueType;
    private Characteristic projectType;


    private final int maxResult;
    private int position = 0;
    private int upperLimit = Integer.MAX_VALUE;
    private boolean hasNext = true;

    public JiraIssueReader(JiraClient client, ObjectMapper mapper, int maxResult, ClassifiableFactory
            textFactory) {

        if (client == null || mapper == null || textFactory == null)
            throw new IllegalArgumentException();

        this.client = client;
        this.mapper = mapper;
        this.maxResult = maxResult;
        this.textFactory = textFactory;
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    }

    // SEQUENTIAL READING

    public boolean next() throws IOException {

        texts.clear();

        if (!hasNext) {
            return false;
        }

        try {
            if (issueType == null) {
                // Jira request always returns all possible variants so do cashing when first iterate through
                String jsonIssueTypes = queryIssueTypes();
                issueType = parseJsonAsCharacteristic(jsonIssueTypes, "issueType");
            }

            if (projectType == null) {
                // Jira request always returns all possible variants so do cashing when first iterate through
                String jsonProjectTypes = queryProjectTypes();
                projectType = parseJsonAsCharacteristic(jsonProjectTypes, "projectType");
            }

            String issuesJson = queryIssues();

            if (!parseJsonAsClassifiableText(issueType, projectType, issuesJson))
                return false;

            position += texts.size();
        } catch (Exception e) {
            // skip elements
            position += maxResult;
            throw new IOException(e);
        }
        hasNext = upperLimit > position;
        return true;
    }

    public List<ClassifiableText> getTexts() {return this.texts;}

    public Set<Characteristic> getCharacteristics() {
        Set<Characteristic> characteristics = new HashSet<>();
        characteristics.add(issueType);
        characteristics.add(projectType);
        return characteristics;
    }

    public int getPosition() {return this.position;}

    public void setPosition(int position) {this.position = position; }

    public void setUpperLimit(int upperLimit) {this.upperLimit = upperLimit; }

    @Override
    public void close() throws Exception {
        client.close();
    }

    // PARSING HTTP RESPONSES

    private boolean parseJsonAsClassifiableText(Characteristic issueChar, Characteristic projectChar, String json) throws
            IOException {

        JsonNode jsonNode = mapper.readTree(json);
        JsonNode issues = jsonNode.get("issues");

        for (JsonNode issue : issues) {

            String descrHtml = issue.get("renderedFields").get("description").textValue();
            if (descrHtml == null)
                continue; // issue description can be empty

            String descr = Jsoup.parse(descrHtml).text();

            Set<CharacteristicValue> characteristics = new HashSet<>();
            characteristics.add(characteristicFromIssue(issue, "issuetype", issueChar));
            characteristics.add(characteristicFromIssue(issue, "project", projectChar));

            ClassifiableText text = textFactory.newClassifiableText(descr, characteristics);

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

        int orderNumber = 1;
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

    // JIRA HTTP REQESTS

    private String queryIssues() throws IOException {

        Map<String, String> params = new HashMap<>();

       // params.put("jql", "project in (" + Projects + ")");
        params.put("fields", fields);
        params.put("maxResults", String.valueOf((Math.min(maxResult, upperLimit))));
        params.put("startAt", String.valueOf(position));
        params.put("expand", "names,renderedFields"); // HTML for description fields

        return client.GET(ISSUE_PATTERN, params);
    }

    private String queryIssueTypes() throws IOException {
        return client.GET(ISSUE_TYPES);
    }

    private String queryProjectTypes() throws IOException {
        return client.GET(PROJECT_TYPES);
    }

}
