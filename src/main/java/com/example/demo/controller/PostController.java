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

    @GetMapping("/gardeningpostlikes/{id}")
    public ResponseEntity<HttpResponse> getAllGardeningPostLikes(@PathVariable("id") long id) {
        int totalLikes = postService.getAllGardeningPostLikes(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("total-likes", totalLikes))
                        .message("All likes retrieved")
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
                        .message("All likes retrieved")
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
                        .message("All likes retrieved")
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
                        .message("All likes retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
