package com.containerdepot.metcon.web;

import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(
        username = "admin",
        roles = {"ADMIN"}
)
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(post("/register")
                .param("username", "username")
                .param("email", "test@mail")
                .param("password", "test123456")
                .param("confirmPassword", "test123456")
                .param("firstName", "FIRST")
                .param("lastName", "LAST")
                .param("company", "MET")
                .param("roles", "USER")
                .with(csrf())
        ).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));

        Optional<UserEntity> optionalUser = userRepository.findByUsername("username");
        Assertions.assertTrue(optionalUser.isPresent());

        UserEntity userEntity = optionalUser.get();
        Assertions.assertEquals("test@mail", userEntity.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("test123456", userEntity.getPassword()));
        Assertions.assertEquals("FIRST", userEntity.getFirstName());
        Assertions.assertEquals("LAST", userEntity.getLastName());
        Assertions.assertFalse(userEntity.getRoles().isEmpty());
        Assertions.assertEquals("USER", userEntity.getRoles().stream().findFirst().get().getRole().name());
    }

}
