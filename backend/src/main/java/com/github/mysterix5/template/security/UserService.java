package com.github.mysterix5.template.security;

import com.github.mysterix5.template.model.error.CustomException;
import com.github.mysterix5.template.model.security.UserEntity;
import com.github.mysterix5.template.model.security.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserMongoRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    public void createUser(UserRegisterDTO userCreationDTO) {
        if (userCreationDTO.getUsername() == null || userCreationDTO.getUsername().isBlank()) {
            throw new CustomException("username is blank");
        }
        if(!isUsername(userCreationDTO.getUsername())){
            throw new CustomException("Your username is not valid");
        }
        if (userRepository.existsByUsernameIgnoreCase(userCreationDTO.getUsername())) {
            throw new CustomException("a user with this name already exists");
        }
        var tmp = new PasswordData(userCreationDTO.getUsername(), userCreationDTO.getPassword());
        RuleResult passwordValidationResult = passwordValidator.validate(tmp);
        if (!passwordValidationResult.isValid()) {
            throw new CustomException("Your password is not secure enough", passwordValidator.getMessages(passwordValidationResult));
        }
        if (!userCreationDTO.getPassword().equals(userCreationDTO.getPasswordRepeat())){
            throw new CustomException("Passwords have to match");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        user.addRole("user");
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username)
                .map(user -> new User(user.getUsername(), user.getPassword(), List.of()))
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean isUsername(String username) {
        String usernameRegex = "^(?=.{3,20}$)(?![_-])(?!.*[_-]{2})[a-zA-Z0-9-_]+(?<![_-])$";
        return username.matches(usernameRegex);
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id).orElseThrow();
    }
}
