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

    public double getPercent() {
        if (value.contains("/")) {
            String[] partials = value.split("/");
            if (partials[1].equals("100")) {
                return Double.parseDouble(partials[0]);
            } else {
                return Double.parseDouble(partials[0]) * Integer.parseInt(partials[1]);
            }
        } else if (value.contains("%")) {
            return Double.parseDouble(value.replace("%", ""));
        }
        return 0;
    }
}
