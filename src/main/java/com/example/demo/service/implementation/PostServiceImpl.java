package com.example.demo.service.implementation;

import com.example.demo.domain.*;
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

    @Override
    public int getAllRecipePostCount() {
        return postRepository.getAllRecipePostCount();
    }

    @Override
    public GardeningPost getGardeningPostById(long id) {
        return postRepository.getGardeningPostById(id);
    }

    @Override
    public RecipePost getRecipePostById(long id) {
        return postRepository.getRecipePostById(id);
    }

    @Override
    public IMadePost getIMadePostById(long id) {
        return postRepository.getIMadePostById(id);
    }

    @Override
    public OtherPost getOtherPostById(long id) {
        return postRepository.getOtherPostById(id);
    }

    @Override
    public List<GardeningComment> getAllGardeningCommentsByPostId(long id) {
        return postRepository.getAllGardeningCommentsByPostId(id);
    }

    @Override
    public GardeningComment addGardeningComment(long userId, long postId, GardeningComment gardeningComment) {
        return postRepository.addGardeningComment(userId, postId, gardeningComment);
    }

    @Override
    public List<RecipeComment> getAllRecipeCommentsByPostId(long id) {
        return postRepository.getAllRecipeCommentsByPostId(id);
    }

    @Override
    public RecipeComment addRecipeComment(long userId, long postId, RecipeComment recipeComment) {
        return postRepository.addRecipeComment(userId, postId, recipeComment);
    }

    @Override
    public List<IMadeComment> getAllIMadeCommentsByPostId(long id) {
        return postRepository.getAllIMadeCommentsByPostId(id);
    }

    @Override
    public IMadeComment addIMadeComment(long userId, long postId, IMadeComment iMadeComment) {
        return postRepository.addIMadeComment(userId, postId, iMadeComment);
    }

    @Override
    public List<OtherComment> getAllOtherCommentsByPostId(long id) {
        return postRepository.getAllOtherCommentsByPostId(id);
    }

    @Override
    public OtherComment addOtherComment(long userId, long postId, OtherComment otherComment) {
        return postRepository.addOtherComment(userId, postId, otherComment);
    }

    @Override
    public void updateGardeningComment(Long commentId, String comment_text) {
        postRepository.updateGardeningComment(commentId, comment_text);
    }

    @Override
    public void updateIMadeComment(Long commentId, String comment_text) {
        postRepository.updateIMadeComment(commentId, comment_text);
    }

    @Override
    public void updateOtherComment(Long commentId, String comment_text) {
        postRepository.updateOtherComment(commentId, comment_text);
    }

    @Override
    public void deleteGardeningComment(Long commentId) {
        postRepository.deleteGardeningComment(commentId);
    }

    @Override
    public void deleteRecipeComment(Long commentId) {
        postRepository.deleteRecipeComment(commentId);
    }

    @Override
    public void deleteIMadeComment(Long commentId) {
        postRepository.deleteIMadeComment(commentId);
    }

    @Override
    public void deleteOtherComment(Long commentId) {
        postRepository.deleteOtherComment(commentId);
    }

    @Override
    public void updatePlusGardeningLike(Long id) {
        postRepository.addGardeningLike(id);
    }

    @Override
    public void updateMinusGardeningLike(Long id) {
        postRepository.deleteGardeningLike(id);
    }

    @Override
    public void addPostLikeKeyTable(Long userId, Long postId) {
        postRepository.addPostLikeKeyTable(userId, postId);
    }

    @Override
    public void deletePostLikeKeyTable(Long userId, Long postId) {
        postRepository.deletePostLikeKeyTable(userId, postId);
    }

    @Override
    public int getAllGardeningPostLikes(Long postId) {
      return postRepository.getAllGardeningPostLikes(postId);
    }

    @Override
    public boolean userHasLikedPost(Long userId, Long postId) {
        return postRepository.userHasLikedPost(userId, postId);
    }

    ;

    @Override
    public void updateRecipeComment(Long commentId, String comment_text) {
        postRepository.updateRecipeComment(commentId, comment_text);
    }

    @Override
    public GardeningComment getGardeningCommentById(long id) {
        return postRepository.getGardeningCommentById(id);
    }

}
