package com.example.demo.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "Comment cannot be empty")
    private String comment_text;

}
