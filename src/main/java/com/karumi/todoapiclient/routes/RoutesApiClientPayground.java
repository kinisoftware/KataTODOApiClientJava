package com.karumi.todoapiclient.routes;

import com.karumi.todoapiclient.exception.ApiClientException;
import com.karumi.todoapiclient.routes.dto.PostDto;

import java.util.List;

public class RoutesApiClientPayground {

    private static void print(Object object) {
        System.out.println(object);
    }

    public static void main(String[] args) {
        RoutesApiClient apiClient = new RoutesApiClient();

        try {
            List<PostDto> allPosts = apiClient.getAllPosts();
            print(allPosts);
        } catch (ApiClientException e) {
            e.printStackTrace();
        }
    }
}
