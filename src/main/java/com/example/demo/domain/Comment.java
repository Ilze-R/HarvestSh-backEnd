package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

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


//Long id;
// LocalDateTime date;
// String comment_text;
// String reply_username;
// Long likes;
// Long parent_comment_id;
//Long comment_user_id;
// String user_image_url;
//String username;

}
