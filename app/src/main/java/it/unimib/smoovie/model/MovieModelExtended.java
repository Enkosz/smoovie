package it.unimib.smoovie.model;

import com.google.gson.annotations.SerializedName;

public class MovieModelExtended extends MovieModelCompact {

    public String status;

    @SerializedName("vote_average")
    public Double voteAverage;

    @SerializedName("vote_count")
    public Double voteCount;

    public Integer runtime;

    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

    public Integer getRuntimeHours() {
        return runtime / 60;
    }

    public Integer getRuntimeMinutes() {
        return runtime % 60;
    }
}
