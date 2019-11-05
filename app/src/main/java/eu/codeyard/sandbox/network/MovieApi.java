package eu.codeyard.sandbox.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit.Ok3Client;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.concurrent.TimeUnit;

import eu.codeyard.sandbox.Application;
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

    public MovieApi() {

    }

    @AfterInject
    void init() {

        String baseUrl = Application.ENDPOINT_ADDRESS;

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(new GsonConverter(gson))
                .setClient(new Ok3Client(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        if (request != null) {
                            request.addHeader("X-Client-Signature", Application.API_TOKEN);
                        }
                    }
                })
                .build();

        System.setProperty("http.keepAlive", "false");

        service = restAdapter.create(MovieWebService.class);

    }

    public void search(String term, int page, Callback<MovieSearchResult> callback) {
        service.search(term, page, Application.API_TOKEN, callback);
    }

}