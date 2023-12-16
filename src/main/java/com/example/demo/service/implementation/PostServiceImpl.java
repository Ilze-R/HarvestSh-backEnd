package com.example.demo.service.implementation;

import com.example.demo.domain.*;
import com.example.demo.enumeration.PostType;
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
    public <T extends Post> T createPost(Long userId, T post, MultipartFile image) {
        return postRepository.create(userId, post, image);
    }

    @Override
    public GardeningPost createGardeningPostNoPhoto(long userId, GardeningPost gardeningPost) {
        return postRepository.createGardeningPostNoPhoto(userId, gardeningPost);
    }

    @Override
    public void deletePost(Long postId, PostType postType) {
postRepository.deletePost(postId, postType);
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
    public Long getGardeningPostUserById(Long id) {
        return postRepository.getGardeningPostUserById(id);
    }

    @Override
    public Long getRecipePostUserById(Long id) {
        return postRepository.getRecipePostUserById(id);
    }

    @Override
    public Long getIMadePostUserById(Long id) {
        return postRepository.getIMadePostUserById(id);
    }

    @Override
    public Long getOtherPostUserById(Long id) {
        return postRepository.getOtherPostUserById(id);
    }

    @Override
    public void addPostLike(Long id, PostType postType) {
        postRepository.addPostLike(id, postType);
    }

    @Override
    public void removePostLike(Long id, PostType postType) {
        postRepository.removePostLike(id, postType);
    }

    @Override
    public boolean userHasLikedPost(Long userId, Long postId, String tableName) {
        return postRepository.userHasLikedPost(userId, postId, tableName);
    }
    @Override
    public void addPostLikeKeyTable(Long userId, Long postId, PostType postType) {
        postRepository.addPostLikeKeyTable(userId, postId, postType);
    }

    @Override
    public void addPostLikeNotification(Long postId, Long actionUser, Long receiverUser, PostType postType) {
        postRepository.addPostLikeNotification(postId, actionUser, receiverUser, postType);
    }

    @Override
    public void deletePostLikeNotification(Long postId, PostType postType) {
        postRepository.deletePostLikeNotification(postId, postType);
    }

    @Override
    public void deletePostLikeKeyTable(Long userId, Long postId, PostType postType) {
        postRepository.deletePostLikeKeyTable(userId, postId, postType);
    }
    @Override
    public List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId) {
        return postRepository.getUserLikedGardeningPosts(userId);
    }
    @Override
    public List<LikedRecipePost> getUserLikedRecipePosts(Long userId) {
        return postRepository.getUserLikedRecipePosts(userId);
    }
    @Override
    public List<LikedIMadePost> getUserLikedIMadePosts(Long userId) {
        return postRepository.getUserLikedIMadePosts(userId);
    }
    @Override
    public List<LikedOtherPost> getUserLikedOtherPosts(Long userId) {
        return postRepository.getUserLikedOtherPosts(userId);
    }

}
