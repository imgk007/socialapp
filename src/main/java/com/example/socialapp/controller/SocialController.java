package com.example.socialapp.controller;

import com.example.socialapp.user.User;
import com.example.socialapp.user.UserDaoService;
import com.example.socialapp.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SocialController {

    private UserDaoService service;

    public SocialController(UserDaoService service) {
        this.service=service;
    }

    @GetMapping("/hello")
    public String helloWorld () {
        return "Hi, it works GK!!!!!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User getSpecificUser(@PathVariable int id) {
        User user=service.findOne(id);
        //if the user is null, it will throw exception
        if(user==null)
            throw new UserNotFoundException("id:"+id);

        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteById(id);
    }
    
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser= service.save(user);

        //service.save(user);
        //appending the location with the url i.e., users/{id} and return in body
        //using http header->location header
        // fromCurrentRequest helps to get the url->/users buildandexpand is used to append the specific id with /{id}
        // without hardcoding, dynamically done, toUri is used to convert this as whole uri
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
