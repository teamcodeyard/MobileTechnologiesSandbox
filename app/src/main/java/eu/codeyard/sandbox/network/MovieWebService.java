package eu.codeyard.sandbox.network;


import java.util.List;

import eu.codeyard.sandbox.model.Movie;
import eu.codeyard.sandbox.model.MovieSearchResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * REST-es kommunikációt leíró interfész.
 */
public interface MovieWebService {

    @GET("/")
    void search(
            @Query("s") String term,
            @Query("page") int page,
            @Query("apikey") String apiKey,
            Callback<MovieSearchResult> callback
    );

}