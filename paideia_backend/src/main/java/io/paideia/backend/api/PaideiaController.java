package io.paideia.backend.api;


import io.paideia.backend.domain.entities.User;
import io.paideia.backend.domain.services.PaideiaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PaideiaController {

    private static final Logger log = LoggerFactory.getLogger(PaideiaController.class);
    private PaideiaService paideiaService;

    PaideiaController(PaideiaService paideiaService) {
        this.paideiaService = paideiaService;
    }


    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestParam(value = "email") String email, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = paideiaService.registerUser(username, password, email);
        log.atDebug().log("User {} registered successfully", user);
        return ResponseEntity.ok().body(user.toString());
    }

}
