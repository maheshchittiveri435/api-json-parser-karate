    package org.example;
    import com.fasterxml.jackson.databind.JsonNode;
    import freemarker.template.Configuration;
    import freemarker.template.Template;
    import freemarker.template.TemplateExceptionHandler;

    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.StringWriter;
    import java.util.*;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class FeatureFileGeneratorWithFreeMarker {

        public static void featureGenerator(JsonNode jsonNode, JsonNode envJsonNode) {
            try {
                System.out.println(jsonNode.toString());
                System.out.println(envJsonNode.toString());

                // Initialize FreeMarker configuration
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
                cfg.setClassLoaderForTemplateLoading(FeatureFileGeneratorWithFreeMarker.class.getClassLoader(), "/templates");
                cfg.setDefaultEncoding("UTF-8");
                cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

                // Load the FreeMarker template
                Template template = cfg.getTemplate("template.ftl");

                String featureName = jsonNode.findValue("name").asText();
                JsonNode items = jsonNode.get("item");

                // Prepare data model
                DataModel dataModel = new DataModel(featureName, createScenarios(items, envJsonNode), createEnvVariables(envJsonNode));

                // Process the template
                StringWriter resultWriter = new StringWriter();
                template.process(dataModel, resultWriter);

                // Print or save the generated content
                System.out.println(resultWriter.toString());

                // Save the content to a file (optional)
                try (FileWriter fileWriter = new FileWriter("src/test/java/examples/users/API.feature")) {
                    fileWriter.write(resultWriter.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static HashMap createEnvVariables(JsonNode envJsonNode) {
            JsonNode valuesNode = envJsonNode.get("values");

            HashMap<String, String> keyValueMap = null;
            if (valuesNode != null && valuesNode.isArray()) {
                Iterator<JsonNode> valuesIterator = valuesNode.elements();

                // Create a HashMap to store key-value pairs
                keyValueMap = new HashMap<>();

                while (valuesIterator.hasNext()) {
                    JsonNode valueNode = valuesIterator.next();
                    String key = valueNode.get("key").asText();
                    String value = valueNode.get("value").asText();

                    keyValueMap.put(key, value);
                }

                // Print or use the key-value pairs in the HashMap
                for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                }

            }
            return keyValueMap;
        }


        private static List<ScenarioData> createScenarios(JsonNode items, JsonNode envJsonNode) {
            List<ScenarioData> scenarios = new ArrayList<>();
            if (items.isArray()) {
                for (JsonNode item : items) {
                    String itemName = item.get("name").asText();
                    String raw = item.get("request").get("url").get("raw").asText();
                    String method = item.get("request").get("method").asText();

                    JsonNode events = item.get("event");
                    String exec = "";

                    for (JsonNode event : events) {
                        JsonNode script = event.get("script");
                        if (script != null) {
                            JsonNode execNode = script.get("exec");
                            if (execNode != null && execNode.isArray()) {
                                for (JsonNode exe : execNode) {
                                    if (exe.toString().contains("pm.response.text())")) {
                                        exec = exe.asText().trim();
                                        break; // exit loop once found
                                    }
                                }
                            }
                        }
                    }
                    HashMap keyValueMap = createEnvVariables(envJsonNode);
                    String extractedString = "";
                    try {
                        String pattern = "pm\\.expect\\(pm\\.response\\.text\\(\\)\\)\\.to\\.include\\(\"(.*?)\"\\)";
                        Pattern regex = Pattern.compile(pattern);
                        Matcher matcher = regex.matcher(exec);
                        if (matcher.find()) {
                            extractedString = matcher.group(1);
                            System.out.println("Extracted String: " + extractedString);
                            scenarios.add(new ScenarioData(itemName, raw, method, extractedString, keyValueMap));
                        } else {
                            scenarios.add(new ScenarioData(itemName, raw, method, null, keyValueMap));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        scenarios.add(new ScenarioData(itemName, raw, method, null, keyValueMap));
                    }
                }
            }
            return scenarios;
        }
    }

