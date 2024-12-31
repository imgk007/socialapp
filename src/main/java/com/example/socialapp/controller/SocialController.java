package com.example.socialapp.controller;

import com.example.socialapp.user.User;
import com.example.socialapp.user.UserDaoService;
import com.example.socialapp.user.UserNotFoundException;
import jakarta.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class SocialController {
    private UserDaoService service;

    public SocialController(UserDaoService service) {
        this.service=service;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }


    //here we are making the method as entity model to wrap the pojo and with link builder,we made it as link
    @GetMapping("/users/{id}")
    public EntityModel<User> getSpecificUser(@PathVariable int id) {
        User user=service.findOne(id);
        //if the user is null, it will throw exception
        if(user==null)
            throw new UserNotFoundException("id:"+id);
        EntityModel<User> entityModel=EntityModel.of(user);

        //instead of hardcoding the URL, here it to point the controller class and its method using HATEOAS
        WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).getAllUsers());
        //here we are adding the link which we retieved with linkTo()
        entityModel.add(link.withRel("all-users"));
        return entityModel;
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
