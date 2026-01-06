package io.paideia.backend.controller;


import io.paideia.backend.entities.User;
import io.paideia.backend.services.PaideiaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class PaideiaController {

    private static final Logger log = LoggerFactory.getLogger(PaideiaController.class);

    private final PaideiaService paideiaService;

    PaideiaController(PaideiaService paideiaService) {
        this.paideiaService = paideiaService;
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return paideiaService.getUser(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestParam(value = "email") String email, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = paideiaService.registerUser(username, password, email);
        log.atDebug().log("User {} registered successfully", user);
        return ResponseEntity.ok(user.toString());
    }

}
