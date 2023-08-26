package com.example.demo.controller;

import com.example.demo.domain.Give;
import com.example.demo.domain.HttpResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.event.NewUserEvent;
import com.example.demo.form.UpdateForm;
import com.example.demo.service.GiveService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static java.time.LocalTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class GiveController {

    private  final GiveService giveService;
    private final UserService userService;

    @GetMapping("/give/{id}")
    public ResponseEntity<HttpResponse> displayGive (@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id) {
        Give retrievedGive = giveService.getGiveById(id);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "give", retrievedGive))
                        .message(String.format("Give with ID %d retrieved successfully", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/give/list")
    public ResponseEntity<HttpResponse> getGives(@AuthenticationPrincipal UserDTO user) {
        Long userId = user.getId();
        Collection<Give> gives = giveService.getGivesForUser(userId);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "give", gives))
                        .message("Gives retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("/give/new")
    public ResponseEntity<HttpResponse> newGive(@AuthenticationPrincipal UserDTO user) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "give", userService.getUserByEmail(user.getEmail())))
                        .message("Give retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(path = "/give/addtouser/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpResponse> addGiveToUser(@AuthenticationPrincipal UserDTO user, @PathVariable("id") Long id,
                                                      @ModelAttribute Give give, @RequestParam("image") MultipartFile image) {
        Give createdGive = giveService.createGive(id, give, image);
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "give", createdGive))
                        .message(String.format("Give added to user with ID: %s", id))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PatchMapping("/give/update/{id}")
    public ResponseEntity<HttpResponse> updateGive(@PathVariable("id") Long id, @RequestBody Give give){
        Give updatedGive = giveService.updateGive(id, give);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("give", updatedGive))
                        .message("Give updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
}
