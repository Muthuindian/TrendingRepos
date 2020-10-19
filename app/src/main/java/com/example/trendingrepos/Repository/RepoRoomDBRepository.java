package com.example.trendingrepos.Repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.trendingrepos.Models.RepoModel;

import java.util.List;

public class RepoRoomDBRepository {
    private RepoDao prepoDao;
    LiveData<List<RepoModel>> mAllPosts;
    List<RepoModel> mOfflineRepos;

    public RepoRoomDBRepository(Application application){
        RepoRoomDataBase db = RepoRoomDataBase.getDatabase(application);
        prepoDao = db.repoDao();
        mAllPosts = prepoDao.getAllRepos();
        new getOfflineAsyncTask(prepoDao).execute();
     }

    public LiveData<List<RepoModel>> getAllRepos() {
        return mAllPosts;
    }

    public List<RepoModel> getOfflineRepos() {
        return mOfflineRepos;
    }

    public void insertRepos (List<RepoModel> resultModel) {
        new insertAsyncTask(prepoDao).execute(resultModel);
    }

    public void deleteRepos () {
        new deleteAsyncTask(prepoDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<List<RepoModel>, Void, Void> {

        private RepoDao mAsyncTaskDao;

        insertAsyncTask(RepoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<RepoModel>... params) {
            mAsyncTaskDao.insertRepos(params[0]);
            return null;
        }
    }


    private class getOfflineAsyncTask extends AsyncTask<List<RepoModel>, Void, Void> {

        private RepoDao mAsyncTaskDao;

        getOfflineAsyncTask(RepoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<RepoModel>... params) {
            mOfflineRepos = mAsyncTaskDao.getOfflineRepos();
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private RepoDao mAsyncTaskDao;

        deleteAsyncTask(RepoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
