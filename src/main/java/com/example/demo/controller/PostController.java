package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        postService.deleteGardeningPost(id);
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
        postService.deleteRecipePost(id);
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
        postService.deleteIMadePost(id);
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
        postService.deleteOtherPost(id);
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
        postService.deleteGardeningComment(id);
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
        postService.deleteRecipeComment(id);
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
        postService.deleteIMadeComment(id);
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
        postService.deleteOtherComment(id);
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
            postService.updateMinusGardeningLike(id);
            postService.deleteGardeningPostLikeKeyTable(userId, id);
        } else {
            postService.updatePlusGardeningLike(id);
            postService.addGardeningPostLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllGardeningPostLikes(id);
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
            postService.updateMinusRecipeLike(id);
            postService.deleteRecipePostLikeKeyTable(userId, id);
        } else {
            postService.updatePlusRecipeLike(id);
            postService.addRecipePostLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllRecipePostLikes(id);
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
            postService.updateMinusIMadeLike(id);
            postService.deleteIMadePostLikeKeyTable(userId, id);
        } else {
            postService.updatePlusIMadeLike(id);
            postService.addIMadePostLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllIMadePostLikes(id);
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
            postService.updateMinusOtherLike(id);
            postService.deleteOtherPostLikeKeyTable(userId, id);
        } else {
            postService.updatePlusOtherLike(id);
            postService.addOtherPostLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllOtherPostLikes(id);
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
            postService.updateMinusGardeningCommentLike(id);
            postService.deleteGardeningCommentLikeKeyTable(userId, id);
        } else {
            postService.updatePlusGardeningCommentLike(id);
            postService.addGardeningCommentLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllGardeningCommentLikes(id);
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
            postService.updateMinusRecipeCommentLike(id);
            postService.deleteRecipeCommentLikeKeyTable(userId, id);
        } else {
            postService.updatePlusRecipeCommentLike(id);
            postService.addRecipeCommentLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllRecipeCommentLikes(id);
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
            postService.updateMinusIMadeCommentLike(id);
            postService.deleteIMadeCommentLikeKeyTable(userId, id);
        } else {
            postService.updatePlusIMadeCommentLike(id);
            postService.addIMadeCommentLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllIMadeCommentLikes(id);
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
            postService.updateMinusOtherCommentLike(id);
            postService.deleteOtherCommentLikeKeyTable(userId, id);
        } else {
            postService.updatePlusOtherCommentLike(id);
            postService.addOtherCommentLikeKeyTable(userId, id);
        }
        int totalLikes = postService.getAllOtherCommentLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message(hasLiked ? "Comment like deleted successfully" : "Comment like added successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/gardeningpostlikes/{id}")
    public ResponseEntity<HttpResponse> getAllGardeningPostLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllGardeningPostLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All post likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/recipepostlikes/{id}")
    public ResponseEntity<HttpResponse> getAllRecipePostLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllRecipePostLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All post likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @GetMapping("/imadepostlikes/{id}")
    public ResponseEntity<HttpResponse> getAllIMadePostLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllIMadePostLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All post likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/otherpostlikes/{id}")
    public ResponseEntity<HttpResponse> getAllOtherPostLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllOtherPostLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All post likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/gardeningcommentlikes/{id}")
    public ResponseEntity<HttpResponse> getAllGardeningCommentLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllGardeningCommentLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All comment likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/recipecommentlikes/{id}")
    public ResponseEntity<HttpResponse> getAllRecipeCommentLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllRecipeCommentLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All comment likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/imadecommentlikes/{id}")
    public ResponseEntity<HttpResponse> getAllIMadeCommentLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllIMadeCommentLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All comment likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/othercommentlikes/{id}")
    public ResponseEntity<HttpResponse> getAllOtherCommentLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllOtherCommentLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All comment likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
