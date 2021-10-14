package io.github.rothschil.starter.service;

import org.springframework.stereotype.Component;
import io.github.rothschil.jwt.po.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/11/6 - 10:39
 * @since 1.0.0
 */
@Component
public class UserService {

    private static final List<User> USERS = new ArrayList<>();

    static {
        USERS.add(new User("1", "A"));
        USERS.add(new User("2", "B"));
        USERS.add(new User("3", "E"));
        USERS.add(new User("4", "F"));
        USERS.add(new User("5", "G"));
    }

    public User getUserById(String id) {
        List<User> lists = USERS.stream().filter(user -> id.equals(user.getId())).collect(Collectors.toList());
        return lists.get(0);
    }

    public User findByUsername(User params) {
        List<User> lists = USERS.stream().filter(user -> (user.getName().equals(params.getName()))).collect(Collectors.toList());
        if (lists.isEmpty()) {
            return null;
        }
        return lists.get(0);
    }
}
