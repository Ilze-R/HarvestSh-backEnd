package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.dto.UserDTO;
import com.example.demo.enumeration.EventType;
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
import java.util.List;

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
        return  sendResponse(user);

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
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", user, "events", eventService.getEventsByUserId(user.getId()),"roles", roleService.getRoles()))
                        .message("Profile Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
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
                        .message("Gardening post retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/addgardeningpost/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> addGardeningPostToUser(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id,
                                                               @ModelAttribute GardeningPost gardeningPost, @RequestParam("image") MultipartFile image) {
        GardeningPost createdGardeningPost = postService.createGardeningPost(id, gardeningPost, image);
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
        RecipePost createdRecipePost = postService.createRecipePost(id, recipePost, image);
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
        IMadePost createdIMadePost = postService.createIMadePost(id, iMadePost, image);
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
       OtherPost createdOtherPost = postService.createOtherPost(id, otherPost, image);
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
                        .message("Gardening posts retrieved")
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
                        .message("Gardening posts retrieved")
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
                        .message("Gardening posts retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
