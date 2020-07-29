package tk.cucurbit.oauth2.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tk.cucurbit.oauth2.annotations.IgnoreResponseAdvice;
import tk.cucurbit.oauth2.entity.UserEntity;
import tk.cucurbit.oauth2.exceptions.UserNotFoundException;
import tk.cucurbit.oauth2.service.UserService;

import javax.validation.constraints.NotNull;

@Validated
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username/{username}")
    public UserEntity findUserByUsername(@NotNull @PathVariable(name = "username") String username) throws UserNotFoundException {
        return userService.findUserByUsername(username);
    }

    @Data
    @IgnoreResponseAdvice
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Param {
        @NotNull
        private String username;
    }
}

