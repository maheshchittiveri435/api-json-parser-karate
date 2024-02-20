package org.example;

import java.util.HashMap;

public class ScenarioData {

    private final String url;
    private String name;
    private String method;
    private String text;
    private HashMap envHM;

    public ScenarioData(String name, String url, String method, String text, HashMap envHM) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.text = text;
        this.envHM = envHM;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
    public String getMethod() {
        return method;
    }

    public String getText() {
        return text;
    }
    public HashMap getEnvHM(){
        return envHM;
    }
}
