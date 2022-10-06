package com.github.mysterix5.template.security;

import com.github.mysterix5.template.model.error.CustomErrorDTO;
import com.github.mysterix5.template.model.error.CustomException;
import com.github.mysterix5.template.model.security.UserAuthenticationDTO;
import com.github.mysterix5.template.model.security.UserEntity;
import com.github.mysterix5.template.model.security.UserInfoDTO;
import com.github.mysterix5.template.model.security.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserRegisterDTO registerData) {
        try {
            userService.createUser(registerData);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (CustomException e) {
            log.warn("registering user {} failed", registerData.getUsername());
            return ResponseEntity.badRequest().body(new CustomErrorDTO(e));
        } catch (DuplicateKeyException e) {
            log.warn("registering user {} failed", registerData.getUsername());
            return ResponseEntity.badRequest().body(new CustomErrorDTO("registering user " + registerData.getUsername() + " failed", "database rejected because this user already exists"));
        } catch (Exception e) {
            log.warn("registering user {} failed", registerData.getUsername(), e);
            return ResponseEntity.internalServerError().body(new CustomErrorDTO("registering user " + registerData.getUsername() + " failed due to some server problem"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserAuthenticationDTO loginData) {
        try {
            UserEntity user = userService.findByUsername(loginData.getUsername()).orElseThrow();
            return ResponseEntity.ok(loginService.login(user, loginData));
        } catch (NoSuchElementException e) {
            log.warn("login user {} failed, user not found", loginData.getUsername());
            return ResponseEntity.badRequest().body(new CustomErrorDTO("Login failed", "This user does not exist"));
        } catch (BadCredentialsException e) {
            log.warn("login user {} failed, authentication failed", loginData.getUsername());
            return ResponseEntity.badRequest().body(new CustomErrorDTO("Login failed", "It was not possible to authenticate this 'user', 'password' combination", "Are you sure your credentials are correct?"));
        }
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoDTO> getUserInfo(Principal principal) {
        log.info("get user info for user '{}'", principal.getName());
        UserInfoDTO userInfo = new UserInfoDTO(userService.findById(principal.getName()));
        return ResponseEntity.ok().body(userInfo);
    }

}
