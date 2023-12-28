package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "Rahul123";
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        System.out.println(encodedPassword);

        boolean matches = bCryptPasswordEncoder.matches(password,encodedPassword);

        assertThat(matches).isTrue();
    }
}
