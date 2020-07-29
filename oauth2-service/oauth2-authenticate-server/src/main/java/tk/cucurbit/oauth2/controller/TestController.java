package tk.cucurbit.oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/login")
    public void login() {

    }
}
