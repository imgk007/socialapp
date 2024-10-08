package com.example.socialapp.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users=new ArrayList<>();

    private static int userCount=0;
    static {
        users.add(new User(++userCount,"GK", LocalDate.now().minusYears(24)));
        users.add(new User(++userCount,"PK", LocalDate.now().minusYears(24)));
        users.add(new User(++userCount,"RR", LocalDate.now().minusYears(25)));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id) {
        Predicate<User> userPredicate = user -> user.getId().equals(id);
        return users.stream().filter(userPredicate).findFirst().orElse(null);

        //return users.get(id);
    }

    public void deleteById(int id) {
        Predicate<User> userPredicate = user -> user.getId().equals(id);
        users.removeIf(userPredicate);
    }

    public User save(User user) {
        user.setId(++userCount);
        users.add(user);
        return user;
    }
}
