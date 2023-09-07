package com.example.demo.repository;

import com.example.demo.domain.GardeningPost;

import com.example.demo.domain.IMadePost;
import com.example.demo.domain.OtherPost;
import com.example.demo.domain.RecipePost;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostRepository{

    GardeningPost create(Long userId, GardeningPost data, MultipartFile image);

    RecipePost create (Long userId, RecipePost data, MultipartFile image);
    IMadePost create (Long userId, IMadePost data, MultipartFile image);
    OtherPost create (Long userId, OtherPost data, MultipartFile image);

    List<GardeningPost> getAllGardeningPosts();

    List<GardeningPost> getAllGardeningPosts(int pageSize, int offset);

    List<GardeningPost> getAllGardeningPost(Pageable pageable);
    List<RecipePost> getAllRecipePost(Pageable pageable);
    List<IMadePost> getAllIMadePost(Pageable pageable);
    List<OtherPost> getAllOtherPost(Pageable pageable);

    int getAllRecipePostCount();
}
