package eu.codeyard.sandbox.event;

import eu.codeyard.sandbox.model.Movie;

public class MovieOpened {

    protected Movie movie;

    public MovieOpened(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
}
