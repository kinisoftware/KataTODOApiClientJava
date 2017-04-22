package com.karumi.todoapiclient.routes;

import com.karumi.todoapiclient.routes.dto.PostDto;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface RoutesService {

    String BASE_ENDPOINT = "http://jsonplaceholder.typicode.com";
    String POSTS_ENDPOINT = "/posts";

    @GET(POSTS_ENDPOINT)
    Call<List<PostDto>> getAll();

    @GET(POSTS_ENDPOINT + "/{postId}")
    Call<PostDto> getById(@Path("postId") String postId);

    @POST(POSTS_ENDPOINT)
    Call<PostDto> add(@Body PostDto post);

    @PUT(POSTS_ENDPOINT + "/{postId}")
    Call<PostDto> updateById(@Path("postId") String postId, @Body PostDto post);

    @DELETE(POSTS_ENDPOINT + "/{postId}")
    Call<Void> deleteById(@Path("postId") String postId);
}
