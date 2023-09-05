package com.example.demo.service.implementation;

import com.example.demo.domain.GardeningPost;
import com.example.demo.domain.IMadePost;
import com.example.demo.domain.OtherPost;
import com.example.demo.domain.RecipePost;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public GardeningPost createGardeningPost(long userId, GardeningPost gardeningPost, MultipartFile image) {
        return postRepository.create(userId, gardeningPost, image);
    }

    @Override
    public RecipePost createRecipePost(long userId, RecipePost recipePost, MultipartFile image) {
        return postRepository.create(userId, recipePost, image );
    }

    @Override
    public IMadePost createIMadePost(long userId, IMadePost iMadePost, MultipartFile image) {
        return postRepository.create(userId, iMadePost, image);
    }

    @Override
    public OtherPost createOtherPost(long userId, OtherPost otherPost, MultipartFile image) {
        return postRepository.create(userId, otherPost, image);
    }

    @Override
    public List<GardeningPost> getAllGardeningPosts() {
        return postRepository.getAllGardeningPosts();
    }

    @Override
    public List<GardeningPost> getAllGardeningPosts(int pageSize, int offset) {
        return postRepository.getAllGardeningPosts(pageSize, offset);
    }

    @Override
    public List<GardeningPost> getAllGardeningPost(Pageable pageable) {
        return postRepository.getAllGardeningPost(pageable);
    }

    @Override
    public List<RecipePost> getAllRecipePost(Pageable pageable) {
        return postRepository.getAllRecipePost(pageable);
    }

    @Override
    public List<IMadePost> getAllIMadePost(Pageable pageable) {
        return postRepository.getAllIMadePost(pageable);
    }

    @Override
    public List<OtherPost> getAllOtherPost(Pageable pageable) {
        return postRepository.getAllOtherPost(pageable);
    }

}
