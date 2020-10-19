package com.example.trendingrepos.Repository;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.trendingrepos.Models.RepoModel;

import java.util.List;

@Dao
public interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RepoModel repoModel);

    @Query("SELECT * from repo_info ORDER BY id ASC")
    LiveData<List<RepoModel>> getAllRepos();

    @Query("DELETE FROM repo_info")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(List<RepoModel> repoModel);

    @Query("SELECT * from repo_info ORDER BY id ASC")
    List<RepoModel> getOfflineRepos();

}
