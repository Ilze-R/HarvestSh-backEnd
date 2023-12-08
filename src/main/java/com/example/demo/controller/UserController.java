package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.dto.UserDTO;
import com.example.demo.enumeration.EventType;
import com.example.demo.enumeration.PostType;
import com.example.demo.event.NewUserEvent;
import com.example.demo.exception.ApiException;
import com.example.demo.form.*;
import com.example.demo.provider.TokenProvider;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.dtomapper.UserDTOMapper.toUser;
import static com.example.demo.enumeration.EventType.*;
import static com.example.demo.utils.ExceptionUtils.processError;
import static com.example.demo.utils.UserUtils.getAuthenticatedUser;
import static com.example.demo.utils.UserUtils.getLoggedInUser;
import static java.time.LocalTime.now;
import static java.util.Map.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserService userService;
    private final PostService postService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RoleService roleService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ApplicationEventPublisher publisher;
    private final EventService eventService;


    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginForm loginForm) {
        UserDTO user = authenticate(loginForm.getEmail(), loginForm.getPassword());
        return sendResponse(user);

    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid Users user) {
        UserDTO userDTO = userService.createUser(user);
        return ResponseEntity.created(getUrl()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userDTO))
                        .message("User created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    @GetMapping("/profile")
    public ResponseEntity<HttpResponse> profile(Authentication authentication) {
        UserDTO user = userService.getUserByEmail(getAuthenticatedUser(authentication).getEmail());
        List<LikedGardeningPost> likedGardeningPosts = postService.getUserLikedGardeningPosts(user.getId());
        List<LikedRecipePost> likedRecipePosts = postService.getUserLikedRecipePosts(user.getId());
        List<LikedIMadePost> likedIMadePosts = postService.getUserLikedIMadePosts(user.getId());
        List<LikedOtherPost> likedOtherPosts = postService.getUserLikedOtherPosts(user.getId());
        List<LikedGardeningComment> likedGardeningComments = postService.getUserLikedGardeningComments(user.getId());
        List<LikedRecipeComment> likedRecipeComments = postService.getUserLikedRecipeComments(user.getId());
        List<LikedIMadeComment> likedIMadeComments = postService.getUserLikedIMadeComments(user.getId());
        List<LikedOtherComment> likedOtherComments = postService.getUserLikedOtherComments(user.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("events", eventService.getEventsByUserId(user.getId()));
        data.put("roles", roleService.getRoles());
        data.put("likedGardeningPosts", likedGardeningPosts);
        data.put("likedRecipePosts", likedRecipePosts);
        data.put("likedIMadePosts", likedIMadePosts);
        data.put("likedOtherPosts", likedOtherPosts);
        data.put("likedGardeningComments", likedGardeningComments);
        data.put("likedRecipeComments", likedRecipeComments);
        data.put("likedIMadeComments", likedIMadeComments);
        data.put("likedOtherComments", likedOtherComments);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now().toString())
                .data(data)
                .message("Profile Retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }



    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getUserById(Authentication authentication, @PathVariable("id") long id) {
        UserDTO userDTO = getAuthenticatedUser(authentication);
        UserDTO retrievedUser = userService.getUserById(userDTO.getId());
        if (retrievedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            HttpResponse.builder()
                                    .timeStamp(LocalDateTime.now().toString())
                                    .message("User not found")
                                    .status(NOT_FOUND)
                                    .build()
                    );
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", retrievedUser))
                        .message("User retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PatchMapping("/update")
    public ResponseEntity<HttpResponse> updateUser(@RequestBody @Valid UpdateForm user){
        UserDTO updatedUser = userService.updateUserDetails(user);
        publisher.publishEvent(new NewUserEvent(updatedUser.getEmail(), PROFILE_UPDATE));
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", updatedUser, "events", eventService.getEventsByUserId(user.getId()),"roles", roleService.getRoles()))
                        .message("User updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/verify/code/{email}/{code}")
    public ResponseEntity<HttpResponse> verifyCode(@PathVariable("email") String email, @PathVariable("code") String code) {
        UserDTO user = userService.verifyCode(email, code);
        publisher.publishEvent(new NewUserEvent(user.getEmail(), LOGIN_ATTEMPT_SUCCESS));
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", user, "access_token", tokenProvider.createAccessToken(getUserPrincipal(user))
                                , "refresh_token", tokenProvider.createRefreshToken(getUserPrincipal(user))))
                        .message("Login Success")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/resetpassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) {
        userService.resetPassword(email);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("Email sent. Please check your email to reset your password.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/verify/account/{key}")
    public ResponseEntity<HttpResponse> verifyAccount(@PathVariable("key") String key) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message(userService.verifyAccountKey(key).isEnabled() ? "Account already verified" : "Account verified")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/verify/password/{key}")
    public ResponseEntity<HttpResponse> verifyPasswordUrl(@PathVariable("key") String key) {
        UserDTO user = userService.verifyPasswordKey(key);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of("user", user))
                        .message("Please enter a new password")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PutMapping("/new/password")
    public ResponseEntity<HttpResponse> resetPasswordWithKey(@RequestBody @Valid NewPasswordForm form) {
        userService.updatePassword(form.getUserId(), form.getPassword(), form.getConfirmedPassword());
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .message("Password reset successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/update/role/{roleName}")
    public ResponseEntity<HttpResponse> updateUserRole(Authentication authentication, @PathVariable("roleName") String roleName) {
        UserDTO userDTO = getAuthenticatedUser(authentication);
        userService.updateUserRole(userDTO.getId(), roleName);
        publisher.publishEvent(new NewUserEvent(userDTO.getEmail(), ROLE_UPDATE));
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .data(of("user", userService.getUserById(userDTO.getId()), "events", eventService.getEventsByUserId(userDTO.getId()),"roles", roleService.getRoles()))
                        .timeStamp(now().toString())
                        .message("Role updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/update/settings")
    public ResponseEntity<HttpResponse> updateAccountSettings(Authentication authentication, @RequestBody @Valid SettingsForm form) {
        UserDTO userDTO = getAuthenticatedUser(authentication);
        userService.updateAccountSettings(userDTO.getId(), form.getEnabled(), form.getNotLocked());
        publisher.publishEvent(new NewUserEvent(userDTO.getEmail(), ACCOUNT_SETTINGS_UPDATE));
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .data(of("user", userService.getUserById(userDTO.getId()), "events", eventService.getEventsByUserId(userDTO.getId()),"roles", roleService.getRoles()))
                        .timeStamp(now().toString())
                        .message("Account settings updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PatchMapping("/update/image")
    public ResponseEntity<HttpResponse> updateProfileImage(Authentication authentication, @RequestParam("image") MultipartFile image) {
        UserDTO user = getAuthenticatedUser(authentication);
        userService.updateImage(user, image);
        publisher.publishEvent(new NewUserEvent(user.getEmail(), PROFILE_PICTURE_UPDATE));
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .data(of("user", userService.getUserById(user.getId()), "events", eventService.getEventsByUserId(user.getId()),"roles", roleService.getRoles()))
                        .timeStamp(now().toString())
                        .message("Profile image updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping(value = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getProfileImage(@PathVariable("fileName") String fileName) throws Exception {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
    }

    @PatchMapping("/update/password")
    public ResponseEntity<HttpResponse> updatePassword(Authentication authentication, @RequestBody @Valid UpdatePasswordForm form) {
        UserDTO userDTO = getAuthenticatedUser(authentication);
        userService.updatePassword(userDTO.getId(), form.getCurrentPassword(), form.getNewPassword(), form.getConfirmNewPassword());
        publisher.publishEvent(new NewUserEvent(userDTO.getEmail(), EventType.PASSWORD_UPDATE));
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserById(userDTO.getId()), "events", eventService.getEventsByUserId(userDTO.getId()),"roles", roleService.getRoles()))
                        .message("Password updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }



    @GetMapping("/refresh/token")
    public ResponseEntity<HttpResponse> refreshToken(HttpServletRequest request) {
        if (isHeaderAndTokenValid(request)) {
            String token = request.getHeader(AUTHORIZATION).substring(TOKEN_PREFIX.length());
            UserDTO user = userService.getUserById(tokenProvider.getSubject(token, request));
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(now().toString())
                            .data(of("user", user, "access_token", tokenProvider.createAccessToken(getUserPrincipal(user))
                                    , "refresh_token", token))
                            .message("Token refreshed")
                            .status(OK)
                            .statusCode(OK.value())
                            .build());
        }
        return ResponseEntity.badRequest().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Token refresh missing or invalid")
                        .developerMessage("Token refresh missing or invalid")
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build());
    }

    private boolean isHeaderAndTokenValid(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION) != null
                && request.getHeader(AUTHORIZATION).startsWith(TOKEN_PREFIX)
                && tokenProvider.isTokenValid(tokenProvider.getSubject(request.getHeader(AUTHORIZATION).substring(TOKEN_PREFIX.length()), request),
                request.getHeader(AUTHORIZATION).substring(TOKEN_PREFIX.length()));
    }

    @GetMapping("/error")
    public ResponseEntity<HttpResponse> handleError(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason("An error occurred")
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build());
    }

    private UserDTO authenticate(String email, String password) {
        try {
//            if(null != userService.getUserByEmail(email)){
//                publisher.publishEvent(new NewUserEvent(email, EventType.LOGIN_ATTEMPT));
//            }
            Authentication authentication = authenticationManager.authenticate(unauthenticated(email, password));
            UserDTO loggedInUser;
            loggedInUser = getLoggedInUser(authentication);
            return loggedInUser;
        } catch (Exception exception) {
            publisher.publishEvent(new NewUserEvent(email, EventType.LOGIN_ATTEMPT_FAILURE));
            processError(request, response, exception);
            throw new ApiException(exception.getMessage());
        }
    }

    private URI getUrl() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }

    private ResponseEntity<HttpResponse> sendResponse(UserDTO user) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", user, "access_token", tokenProvider.createAccessToken(getUserPrincipal(user))
                                , "refresh_token", tokenProvider.createRefreshToken(getUserPrincipal(user))))
                        .message("Login Success")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    private UserPrincipal getUserPrincipal(UserDTO user) {
        return new UserPrincipal(toUser(userService.getUserByEmail(user.getEmail())), roleService.getRoleByUserId(user.getId()));
    }

    private ResponseEntity<HttpResponse> sendVerificationCode(UserDTO user) {
        userService.sendVerificationCode(user);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", user))
                        .message("Verification Code Sent")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/post/new")
    public ResponseEntity<HttpResponse> newGardeningPost(@AuthenticationPrincipal UserDTO user) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "post", userService.getUserByEmail(user.getEmail())))
                        .message("Post retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addgardeningpost/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> addGardeningPostToUser(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id,
                                                               @ModelAttribute GardeningPost gardeningPost, @RequestParam("image") MultipartFile image) {
        GardeningPost createdGardeningPost = postService.createPost(id, gardeningPost, image);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "gardeningPost", createdGardeningPost))
                        .message(String.format("Gardening post added to user with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addgardeningpost/{id}")
    public ResponseEntity<HttpResponse> addGardeningPostNoPhotoToUser(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id,
                                                               @ModelAttribute GardeningPost gardeningPost) {
        GardeningPost createdGardeningPost = postService.createGardeningPostNoPhoto(id, gardeningPost);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "gardeningPost", createdGardeningPost))
                        .message(String.format("Gardening post added to user with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addrecipepost/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> addRecipePostToUser(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id,
                                                               @ModelAttribute RecipePost recipePost, @RequestParam("image") MultipartFile image) {
        RecipePost createdRecipePost = postService.createPost(id, recipePost, image);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "recipePost", createdRecipePost))
                        .message(String.format("Recipe post added to user with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addimadepost/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> addIMadePostToUser(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id,
                                                            @ModelAttribute IMadePost iMadePost, @RequestParam("image") MultipartFile image) {
        IMadePost createdIMadePost = postService.createPost(id, iMadePost, image);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "iMadePost", createdIMadePost))
                        .message(String.format("I made post added to user with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addotherpost/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> addOtherPostToUser(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id,
                                                           @ModelAttribute OtherPost otherPost, @RequestParam("image") MultipartFile image) {
       OtherPost createdOtherPost = postService.createPost(id, otherPost, image);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "otherPost", createdOtherPost))
                        .message(String.format("Other post added to user with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/gardening/list")
    public ResponseEntity<HttpResponse> getAllGardeningPosts(@AuthenticationPrincipal UserDTO user,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<GardeningPost> gardeningPosts = postService.getAllGardeningPost(pageable);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "posts", gardeningPosts))
                        .message("Gardening posts retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/recipe/list")
    public ResponseEntity<HttpResponse> getAllRecipePosts(@AuthenticationPrincipal UserDTO user,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<RecipePost> recipePosts = postService.getAllRecipePost(pageable);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "posts", recipePosts))
                        .message("Recipe posts retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/recipe/count")
    public ResponseEntity<HttpResponse> getAllRecipePosts(@AuthenticationPrincipal UserDTO user) {
int postCount = postService.getAllRecipePostCount();
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("postCount", postCount))
                        .message("Recipe posts counted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/imade/list")
    public ResponseEntity<HttpResponse> getAllIMAdePosts(@AuthenticationPrincipal UserDTO user,
                                                          @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<IMadePost> iMadePosts = postService.getAllIMadePost(pageable);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "posts", iMadePosts))
                        .message("I made posts retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/other/list")
    public ResponseEntity<HttpResponse> getAllOtherPosts(@AuthenticationPrincipal UserDTO user,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<OtherPost> otherPosts = postService.getAllOtherPost(pageable);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "posts", otherPosts))
                        .message("Other posts retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addgardeningcomment/{userId}/{postId}")
    public ResponseEntity<HttpResponse> addCommentToGardeningPost(@AuthenticationPrincipal UserDTO user, @PathVariable("userId") Long userId, @PathVariable("postId") Long postId,
                                                           @RequestBody GardeningComment gardeningComment) {

        GardeningComment createdComment = postService.addGardeningComment(userId, postId, gardeningComment);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "post", postService.getGardeningPostById(postId)))
                        .message("Comment added")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());

    }

    @PostMapping(path = "/addrecipecomment/{userId}/{postId}")
    public ResponseEntity<HttpResponse> addCommentToRecipePost(@AuthenticationPrincipal UserDTO user, @PathVariable("userId") Long userId, @PathVariable("postId") Long postId,
                                                               @RequestBody RecipeComment recipeComment) {
        RecipeComment createdComment = postService.addRecipeComment(userId, postId, recipeComment);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "post", postService.getRecipePostById(postId)))
                        .message("Comment added")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/gardeningpost/comments/{id}")
    public ResponseEntity<HttpResponse> getAllGardeningPostComments(@PathVariable("id") long id) {
        List<GardeningComment> gardeningComment = postService.getAllGardeningCommentsByPostId(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of(
                                "comments", gardeningComment))
                        .message("Gardening Post comments retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }



    @GetMapping("/recipepost/comments/{id}")
    public ResponseEntity<HttpResponse> getAllRecipePostComments(@PathVariable("id") long id) {
        List<RecipeComment> recipeComments = postService.getAllRecipeCommentsByPostId(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of(
                                "comments", recipeComments))
                        .message("Recipe Post comments retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addimadecomment/{userId}/{postId}")
    public ResponseEntity<HttpResponse> addCommentToIMadePost(@AuthenticationPrincipal UserDTO user, @PathVariable("userId") Long userId, @PathVariable("postId") Long postId,
                                                               @RequestBody IMadeComment iMadeComment) {
        IMadeComment createdComment = postService.addIMadeComment(userId, postId, iMadeComment);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "post", postService.getIMadePostById(postId)))
                        .message("Comment added")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/imadepost/comments/{id}")
    public ResponseEntity<HttpResponse> getAllIMadePostComments(@PathVariable("id") long id) {
        List<IMadeComment> iMadeComments = postService.getAllIMadeCommentsByPostId(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of(
                                "comments", iMadeComments))
                        .message("I Made Post comments retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addothercomment/{userId}/{postId}")
    public ResponseEntity<HttpResponse> addCommentToOtherPost(@AuthenticationPrincipal UserDTO user, @PathVariable("userId") Long userId, @PathVariable("postId") Long postId,
                                                              @RequestBody OtherComment otherComment) {
        OtherComment createdComment = postService.addOtherComment(userId, postId, otherComment);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "post", postService.getOtherPostById(postId)))
                        .message("Comment added")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/otherpost/comments/{id}")
    public ResponseEntity<HttpResponse> getAllOtherPostComments(@PathVariable("id") long id) {
        List<OtherComment> otherComments = postService.getAllOtherCommentsByPostId(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(of(
                                "comments", otherComments))
                        .message("Other Post comments retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/update/gardeningcomment/{id}")
    public ResponseEntity<HttpResponse> updateGardeningComment(@PathVariable("id") long id, @RequestBody @Valid CommentForm form) {
        postService.updateComment(id, form.getComment_text(), PostType.GARDENING);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/update/recipecomment/{id}")
    public ResponseEntity<HttpResponse> updateRecipeComment(@PathVariable("id") long id, @RequestBody @Valid CommentForm form) {
        postService.updateComment(id, form.getComment_text(), PostType.RECIPE);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PatchMapping("/update/imadecomment/{id}")
    public ResponseEntity<HttpResponse> updateIMadeComment(@PathVariable("id") long id, @RequestBody @Valid CommentForm form) {
        postService.updateComment(id, form.getComment_text(), PostType.I_MADE);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PatchMapping("/update/othercomment/{id}")
    public ResponseEntity<HttpResponse> updateOtherComment(@PathVariable("id") long id, @RequestBody @Valid CommentForm form) {
        postService.updateComment(id, form.getComment_text(), PostType.OTHER);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .message("Comment updated successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
