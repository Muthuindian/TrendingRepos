package com.example.trendingrepos.Repository;

import android.os.AsyncTask;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final RepoDao mDao;

    PopulateDbAsync(RepoRoomDataBase db) {
        mDao = db.repoDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        mDao.deleteAll();
        return null;
    }
}
