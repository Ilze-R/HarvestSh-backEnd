package com.example.demo.service;

import com.example.demo.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    GardeningPost createGardeningPost(long userId, GardeningPost gardeningPost, MultipartFile image);
    RecipePost createRecipePost(long userId, RecipePost recipePost, MultipartFile image);

    IMadePost createIMadePost(long userId, IMadePost iMadePost, MultipartFile image);

    OtherPost createOtherPost(long userId, OtherPost otherPost, MultipartFile image);
    List<GardeningPost> getAllGardeningPosts();

    List<GardeningPost> getAllGardeningPosts(int pageSize, int offset);

    List<GardeningPost> getAllGardeningPost(Pageable pageable);
    List<RecipePost> getAllRecipePost(Pageable pageable);
    List<IMadePost> getAllIMadePost(Pageable pageable);
    List<OtherPost> getAllOtherPost(Pageable pageable);

    int getAllRecipePostCount();
}
