package com.example.demo.domain;

import java.time.LocalDateTime;

public interface Post {
    String getTitle();
    String getDescription();
    String getTag();
    String getImg_url();
    Long getLikes();
    Long getView_count();
    LocalDateTime getDate();
    void setId(Long id);
    void setImg_url(String img_url);
    void setDate(LocalDateTime date);
}
