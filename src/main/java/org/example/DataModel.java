package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModel {
    private String featureName;
    private List<ScenarioData> scenarios;
    private Map<String, String> envVariables;
    public DataModel(String featureName, List<ScenarioData> scenarios, HashMap envVariables) {
        this.featureName = featureName;
        this.scenarios = scenarios;
        this.envVariables = envVariables;
    }

    public String getFeatureName() {
        return featureName;
    }

    public List<ScenarioData> getScenarios() {
        return scenarios;
    }
    public Map<String, String> getEnvVariables() {
        return envVariables;
    }
}
