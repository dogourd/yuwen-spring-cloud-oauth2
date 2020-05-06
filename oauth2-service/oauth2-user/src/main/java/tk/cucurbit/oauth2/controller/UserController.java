package tk.cucurbit.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tk.cucurbit.oauth2.entity.UserEntity;
import tk.cucurbit.oauth2.exceptions.UserNotFoundException;
import tk.cucurbit.oauth2.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username/{username}")
    public UserEntity findUserByUsername(@PathVariable(name = "username") String username) throws UserNotFoundException {
        return userService.findUserByUsername(username);
    }

}
