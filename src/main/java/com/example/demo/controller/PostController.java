package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.enumeration.PostType;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @DeleteMapping("/delete/gardeningpost/{id}")
    public ResponseEntity<HttpResponse> deleteGardeningPost(@PathVariable("id") long id) {
        postService.deletePost(id, PostType.GARDENING);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Post deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/recipepost/{id}")
    public ResponseEntity<HttpResponse> deleteRecipePost(@PathVariable("id") long id) {
        postService.deletePost(id, PostType.RECIPE);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Post deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/imadepost/{id}")
    public ResponseEntity<HttpResponse> deleteIMadePost(@PathVariable("id") long id) {
        postService.deletePost(id, PostType.I_MADE);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Post deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/otherpost/{id}")
    public ResponseEntity<HttpResponse> deleteOtherPost(@PathVariable("id") long id) {
        postService.deletePost(id, PostType.OTHER);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Post deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/gardeningcomment/{id}")
    public ResponseEntity<HttpResponse> deleteGardeningComment(@PathVariable("id") long id) {
        commentService.deleteComment(id, PostType.GARDENING);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/recipecomment/{id}")
    public ResponseEntity<HttpResponse> deleteRecipeComment(@PathVariable("id") long id) {
        commentService.deleteComment(id, PostType.RECIPE);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/imadecomment/{id}")
    public ResponseEntity<HttpResponse> deleteIMadeComment(@PathVariable("id") long id) {
        commentService.deleteComment(id, PostType.I_MADE);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/othercomment/{id}")
    public ResponseEntity<HttpResponse> deleteOtherComment(@PathVariable("id") long id) {
        commentService.deleteComment(id, PostType.OTHER);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PatchMapping("/toggle/gardeninglikes/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToGardeningPost(@PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = postService.userHasLikedPost(actionUser, postId, "GardeningPostLikes");
        if (hasLiked) {
            postService.removePostLike(postId, PostType.GARDENING);
            postService.deletePostLikeKeyTable(actionUser, postId, PostType.GARDENING);
            postService.deletePostLikeNotification(postId, PostType.GARDENING);
        } else {
            postService.addPostLike(postId, PostType.GARDENING);
            postService.addPostLikeKeyTable(actionUser, postId, PostType.GARDENING);
            postService.addPostLikeNotification(postId, actionUser, receiverUser, PostType.GARDENING);
        }
        List<LikedGardeningPost> likedGardeningPosts = postService.getUserLikedGardeningPosts(actionUser);
        int totalLikes = likedGardeningPosts.size();
        //   int totalLikes = postService.getAllPostLikes(id, PostType.GARDENING);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Post like deleted successfully" : "Post like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PatchMapping("/toggle/recipelikes/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToRecipePost(@PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = postService.userHasLikedPost(actionUser, postId, "RecipePostLikes");
        if (hasLiked) {
            postService.removePostLike(postId, PostType.RECIPE);
            postService.deletePostLikeKeyTable(actionUser, postId, PostType.RECIPE);
            postService.deletePostLikeNotification(postId, PostType.RECIPE);
        } else {
            postService.addPostLike(postId, PostType.RECIPE);
            postService.addPostLikeKeyTable(actionUser, postId, PostType.RECIPE);
            postService.addPostLikeNotification(postId, actionUser, receiverUser, PostType.RECIPE);
        }
        List<LikedRecipePost> likedRecipePosts = postService.getUserLikedRecipePosts(actionUser);
        int totalLikes = likedRecipePosts.size();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Post like deleted successfully" : "Post like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/toggle/imadelikes/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToIMadePost(@PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = postService.userHasLikedPost(actionUser, postId, "IMadePostLikes");
        if (hasLiked) {
            postService.removePostLike(postId, PostType.I_MADE);
            postService.deletePostLikeKeyTable(actionUser, postId, PostType.I_MADE);
            postService.deletePostLikeNotification(postId, PostType.I_MADE);
        } else {
            postService.addPostLike(postId, PostType.I_MADE);
            postService.addPostLikeKeyTable(actionUser, postId, PostType.I_MADE);
            postService.addPostLikeNotification(postId, actionUser, receiverUser, PostType.I_MADE);
        }
        List<LikedIMadePost> likedIMadePosts = postService.getUserLikedIMadePosts(actionUser);
        int totalLikes = likedIMadePosts.size();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Post like deleted successfully" : "Post like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PatchMapping("/toggle/otherlikes/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToOtherPost(@PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = postService.userHasLikedPost(actionUser, postId, "OtherPostLikes");
        if (hasLiked) {
            postService.removePostLike(postId, PostType.OTHER);
            postService.deletePostLikeKeyTable(actionUser, postId, PostType.OTHER);
            postService.deletePostLikeNotification(postId, PostType.OTHER);
        } else {
            postService.addPostLike(postId, PostType.OTHER);
            postService.addPostLikeKeyTable(actionUser, postId, PostType.OTHER);
            postService.addPostLikeNotification(postId, actionUser, receiverUser, PostType.OTHER);
        }
        List<LikedOtherPost> likedOtherPosts = postService.getUserLikedOtherPosts(actionUser);
        int totalLikes = likedOtherPosts.size();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Post like deleted successfully" : "Post like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/toggle/gardeningcommentlike/{comment_id}/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToGardeningComment(@PathVariable("comment_id") long commentId, @PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = commentService.userHasLikedComment(actionUser, commentId, "GardeningCommentLikes");
        if (hasLiked) {
            commentService.removeCommentLike(commentId, PostType.GARDENING);
            commentService.deleteCommentLikeKeyTable(actionUser, commentId, PostType.GARDENING);
            commentService.deleteCommentLikeNotification(commentId, postId, PostType.GARDENING);
        } else {
            commentService.addCommentLike(commentId, PostType.GARDENING);
            commentService.addCommentLikeKeyTable(actionUser, commentId, PostType.GARDENING);
            commentService.addCommentLikeNotification(commentId, postId, actionUser, receiverUser, PostType.GARDENING);
        }
        List<LikedGardeningComment> likedGardeningComments = commentService.getUserLikedGardeningComments(actionUser);
        int totalLikes = likedGardeningComments.size();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Comment like deleted successfully" : "Comment like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/toggle/recipecommentlike/{comment_id}/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToRecipeComment(@PathVariable("comment_id") long commentId, @PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = commentService.userHasLikedComment(actionUser, commentId, "RecipeCommentLikes");
        if (hasLiked) {
            commentService.removeCommentLike(commentId, PostType.RECIPE);
            commentService.deleteCommentLikeKeyTable(actionUser, commentId, PostType.RECIPE);
            commentService.deleteCommentLikeNotification(commentId, postId, PostType.RECIPE);
        } else {
            commentService.addCommentLike(commentId, PostType.RECIPE);
            commentService.addCommentLikeKeyTable(actionUser, commentId, PostType.RECIPE);
            commentService.addCommentLikeNotification(commentId, postId, actionUser, receiverUser, PostType.RECIPE);
        }
        List<LikedRecipeComment> likedRecipeComments = commentService.getUserLikedRecipeComments(actionUser);
        int totalLikes = likedRecipeComments.size();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Comment like deleted successfully" : "Comment like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/toggle/imadecommentlike/{comment_id}/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToIMadeComment(@PathVariable("comment_id") long commentId, @PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = commentService.userHasLikedComment(actionUser, commentId, "IMadeCommentLikes");
        if (hasLiked) {
            commentService.removeCommentLike(commentId, PostType.I_MADE);
            commentService.deleteCommentLikeKeyTable(actionUser, commentId, PostType.I_MADE);
            commentService.deleteCommentLikeNotification(commentId, postId, PostType.I_MADE);
        } else {
            commentService.addCommentLike(commentId, PostType.I_MADE);
            commentService.addCommentLikeKeyTable(actionUser, commentId, PostType.I_MADE);
            commentService.addCommentLikeNotification(commentId, postId, actionUser, receiverUser, PostType.I_MADE);
        }
        List<LikedIMadeComment> likedIMadeComments = commentService.getUserLikedIMadeComments(actionUser);
        int totalLikes = likedIMadeComments.size();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Comment like deleted successfully" : "Comment like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/toggle/othercommentlike/{comment_id}/{post_id}/{action_userId}/{receiver_userId}")
    public ResponseEntity<HttpResponse> toggleLikeToOtherComment(@PathVariable("comment_id") long commentId, @PathVariable("post_id") long postId, @PathVariable("action_userId") long actionUser, @PathVariable("receiver_userId") long receiverUser) {
        boolean hasLiked = commentService.userHasLikedComment(actionUser, commentId, "OtherCommentLikes");
        if (hasLiked) {
            commentService.removeCommentLike(commentId, PostType.OTHER);
            commentService.deleteCommentLikeKeyTable(actionUser, commentId, PostType.OTHER);
            commentService.deleteCommentLikeNotification(commentId, postId, PostType.OTHER);
        } else {
           commentService.addCommentLike(commentId, PostType.OTHER);
            commentService.addCommentLikeKeyTable(actionUser, commentId, PostType.OTHER);
            commentService.addCommentLikeNotification(commentId, postId, actionUser, receiverUser, PostType.OTHER);
        }
        List<LikedOtherComment> likedOtherComments = commentService.getUserLikedOtherComments(actionUser);
        int totalLikes = likedOtherComments.size();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Comment like deleted successfully" : "Comment like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
}
