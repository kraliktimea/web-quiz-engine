package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //user registration
    @PostMapping(path = "/api/register", consumes = "application/json")
    public ResponseEntity<User> regUser(@Valid @RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) == null) {
            User newUser = userService.saveUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forget your password?");
        }
    }
}
