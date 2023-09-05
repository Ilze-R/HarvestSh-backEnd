package com.example.demo.service;

import com.example.demo.domain.GardeningPost;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GardeningPostService {
    GardeningPost createPost(long userId, GardeningPost gardeningPost, MultipartFile image);

    List<GardeningPost> getAllGardeningPosts();

    List<GardeningPost> getAllGardeningPosts(int pageSize, int offset);

    List<GardeningPost> getAllGardeningPost(Pageable pageable);
}
