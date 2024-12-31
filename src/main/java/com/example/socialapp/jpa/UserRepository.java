package com.example.socialapp.jpa;

import com.example.socialapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
