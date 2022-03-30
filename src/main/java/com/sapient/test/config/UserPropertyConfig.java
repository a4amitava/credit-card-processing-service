package com.sapient.test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Setter
@Getter
public class UserPropertyConfig {
    private final List<Users> users = new ArrayList<>();

    @Setter
    @Getter
    public static class Users {
        private String userName;
        private String password;
        private List<String> roles;
    }
}
