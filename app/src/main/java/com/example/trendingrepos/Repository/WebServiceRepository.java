package com.example.trendingrepos.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trendingrepos.Models.RepoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebServiceRepository {

    Application application;
    public  WebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<RepoModel> webserviceResponseList = new ArrayList<>();

 public LiveData<List<RepoModel>> providesWebService() {

     final MutableLiveData<List<RepoModel>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         APIService service = retrofit.create(APIService.class);
        //  response = service.makeRequest().execute().body();
         service.makeRequest().enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 RepoRoomDBRepository postRoomDBRepository = new RepoRoomDBRepository(application);
                 postRoomDBRepository.insertRepos(webserviceResponseList);
                 data.setValue(webserviceResponseList);

             }

             @Override
             public void onFailure(Call<String> call, Throwable t) {
                 Log.d("Repository","Failed:::");
             }
         });
     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<RepoModel> parseJson(String response) {

        List<RepoModel> apiResults = new ArrayList<>();

        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                RepoModel repoModel = new RepoModel();
                repoModel.setAuthor(object.getString("author"));
                repoModel.setName(object.getString("name"));
                repoModel.setAvatar(object.getString("avatar"));
                repoModel.setDescription(object.getString("description"));
                repoModel.setLanguage(object.getString("language"));
                repoModel.setLanguageColor(object.getString("languageColor"));
                repoModel.setStars(object.getString("stars"));
                apiResults.add(repoModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
