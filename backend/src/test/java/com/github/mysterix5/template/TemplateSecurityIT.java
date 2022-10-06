package com.github.mysterix5.template;

import com.github.mysterix5.template.model.security.LoginResponse;
import com.github.mysterix5.template.model.security.UserAuthenticationDTO;
import com.github.mysterix5.template.model.security.UserInfoDTO;
import com.github.mysterix5.template.model.security.UserRegisterDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemplateSecurityIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void authenticationTests() {
        // fail and succeed with registering
        ResponseEntity<Void> userCreationResponse = restTemplate.postForEntity("/api/auth/register", new UserRegisterDTO("", "testASDF3%", "testASDF3%"), Void.class);
        assertThat(userCreationResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        userCreationResponse = restTemplate.postForEntity("/api/auth/register", new UserRegisterDTO("user1", "test", "test"), Void.class);
        assertThat(userCreationResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        userCreationResponse = restTemplate.postForEntity("/api/auth/register", new UserRegisterDTO("user1", "testASDF3%", "testASDF3%"), Void.class);
        assertThat(userCreationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        userCreationResponse = restTemplate.postForEntity("/api/auth/register", new UserRegisterDTO("user2", "testASDF3!", "testASDF3!"), Void.class);
        assertThat(userCreationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // login user1
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity("/api/auth/login", new UserAuthenticationDTO("user1", "testASDF3%"), LoginResponse.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token1 = loginResponse.getBody().getToken();

        // login user2
        loginResponse = restTemplate.postForEntity("/api/auth/login", new UserAuthenticationDTO("user2", "testASDF3!"), LoginResponse.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token2 = loginResponse.getBody().getToken();

        // try to get user info with and without authorization
        var userInfoResponse = restTemplate.getForEntity("/api/auth/userinfo", UserInfoDTO.class);
        assertThat(userInfoResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        var userInfoResponse2 = restTemplate.exchange("/api/auth/userinfo", HttpMethod.GET, new HttpEntity<>(createHeaders(token1)), UserInfoDTO.class);
        assertThat(userInfoResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userInfoResponse2.getBody().getUsername()).isEqualTo("user1");
    }

    private HttpHeaders createHeaders(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        return headers;
    }
}
