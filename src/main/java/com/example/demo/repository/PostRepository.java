package com.example.demo.repository;

import com.example.demo.domain.*;

import com.example.demo.enumeration.PostType;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostRepository {

    <T extends Post> T create(Long userId, T post, MultipartFile image);
    GardeningPost createGardeningPostNoPhoto(long userId, GardeningPost gardeningPost);
    void deletePost(Long postId, PostType postType);
    List<GardeningPost> getAllGardeningPosts();
    List<GardeningPost> getAllGardeningPosts(int pageSize, int offset);
    List<GardeningPost> getAllGardeningPost(Pageable pageable);
    List<RecipePost> getAllRecipePost(Pageable pageable);
    List<IMadePost> getAllIMadePost(Pageable pageable);
    List<OtherPost> getAllOtherPost(Pageable pageable);
    Long getGardeningPostUserById(Long id);
    Long getRecipePostUserById(Long id);
    Long getIMadePostUserById(Long id);
    Long getOtherPostUserById(Long id);
    int getAllRecipePostCount();
    void addPostLike(Long id, PostType postType);
    void addPostLikeNotification(Long postId, Long actionUser, Long receiverUser, PostType postType);

    void deletePostLikeNotification(Long postId, PostType postType);
    void removePostLike(Long id, PostType postType);
    boolean userHasLikedPost(Long userId, Long postId, String tableName);
    void addPostLikeKeyTable (Long userId, Long postId, PostType postType);
    void deletePostLikeKeyTable(Long userId, Long postId, PostType postType);
    List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId);
    List<LikedRecipePost> getUserLikedRecipePosts(Long userId);
    List<LikedIMadePost> getUserLikedIMadePosts(Long userId);
    List<LikedOtherPost> getUserLikedOtherPosts(Long userId);

}
