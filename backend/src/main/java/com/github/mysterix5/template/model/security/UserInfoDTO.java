package com.github.mysterix5.template.model.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfoDTO {
    private String username;
    private String id;
    private List<String> roles;

    public UserInfoDTO(UserEntity user){
        username = user.getUsername();
        id = user.getId();
        roles = user.getRoles();
    }
}
