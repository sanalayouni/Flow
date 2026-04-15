package com.contentflow.contentflow.dto.request;

import com.contentflow.contentflow.model.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PostRequest {

    @NotBlank(message = "Post title is required")
    @Size(min = 2, max = 200, message = "Post title must be between 2 and 200 characters")
    private String title;

    @NotBlank(message = "Post content is required")
    private String content;

    @NotNull(message = "Post status is required")
    private PostStatus status;

    private LocalDateTime scheduledDate;

    private Long categoryId;

    public PostRequest() {
    }

    public PostRequest(String title, String content, PostStatus status, LocalDateTime scheduledDate, Long categoryId) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.scheduledDate = scheduledDate;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}