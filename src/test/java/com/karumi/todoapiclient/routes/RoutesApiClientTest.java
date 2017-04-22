package com.karumi.todoapiclient.routes;

import com.karumi.todoapiclient.MockWebServerTest;

import org.junit.Before;
import org.junit.Test;

public class RoutesApiClientTest extends MockWebServerTest {

    private RoutesApiClient apiClient;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String mockWebServerEndpoint = getBaseEndpoint();
        apiClient = new RoutesApiClient(mockWebServerEndpoint);
    }

    @Test
    public void sendsAcceptAndContentTypeHeaders() throws Exception {
        enqueueMockResponse();

        apiClient.getAllPosts();

        assertRequestContainsHeader("Accept", "application/json");
    }

    @Test
    public void sendsGetAllPostRequestToTheCorrectEndpoint() throws Exception {
        enqueueMockResponse();

        apiClient.getAllPosts();

        assertGetRequestSentTo("/posts");
    }
}
