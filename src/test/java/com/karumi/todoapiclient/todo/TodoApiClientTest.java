/*
 *   Copyright (C) 2016 Karumi.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.karumi.todoapiclient.todo;

import com.google.gson.JsonSyntaxException;
import com.karumi.todoapiclient.MockWebServerTest;
import com.karumi.todoapiclient.exception.ItemNotFoundException;
import com.karumi.todoapiclient.exception.UnknownErrorException;
import com.karumi.todoapiclient.todo.dto.TaskDto;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TodoApiClientTest extends MockWebServerTest {

    private TodoApiClient apiClient;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String mockWebServerEndpoint = getBaseEndpoint();
        apiClient = new TodoApiClient(mockWebServerEndpoint);
    }

    @Test
    public void sendsAcceptAndContentTypeHeaders() throws Exception {
        enqueueMockResponse();

        apiClient.getAllTasks();

        assertRequestContainsHeader("Accept", "application/json");
    }

    @Test
    public void sendsGetAllTaskRequestToTheCorrectEndpoint() throws Exception {
        enqueueMockResponse();

        apiClient.getAllTasks();

        assertGetRequestSentTo("/todos");
    }

    @Test
    public void parsesTasksProperlyGettingAllTheTasks() throws Exception {
        enqueueMockResponse(200, "getTasksResponse.json");

        List<TaskDto> tasks = apiClient.getAllTasks();

        assertEquals(tasks.size(), 200);
        assertTaskContainsExpectedValues(tasks.get(0));
    }

    @Test
    public void sendsHeaderWithAcceptLanguage() throws Exception {
        enqueueMockResponse();

        apiClient.getAllTasks();

        assertRequestContainsHeader("Accept-Language", "es-es");
    }

    @Test
    public void parsesTasksProperlyGettingEmptyTasks() throws Exception {
        enqueueMockResponse(200, "getEmptyTasksResponse.json");

        List<TaskDto> tasks = apiClient.getAllTasks();

        assertTrue(tasks.isEmpty());
    }

    @Test(expected = ItemNotFoundException.class)
    public void throwsAnItemNotFoundExceptionWhenGettingAllTasksReturnA404() throws Exception {
        enqueueMockResponse(404);

        apiClient.getAllTasks();
    }

    @Test
    public void noReturnTasksWhenGettingAllTasksReturnNoResponse() throws Exception {
        enqueueMockResponse(200, "getEmptyResponse.json");

        List<TaskDto> tasks = apiClient.getAllTasks();

        assertNull(tasks);
    }

    @Test
    public void parsesTaskProperlyGettingATaskById() throws Exception {
        enqueueMockResponse(200, "getTaskByIdResponse.json");

        TaskDto taks = apiClient.getTaskById("1");

        assertEquals("1", taks.getId());
        assertFalse(taks.isFinished());
    }

    @Test(expected = UnknownErrorException.class)
    public void thownsAnUnknownErrorExceptionWhenGettingATaskByIdReturnA500() throws Exception {
        enqueueMockResponse(500);

        apiClient.getAllTasks();
    }

    @Test
    public void useGetToTheCorrectPathGettingATaskById() throws Exception {
        enqueueMockResponse(200, "getTaskByIdResponse.json");

        apiClient.getTaskById("1");

        assertGetRequestSentTo("/todos/1");
    }

    @Test
    public void noTaskGettingATaskByNonExistingId() throws Exception {
        enqueueMockResponse(200);

        TaskDto task = apiClient.getTaskById("1");

        assertNull(task);
    }

    @Test(expected = JsonSyntaxException.class)
    public void noTaskGettingATaskByIdWhenWrongJson() throws Exception {
        enqueueMockResponse(200, "getTaskByIdWrongJsonResponse.json");

        apiClient.getTaskById("1");
    }

    @Test
    public void requestIsRightCreatingATaskById() throws Exception {
        enqueueMockResponse();

        TaskDto anyTask = new TaskDtoBuilder().build();
        apiClient.addTask(anyTask);

        assertPostRequestSentTo("/todos");
    }

    @Test
    public void createsTaskSentTheRightBody() throws Exception {
        enqueueMockResponse();

        TaskDto taskDto = new TaskDtoBuilder()
                .withUserId("2")
                .withFinishedStatus(false)
                .withTitle("Finish this kata")
                .build();
        apiClient.addTask(taskDto);

        assertRequestBodyEquals("addTaskRequest.json");
    }

    @Test
    public void createTaskReturnTheCreatedTask() throws Exception {
        enqueueMockResponse(201, "addTaskResponse.json");

        TaskDto anyTask = new TaskDtoBuilder().build();
        TaskDto createdTask = apiClient.addTask(anyTask);

        TaskDto expectedCreatedTask = new TaskDtoBuilder()
                .withUserId("1")
                .withTitle("delectus aut autem")
                .withFinishedStatus(false)
                .withId("1")
                .build();
        assertEquals(expectedCreatedTask, createdTask);
    }

    @Test
    public void requestIsRightDeletingATaskById() throws Exception {
        enqueueMockResponse();

        String taskId = "any_task";
        apiClient.deleteTaskById(taskId);

        assertDeleteRequestSentTo("/todos/" + taskId);
    }

    @Test
    public void requestIsRightUpdatingATask() throws Exception {
        enqueueMockResponse();

        TaskDto taskDto = new TaskDtoBuilder()
                .withId("1")
                .build();
        apiClient.updateTaskById(taskDto);

        assertPutRequestSentTo("/todos/" + taskDto.getId());
    }

    private void assertTaskContainsExpectedValues(TaskDto task) {
        assertEquals(task.getId(), "1");
        assertEquals(task.getUserId(), "1");
        assertEquals(task.getTitle(), "delectus aut autem");
        assertFalse(task.isFinished());
    }
}

class TaskDtoBuilder {

    private String id;
    private String title;
    private String userId;
    private boolean finished;

    public TaskDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskDtoBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public TaskDtoBuilder withFinishedStatus(boolean isFinished) {
        this.finished = isFinished;
        return this;
    }

    public TaskDtoBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public TaskDto build() {
        return new TaskDto(id, userId, title, finished);
    }

}
