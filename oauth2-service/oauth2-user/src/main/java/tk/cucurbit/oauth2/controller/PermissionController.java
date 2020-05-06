package tk.cucurbit.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.cucurbit.oauth2.service.PermissionService;

import java.util.Set;

@RestController
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permission/getByUserId")
    public Set<String> findPermissionByUserId(String userId) {
        return permissionService.findPermissionByUserId(userId);
    }
}
