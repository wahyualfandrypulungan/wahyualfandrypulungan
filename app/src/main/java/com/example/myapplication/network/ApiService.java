package com.example.myapplication.network;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.model.Movie;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private ApiInterface apiInterface;

    public ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                 .build();
        apiInterface=retrofit.create((ApiInterface.class));
    }
    private OkHttpClient builder(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
        okHttpClient.connectTimeout(20, TimeUnit.SECONDS );
        okHttpClient.writeTimeout(20,TimeUnit.SECONDS);
        okHttpClient.readTimeout(80,TimeUnit.SECONDS);

        if(BuildConfig.DEBUG){
            okHttpClient.addInterceptor(interceptor());

        }
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", Constant.MOVIE_API_KEY)
                        .addQueryParameter("languange", Constant.LANG_EN)
                        .build();
                return chain.proceed(request);


            }
        });
        return okHttpClient.build();
    }

    private static HttpLoggingInterceptor interceptor() {
        HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public void getPopularMovie(int page, retrofit2.Callback callback) {
        apiInterface.popularMovies(page).enqueue((retrofit2.Callback<Movie>)callback);
    }
}
