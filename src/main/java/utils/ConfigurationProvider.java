package utils;

import burp.api.montoya.utilities.json.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static burp.api.montoya.utilities.json.JsonNode.jsonNode;
import static java.util.regex.Pattern.DOTALL;

public class ConfigurationProvider
{
    private static final String DESCRIPTION_REGEX = "\"description\":\"(\\{\\\\\"matchers\\\\\": *\\[.*?\\]\\})"; //TODO can't handle any sort of whitespace/newline with the curly brackets
    private final List<MatcherRecord> matchers;

    public ConfigurationProvider(Logger logging, String sessionConfig)
    {
        Pattern descriptionPattern = Pattern.compile(DESCRIPTION_REGEX, DOTALL);
        Matcher matcher = descriptionPattern.matcher(sessionConfig);

        if (matcher.find())
        {
            String ruleDescription = matcher.group(1);

            String escapedDescription = ruleDescription
                    .replaceAll("\\\\n", "\n")
                    .replaceAll("\\\\", "");

            List<JsonNode> jsonNodes = jsonNode(escapedDescription)
                    .asObject()
                    .get("matchers")
                    .asArray()
                    .asList();

            matchers = new ArrayList<>();
            jsonNodes.forEach(n -> {
                MatcherRecord m = new MatcherRecord(n.asObject().getString("match"), n.asObject().getString("replace"));
                matchers.add(m);

                logging.logToOutput("Matching: " + m.match() + ", Replacing: " + m.replace());
            });
        }
        else
        {
            throw new RuntimeException("No valid session handling rule found.");
        }
    }

    public List<MatcherRecord> getMatchers()
    {
        return matchers;
    }
}
