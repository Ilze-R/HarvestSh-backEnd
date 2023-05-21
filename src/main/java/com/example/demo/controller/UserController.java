package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;


//  @PutMapping("change/{role}") // api/user/change/{role}
//  public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Role role) {
//
//    userService.changeRole(role, userPrincipal.getUsername());
//
//    return ResponseEntity.ok(true);
//  }


  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + id));

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

//  @DeleteMapping("/delete/{id}")
//  public ResponseEntity removeUser(@PathVariable Long id) {
//    userService.deleteUser(id);
//    return new ResponseEntity(HttpStatus.OK);
//  }


}
