package com.karumi.todoapiclient.routes.dto;

public class PostDto {

    private final String id;
    private final String userId;
    private final String title;
    private final String body;

    public PostDto(String id, String userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
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

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostDto postDto = (PostDto) o;

        if (id != null ? !id.equals(postDto.id) : postDto.id != null) return false;
        if (!userId.equals(postDto.userId)) return false;
        if (!title.equals(postDto.title)) return false;
        return body.equals(postDto.body);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
