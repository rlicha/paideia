package io.paideia.backend.controller;


import io.paideia.backend.entities.UserEntity;
import io.paideia.backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/users")
class PaideiaController {

    private static final Logger log = LoggerFactory.getLogger(PaideiaController.class);

    private final UserService userService;

    PaideiaController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String username) {
        return userService.getUser(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    // TODO utiliser un DTO sous forme de record
    public ResponseEntity<String> registerUser(@RequestParam(value = "email") String email, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        UserEntity userEntity = userService.registerUser(username, password, email);
        log.atDebug().log("User {} registered successfully", userEntity);
        return ResponseEntity.ok(userEntity.toString());
    }

}
