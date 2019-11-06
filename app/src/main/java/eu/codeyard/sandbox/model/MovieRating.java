package eu.codeyard.sandbox.model;

import com.google.gson.annotations.SerializedName;

public class MovieRating {

    @SerializedName("Source")
    protected String source;

    @SerializedName("Value")
    protected String value;

    public MovieRating() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
