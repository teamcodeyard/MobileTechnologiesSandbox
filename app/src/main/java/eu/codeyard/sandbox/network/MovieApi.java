package eu.codeyard.sandbox.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit.Ok3Client;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.concurrent.TimeUnit;

import eu.codeyard.sandbox.Application;
import eu.codeyard.sandbox.model.MovieDetail;
import eu.codeyard.sandbox.model.MovieSearchResult;
import okhttp3.OkHttpClient;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@EBean(scope = EBean.Scope.Singleton)
public class MovieApi {

    private MovieWebService service;

    @App
    Application app;

    @AfterInject
    void init() {
        String baseUrl = Application.ENDPOINT_ADDRESS;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        System.setProperty("http.keepAlive", "false");

        service = restAdapter.create(MovieWebService.class);
    }

    public void search(String term, int page, Callback<MovieSearchResult> callback) {
        service.search(term, page, Application.API_TOKEN, callback);
    }

    public void getMovie(String imdbId, Callback<MovieDetail> callback) {
        service.getMovie(imdbId, Application.API_TOKEN, callback);
    }

}