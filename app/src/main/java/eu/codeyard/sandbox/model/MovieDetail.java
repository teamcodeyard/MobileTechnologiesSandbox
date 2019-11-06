package eu.codeyard.sandbox.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDetail extends Movie {

    @SerializedName("Released")
    protected String released;

    @SerializedName("Runtime")
    protected String runtime;

    @SerializedName("Genre")
    protected String genre;

    @SerializedName("Director")
    protected String director;

    @SerializedName("Writer")
    protected String writer;

    @SerializedName("Actors")
    protected String actors;

    @SerializedName("Plot")
    protected String plot;

    @SerializedName("Language")
    protected String language;

    @SerializedName("Country")
    protected String country;

    @SerializedName("Awards")
    protected String awards;

    @SerializedName("Ratings")
    protected List<MovieRating> ratings = new ArrayList<>();

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public List<MovieRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<MovieRating> ratings) {
        this.ratings = ratings;
    }
}
