package it.unimib.smoovie.model;

import com.google.gson.annotations.SerializedName;

public class MovieModel {

    public Long id;
    public String title;

    @SerializedName("poster_path")
    public String posterPath;
}
