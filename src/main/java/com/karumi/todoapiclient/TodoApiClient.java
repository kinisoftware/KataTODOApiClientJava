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

package com.karumi.todoapiclient;

import com.karumi.todoapiclient.interceptor.DefaultHeadersInterceptor;
import java.io.IOException;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.karumi.todoapiclient.TodoApiClientConfig.BASE_ENDPOINT;

public class TodoApiClient {

  private final TodoService todoService;

  public TodoApiClient() {
    OkHttpClient okHttpClient = new OkHttpClient();
    okHttpClient.interceptors().add(new DefaultHeadersInterceptor());
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_ENDPOINT).client(okHttpClient).build();
    this.todoService = retrofit.create(TodoService.class);
  }

  public void getAllTasks() {
    try {
      todoService.getAll().execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
