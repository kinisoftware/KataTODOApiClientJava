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

import com.karumi.todoapiclient.todo.dto.TaskDto;
import java.util.List;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

interface TodoService {

  @GET(TodoApiClientConfig.TASKS_ENDPOINT) Call<List<TaskDto>> getAll();

  @GET(TodoApiClientConfig.TASKS_ENDPOINT + "/{taskId}") Call<TaskDto> getById(@Path("taskId") String taskId);

  @POST(TodoApiClientConfig.TASKS_ENDPOINT) Call<TaskDto> add(@Body TaskDto task);

  @PUT(TodoApiClientConfig.TASKS_ENDPOINT + "/{taskId}") Call<TaskDto> updateById(@Path("taskId") String taskId,
                                                                                  @Body TaskDto task);

  @DELETE(TodoApiClientConfig.TASKS_ENDPOINT + "/{taskId}") Call<Void> deleteById(@Path("taskId") String taskId);
}
