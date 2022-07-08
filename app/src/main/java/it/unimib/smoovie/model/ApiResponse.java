package it.unimib.smoovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse<T> {

    public Long id;

    @SerializedName("results")
    public List<T> movies;

    @SerializedName("total_pages")
    public Long totalPages;

    @SerializedName("total_results")
    public Long totalResults;
}
