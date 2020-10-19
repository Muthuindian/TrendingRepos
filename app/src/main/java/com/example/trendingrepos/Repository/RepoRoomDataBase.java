package com.example.trendingrepos.Repository;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.trendingrepos.Models.RepoModel;

@Database(entities = {RepoModel.class}, version = 1)
public abstract class RepoRoomDataBase extends RoomDatabase {
    public abstract RepoDao repoDao();

    private static RepoRoomDataBase INSTANCE;


    public static RepoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RepoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RepoRoomDataBase.class, "repos_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}
