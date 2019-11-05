package eu.codeyard.sandbox;

import org.androidannotations.annotations.EApplication;

@EApplication
public class Application extends android.app.Application {
    public static final String ENDPOINT_ADDRESS = "http://www.omdbapi.com";
    public static final String API_TOKEN = "1ade2a3";
}
