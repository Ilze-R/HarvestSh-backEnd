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
    public void deleteGardeningPost(Long postId) {
        postRepository.deleteGardeningPost(postId);
    }

    @Override
    public void deleteRecipePost(Long postId) {
        postRepository.deleteRecipePost(postId);
    }

    @Override
    public void deleteIMadePost(Long postId) {
        postRepository.deleteIMadePost(postId);
    }

    @Override
    public void deleteOtherPost(Long postId) {
        postRepository.deleteOtherPost(postId);
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
    public GardeningComment getLatestGardeningComment(long postId) {
        return postRepository.getLatestGardeningComment(postId);
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
    public void addGardeningPostLikeKeyTable(Long userId, Long postId) {
        postRepository.addGardeningPostLikeKeyTable(userId, postId);
    }

    @Override
    public void deleteGardeningPostLikeKeyTable(Long userId, Long postId) {
        postRepository.deleteGardeningPostLikeKeyTable(userId, postId);
    }

    @Override
    public int getAllGardeningPostLikes(Long postId) {
      return postRepository.getAllGardeningPostLikes(postId);
    }

    @Override
    public boolean userHasLikedGardeningPost(Long userId, Long postId) {
        return postRepository.userHasLikedGardeningPost(userId, postId);
    }

    @Override
    public List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId) {
        return postRepository.getUserLikedGardeningPosts(userId);
    }

    ;

    @Override
    public void updateRecipeComment(Long commentId, String comment_text) {
        postRepository.updateRecipeComment(commentId, comment_text);
    }

//    @Override
//    public GardeningComment getGardeningCommentById(long id) {
//        return postRepository.getGardeningCommentById(id);
//    }

    @Override
    public void updatePlusGardeningCommentLike(Long id) {
        postRepository.addGardeningCommentLike(id);
    }

    @Override
    public void updateMinusGardeningCommentLike(Long id) {
        postRepository.deleteGardeningCommentLike(id);
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
    public int getAllGardeningCommentLikes(Long commentId) {
        return postRepository.getAllGardeningCommentLikes(commentId);
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
    public void updatePlusRecipeLike(Long id) {
        postRepository.addRecipeLike(id);
    }

    @Override
    public void updateMinusRecipeLike(Long id) {
        postRepository.deleteRecipeLike(id);
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
    public int getAllRecipePostLikes(Long postId) {
        return postRepository.getAllRecipePostLikes(postId);
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
    public void updatePlusRecipeCommentLike(Long id) {
        postRepository.addRecipeCommentLike(id);
    }

    @Override
    public void updateMinusRecipeCommentLike(Long id) {
        postRepository.deleteRecipeCommentLike(id);
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
    public int getAllRecipeCommentLikes(Long commentId) {
        return postRepository.getAllRecipeCommentLikes(commentId);
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
    public void updatePlusIMadeLike(Long id) {
        postRepository.addIMadeLike(id);
    }

    @Override
    public void updateMinusIMadeLike(Long id) {
        postRepository.deleteIMadeLike(id);
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
    public int getAllIMadePostLikes(Long postId) {
        return postRepository.getAllIMadePostLikes(postId);
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
    public void updatePlusIMadeCommentLike(Long id) {
        postRepository.addIMadeCommentLike(id);
    }

    @Override
    public void updateMinusIMadeCommentLike(Long id) {
        postRepository.deleteIMadeCommentLike(id);
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
    public int getAllIMadeCommentLikes(Long commentId) {
        return postRepository.getAllIMadeCommentLikes(commentId);
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
    public void updatePlusOtherLike(Long id) {
        postRepository.addOtherLike(id);
    }

    @Override
    public void updateMinusOtherLike(Long id) {
        postRepository.deleteOtherLike(id);
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
    public int getAllOtherPostLikes(Long postId) {
        return postRepository.getAllOtherPostLikes(postId);
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
    public void updatePlusOtherCommentLike(Long id) {
        postRepository.addOtherCommentLike(id);
    }

    @Override
    public void updateMinusOtherCommentLike(Long id) {
        postRepository.deleteOtherCommentLike(id);
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
    public int getAllOtherCommentLikes(Long commentId) {
        return postRepository.getAllOtherCommentLikes(commentId);
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
