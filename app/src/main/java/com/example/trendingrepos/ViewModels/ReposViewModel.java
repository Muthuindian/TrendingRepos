package com.example.trendingrepos.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.trendingrepos.Models.RepoModel;
import com.example.trendingrepos.Repository.RepoRoomDBRepository;
import com.example.trendingrepos.Repository.WebServiceRepository;

import java.util.List;

public class ReposViewModel extends AndroidViewModel {

    private RepoRoomDBRepository repoRoomDBRepository;
    private LiveData<List<RepoModel>> repos;
    WebServiceRepository webServiceRepository ;
    private final LiveData<List<RepoModel>>  retroObservable;
    public ReposViewModel(Application application){
        super(application);
        repoRoomDBRepository = new RepoRoomDBRepository(application);
        webServiceRepository = new WebServiceRepository(application);
        retroObservable = webServiceRepository.providesWebService();
        repos = repoRoomDBRepository.getAllRepos();
    }

    public LiveData<List<RepoModel>> getAllRepos() {
        return repos;
    }

}
