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
    public void addPostLike(Long id, PostType postType) {
        postRepository.addPostLike(id, postType);
    }

    @Override
    public void removePostLike(Long id, PostType postType) {
        postRepository.removePostLike(id, postType);
    }

    @Override
    public GardeningComment addGardeningComment(long userId, long postId, GardeningComment gardeningComment) {
        return postRepository.addGardeningComment(userId, postId, gardeningComment);

    }
    @Override
    public IMadeComment addIMadeComment(long userId, long postId, IMadeComment iMadeComment) {
        return postRepository.addIMadeComment(userId, postId, iMadeComment);
    }
    @Override
    public RecipeComment addRecipeComment(long userId, long postId, RecipeComment recipeComment) {
        return postRepository.addRecipeComment(userId, postId, recipeComment);
    }

    @Override
    public OtherComment addOtherComment(long userId, long postId, OtherComment otherComment) {
        return postRepository.addOtherComment(userId, postId, otherComment);
    }

    @Override
    public List<GardeningComment> getAllGardeningCommentsByPostId(long id) {
        return postRepository.getAllGardeningCommentsByPostId(id);
    }

    @Override
    public List<RecipeComment> getAllRecipeCommentsByPostId(long id) {
        return postRepository.getAllRecipeCommentsByPostId(id);
    }

    @Override
    public List<IMadeComment> getAllIMadeCommentsByPostId(long id) {
        return postRepository.getAllIMadeCommentsByPostId(id);
    }

    @Override
    public List<OtherComment> getAllOtherCommentsByPostId(long id) {
        return postRepository.getAllOtherCommentsByPostId(id);
    }

    @Override
    public void updateComment(Long commentId, String comment_text, PostType postType) {
        postRepository.updateComment(commentId, comment_text, postType);
    }

    @Override
    public void deleteComment(Long commentId, PostType postType) {
        postRepository.deleteComment(commentId, postType);
    }

    @Override
    public GardeningComment getLatestGardeningComment(long postId) {
        return postRepository.getLatestGardeningComment(postId);
    }

    @Override
    public void addGardeningPostLikeKeyTable(Long userId, Long postId) {
        postRepository.addGardeningPostLikeKeyTable(userId, postId);
    }

    @Override
    public void deleteGardeningPostLikeKeyTable(Long userId, Long postId) {
        postRepository.deleteGardeningPostLikeKeyTable(userId, postId);
    }
    @Override
    public boolean userHasLikedGardeningPost(Long userId, Long postId) {
        return postRepository.userHasLikedGardeningPost(userId, postId);
    }

    @Override
    public List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId) {
        return postRepository.getUserLikedGardeningPosts(userId);
    }

    @Override
    public void addCommentLike(Long id, PostType postType) {
        postRepository.addCommentLike(id, postType);
    }

    @Override
    public void removeCommentLike(Long id, PostType postType) {
        postRepository.removeCommentLike(id, postType);
    }

    @Override
    public void addGardeningCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.addGardeningCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public void deleteGardeningCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.deleteGardeningCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public boolean userHasLikedGardeningComment(Long userId, Long commentId) {
        return postRepository.userHasLikedGardeningComment(userId, commentId);
    }

    @Override
    public List<LikedGardeningComment> getUserLikedGardeningComments(Long userId) {
        return postRepository.getUserLikedGardeningComments(userId);
    }

    @Override
    public void addRecipePostLikeKeyTable(Long userId, Long postId) {
        postRepository.addRecipePostLikeKeyTable(userId, postId);
    }

    @Override
    public void deleteRecipePostLikeKeyTable(Long userId, Long postId) {
        postRepository.deleteRecipePostLikeKeyTable(userId, postId);
    }

    @Override
    public boolean userHasLikedRecipePost(Long userId, Long postId) {
        return postRepository.userHasLikedRecipePost(userId, postId);
    }

    @Override
    public List<LikedRecipePost> getUserLikedRecipePosts(Long userId) {
        return postRepository.getUserLikedRecipePosts(userId);
    }

    @Override
    public void addRecipeCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.addRecipeCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public void deleteRecipeCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.deleteRecipeCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public boolean userHasLikedRecipeComment(Long userId, Long commentId) {
        return postRepository.userHasLikedRecipeComment(userId, commentId);
    }

    @Override
    public List<LikedRecipeComment> getUserLikedRecipeComments(Long userId) {
        return postRepository.getUserLikedRecipeComments(userId);
    }

    @Override
    public void addIMadePostLikeKeyTable(Long userId, Long postId) {
        postRepository.addIMadePostLikeKeyTable(userId, postId);
    }

    @Override
    public void deleteIMadePostLikeKeyTable(Long userId, Long postId) {
        postRepository.deleteIMadePostLikeKeyTable(userId, postId);
    }

    @Override
    public boolean userHasLikedIMadePost(Long userId, Long postId) {
        return postRepository.userHasLikedIMadePost(userId, postId);
    }

    @Override
    public List<LikedIMadePost> getUserLikedIMadePosts(Long userId) {
        return postRepository.getUserLikedIMadePosts(userId);
    }

    @Override
    public void addIMadeCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.addIMadeCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public void deleteIMadeCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.deleteIMadeCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public boolean userHasLikedIMadeComment(Long userId, Long commentId) {
        return postRepository.userHasLikedIMadeComment(userId, commentId);
    }

    @Override
    public List<LikedIMadeComment> getUserLikedIMadeComments(Long userId) {
        return postRepository.getUserLikedIMadeComments(userId);
    }

    @Override
    public void addOtherPostLikeKeyTable(Long userId, Long postId) {
        postRepository.addOtherPostLikeKeyTable(userId, postId);
    }

    @Override
    public void deleteOtherPostLikeKeyTable(Long userId, Long postId) {
        postRepository.deleteOtherPostLikeKeyTable(userId, postId);
    }

    @Override
    public boolean userHasLikedOtherPost(Long userId, Long postId) {
        return postRepository.userHasLikedOtherPost(userId, postId);
    }

    @Override
    public List<LikedOtherPost> getUserLikedOtherPosts(Long userId) {
        return postRepository.getUserLikedOtherPosts(userId);
    }

    @Override
    public void addOtherCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.addOtherCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public void deleteOtherCommentLikeKeyTable(Long userId, Long commentId) {
        postRepository.deleteOtherCommentLikeKeyTable(userId, commentId);
    }

    @Override
    public boolean userHasLikedOtherComment(Long userId, Long commentId) {
        return postRepository.userHasLikedOtherComment(userId, commentId);
    }

    @Override
    public List<LikedOtherComment> getUserLikedOtherComments(Long userId) {
        return postRepository.getUserLikedOtherComments(userId);
    }

}
