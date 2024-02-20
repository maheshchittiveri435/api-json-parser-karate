package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JsonToFeatureConverter {

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/collection_jsons/REST API basics.postman_collection.json"));
            String envType = loadEnvironment();
            if (envType != null) {
                System.out.println("Environment: " + envType);
            } else {
                System.out.println("Failed to load environment.");
            }
            String envFile=null;
            if(envType.equalsIgnoreCase("QA"))
                envFile = "src/main/resources/environment_QA.json";
            else envFile = "src/main/resources/environment_DEV.json";
            JsonNode envJsonNode = objectMapper.readTree(new File(envFile));
            // Construct Cucumber feature file content
            FeatureFileGeneratorWithFreeMarker.featureGenerator(jsonNode, envJsonNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String loadEnvironment() {
        Properties properties = new Properties();
        try (InputStream input = FeatureFileGeneratorWithFreeMarker.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }

            // Load the properties file
            properties.load(input);

            // Retrieve the value of the 'environment' property
            return properties.getProperty("environment");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
