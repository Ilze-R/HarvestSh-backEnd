package com.example.demo;

import com.example.demo.domain.Give;
import com.example.demo.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiveAndImageRequest {
    private Give give;
    private Image image;
}
