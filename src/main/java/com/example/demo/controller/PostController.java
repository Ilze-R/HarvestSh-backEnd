package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.enumeration.PostType;
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
        postService.deleteComment(id, PostType.GARDENING);
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
        postService.deleteComment(id, PostType.RECIPE);
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
        postService.deleteComment(id, PostType.I_MADE);
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
        postService.deleteComment(id, PostType.OTHER);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PatchMapping("/toggle/gardeninglikes/{id}/{userid}")
    public ResponseEntity<HttpResponse> toggleLikeToGardeningPost(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedGardeningPost(userId, id);
        if (hasLiked) {
            postService.removePostLike(id, PostType.GARDENING);
            postService.deleteGardeningPostLikeKeyTable(userId, id);
        } else {
            postService.addPostLike(id, PostType.GARDENING);
            postService.addGardeningPostLikeKeyTable(userId, id);
        }
        List<LikedGardeningPost> likedGardeningPosts = postService.getUserLikedGardeningPosts(userId);
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
    @PatchMapping("/toggle/recipelikes/{id}/{userid}")
    public ResponseEntity<HttpResponse> toggleLikeToRecipePost(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedRecipePost(userId, id);
        if (hasLiked) {
            postService.removePostLike(id, PostType.RECIPE);
            postService.deleteRecipePostLikeKeyTable(userId, id);
        } else {
            postService.addPostLike(id, PostType.RECIPE);
            postService.addRecipePostLikeKeyTable(userId, id);
        }
        List<LikedRecipePost> likedRecipePosts = postService.getUserLikedRecipePosts(userId);
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

    @PatchMapping("/toggle/imadelikes/{id}/{userid}")
    public ResponseEntity<HttpResponse> toggleLikeToIMadePost(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedIMadePost(userId, id);
        if (hasLiked) {
            postService.removePostLike(id, PostType.I_MADE);
            postService.deleteIMadePostLikeKeyTable(userId, id);
        } else {
            postService.addPostLike(id, PostType.I_MADE);
            postService.addIMadePostLikeKeyTable(userId, id);
        }
        List<LikedIMadePost> likedIMadePosts = postService.getUserLikedIMadePosts(userId);
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
    @PatchMapping("/toggle/otherlikes/{id}/{userid}")
    public ResponseEntity<HttpResponse> toggleLikeToOtherPost(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedOtherPost(userId, id);
        if (hasLiked) {
            postService.removePostLike(id, PostType.OTHER);
            postService.deleteOtherPostLikeKeyTable(userId, id);
        } else {
            postService.addPostLike(id, PostType.OTHER);
            postService.addOtherPostLikeKeyTable(userId, id);
        }
        List<LikedOtherPost> likedOtherPosts = postService.getUserLikedOtherPosts(userId);
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

    @PatchMapping("/toggle/gardeningcommentlike/{userid}/{id}")
    public ResponseEntity<HttpResponse> toggleLikeToGardeningComment(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedGardeningComment(userId, id);
        if (hasLiked) {
            postService.removeCommentLike(id, PostType.GARDENING);
            postService.deleteGardeningCommentLikeKeyTable(userId, id);
        } else {
            postService.addCommentLike(id, PostType.GARDENING);
            postService.addGardeningCommentLikeKeyTable(userId, id);
        }
        List<LikedGardeningComment> likedGardeningComments = postService.getUserLikedGardeningComments(userId);
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

    @PatchMapping("/toggle/recipecommentlike/{userid}/{id}")
    public ResponseEntity<HttpResponse> toggleLikeToRecipeComment(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedRecipeComment(userId, id);
        if (hasLiked) {
            postService.removeCommentLike(id, PostType.RECIPE);
            postService.deleteRecipeCommentLikeKeyTable(userId, id);
        } else {
            postService.addCommentLike(id, PostType.RECIPE);
            postService.addRecipeCommentLikeKeyTable(userId, id);
        }
        List<LikedRecipeComment> likedRecipeComments = postService.getUserLikedRecipeComments(userId);
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

    @PatchMapping("/toggle/imadecommentlike/{userid}/{id}")
    public ResponseEntity<HttpResponse> toggleLikeToIMadeComment(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedIMadeComment(userId, id);
        if (hasLiked) {
            postService.removeCommentLike(id, PostType.I_MADE);
            postService.deleteIMadeCommentLikeKeyTable(userId, id);
        } else {
            postService.addCommentLike(id, PostType.I_MADE);
            postService.addIMadeCommentLikeKeyTable(userId, id);
        }
        List<LikedIMadeComment> likedIMadeComments = postService.getUserLikedIMadeComments(userId);
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

    @PatchMapping("/toggle/othercommentlike/{userid}/{id}")
    public ResponseEntity<HttpResponse> toggleLikeToOtherComment(@PathVariable("id") long id, @PathVariable("userid") long userId) {
        boolean hasLiked = postService.userHasLikedOtherComment(userId, id);
        if (hasLiked) {
            postService.removeCommentLike(id, PostType.OTHER);
            postService.deleteOtherCommentLikeKeyTable(userId, id);
        } else {
            postService.addCommentLike(id, PostType.OTHER);
            postService.addOtherCommentLikeKeyTable(userId, id);
        }
        List<LikedOtherComment> likedOtherComments = postService.getUserLikedOtherComments(userId);
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
