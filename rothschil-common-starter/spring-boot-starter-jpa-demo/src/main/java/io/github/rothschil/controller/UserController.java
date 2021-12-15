package io.github.rothschil.controller;

import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.exception.DrunkardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public User getId(){
        throw new DrunkardException(Status.MANY_ERRORS_OPT);
//        return userService.getByKey(8L);
    }
}
