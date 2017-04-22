package com.karumi.todoapiclient.routes;

import com.karumi.todoapiclient.routes.dto.PostDto;
import com.karumi.todoapiclient.exception.ItemNotFoundException;
import com.karumi.todoapiclient.exception.NetworkErrorException;
import com.karumi.todoapiclient.todo.TodoApiClientException;
import com.karumi.todoapiclient.exception.UnknownErrorException;
import com.karumi.todoapiclient.interceptor.DefaultHeadersInterceptor;

import java.io.IOException;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RoutesApiClient {

    private final RoutesService routesService;

    public RoutesApiClient() {
        this(RoutesService.BASE_ENDPOINT);
    }

    public RoutesApiClient(String baseEndpoint) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.client().interceptors().add(new DefaultHeadersInterceptor());
        this.routesService = retrofit.create(RoutesService.class);
    }

    public List<PostDto> getAllPosts() throws TodoApiClientException {
        try {
            Response<List<PostDto>> response = routesService.getAll().execute();
            inspectResponseForErrors(response);
            return response.body();
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
    }

    public PostDto getPostById(String postId) throws TodoApiClientException {
        try {
            Response<PostDto> response = routesService.getById(postId).execute();
            inspectResponseForErrors(response);
            return response.body();
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
    }

    public PostDto addTask(PostDto post) throws TodoApiClientException {
        try {
            Response<PostDto> response = routesService.add(post).execute();
            inspectResponseForErrors(response);
            return response.body();
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
    }

    public PostDto updateTaskById(PostDto post) throws TodoApiClientException {
        try {
            Response<PostDto> response = routesService.updateById(post.getId(), post).execute();
            inspectResponseForErrors(response);
            return response.body();
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
    }

    public void deleteTaskById(String postId) throws TodoApiClientException {
        try {
            Response<Void> response = routesService.deleteById(postId).execute();
            inspectResponseForErrors(response);
        } catch (IOException e) {
            throw new NetworkErrorException();
        }
    }

    private void inspectResponseForErrors(Response response) throws TodoApiClientException {
        int code = response.code();
        if (code == 404) {
            throw new ItemNotFoundException();
        } else if (code >= 400) {
            throw new UnknownErrorException(code);
        }
    }
}
