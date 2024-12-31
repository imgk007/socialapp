package com.example.socialapp.controller;

import com.example.socialapp.jpa.PostRepository;
import com.example.socialapp.jpa.UserRepository;
import com.example.socialapp.user.Post;
import com.example.socialapp.user.User;
import com.example.socialapp.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SocialJpaController {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public SocialJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository=postRepository;
    }

    @GetMapping("/jpa/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    //here we are making the method as entity model to wrap the pojo and with link builder,we made it as link
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> getSpecificUser(@PathVariable int id) {
        Optional<User> user= userRepository.findById(id);
        //if the user is null, it will throw exception
        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        EntityModel<User> entityModel=EntityModel.of(user.get());

        //instead of hardcoding the URL, here it to point the controller class and its method using HATEOAS
        WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).getAllUsers());
        //here we are adding the link which we retieved with linkTo()
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostForUser (@PathVariable int id) {
        Optional<User> user= userRepository.findById(id);
        //if the user is null, it will throw exception
        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        return user.get().getPosts();
    }
    
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser= userRepository.save(user);

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

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser (@PathVariable int id, @Valid @RequestBody Post post ) {
        Optional<User> user= userRepository.findById(id);

        //if the user is null, it will throw exception
        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
