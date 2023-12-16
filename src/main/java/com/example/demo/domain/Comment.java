package com.example.demo.domain;

import java.time.LocalDateTime;


public interface Comment {

    LocalDateTime getDate();
    String getComment_text();
    String getReply_username();
    Long getLikes();
    Long getParent_comment_id();
    Long getComment_user_id();
    String getUser_image_url();
    String getUsername();
    void setId(Long id);
    void setDate(LocalDateTime date);

}
