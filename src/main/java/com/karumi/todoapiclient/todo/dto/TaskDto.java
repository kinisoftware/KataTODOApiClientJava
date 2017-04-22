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

package com.karumi.todoapiclient.todo.dto;

import com.google.gson.annotations.SerializedName;

public class TaskDto {

    @SerializedName("id")
    private final String id;
    @SerializedName("userId")
    private final String userId;
    @SerializedName("title")
    private final String title;
    @SerializedName("finished")
    private final boolean finished;

    public TaskDto(String id, String userId, String title, boolean finished) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.finished = finished;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDto taskDto = (TaskDto) o;

        if (finished != taskDto.finished) return false;
        if (id != null ? !id.equals(taskDto.id) : taskDto.id != null) return false;
        if (!userId.equals(taskDto.userId)) return false;
        return title.equals(taskDto.title);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + userId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (finished ? 1 : 0);
        return result;
    }
}
