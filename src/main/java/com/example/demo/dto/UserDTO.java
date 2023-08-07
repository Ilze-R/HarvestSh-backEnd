package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private boolean enabled;
    private boolean isNotLocked;
    private LocalDateTime createdAt;
    private String roleName;
    private String permission;
}
