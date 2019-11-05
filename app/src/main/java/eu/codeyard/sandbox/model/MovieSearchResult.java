package eu.codeyard.sandbox.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchResult {

    @SerializedName("Search")
    protected List<Movie> searchResult = new ArrayList<>();

    public List<Movie> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Movie> searchResult) {
        this.searchResult = searchResult;
    }

    @Override
    public String toString() {
        return "MovieSearchResult{" +
                "searchResult=" + searchResult +
                '}';
    }
}
