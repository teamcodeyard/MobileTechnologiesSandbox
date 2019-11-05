package eu.codeyard.sandbox.model;

import com.google.gson.annotations.SerializedName;

import io.trieulh.simplegenericadapter.diff.Diffable;

public class Movie implements Diffable {

    @SerializedName("Title")
    protected String title;

    @SerializedName("Year")
    protected String year;

    protected String imdbID;

    @SerializedName("Type")
    protected String type;

    @SerializedName("Poster")
    protected String poster;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", type='" + type + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }

    @Override
    public boolean areContentTheSame(Diffable diffable) {
        return diffable.getUniqueIdentifier() == getUniqueIdentifier();
    }

    @Override
    public long getUniqueIdentifier() {
        return imdbID.hashCode();
    }
}
