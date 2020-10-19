package com.example.trendingrepos.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "repo_info")
public class RepoModel
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    @ColumnInfo(name = "author")
    @SerializedName("author")
    private String author;
    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;
    @NonNull
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    private String avatar;
    @NonNull
    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String description;
    @NonNull
    @ColumnInfo(name = "language")
    @SerializedName("language")
    private String language;
    @ColumnInfo(name = "languageColor")
    @SerializedName("languageColor")
    private String languageColor;
    @NonNull
    @ColumnInfo(name = "stars")
    @SerializedName("stars")
    private String stars;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@NonNull String avatar) {
        this.avatar = avatar;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NonNull String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    @NonNull
    public String getStars() {
        return stars;
    }

    public void setStars(@NonNull String stars) {
        this.stars = stars;
    }
}