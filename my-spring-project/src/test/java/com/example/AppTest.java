package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.MockMvc;

public class AppTest {

    private MockMvc mockMvc;
    private Validator validator;

    @BeforeEach
    public void setup() {
        mockMvc = configureMockMvc();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private MockMvc configureMockMvc() {
        return standaloneSetup(new RegistrationController()).build();
    }

    @Test
    public void testInvalidName() {
        User user = new User();
        user.setName("A");
        user.setEmail("dionithish2002@gmail.com");
        user.setPassword("nithish");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(!violations.isEmpty());

        ConstraintViolation<User> nameViolation = violations.iterator().next();

        assertEquals("Name must be between 5 and 12 characters", nameViolation.getMessage()); // Check the error message
    }

    @Test
    public void testInvalidEmail() {
        User user = new User();
        user.setName("Nithish");
        user.setEmail("invalid-email");
        user.setPassword("nithish");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(!violations.isEmpty());

        ConstraintViolation<User> nameViolation = violations.iterator().next();

        assertEquals("Invalid email format", nameViolation.getMessage()); // Check the error message
    }

    @Test
    public void testInvalidPassword() {
        User user = new User();
        user.setName("Nithish");
        user.setEmail("dionithish2002@gmail.com");
        user.setPassword("as");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(!violations.isEmpty());

        ConstraintViolation<User> nameViolation = violations.iterator().next();

        assertEquals("Password must be at least 6 characters", nameViolation.getMessage()); // Check the error message
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("formregistration"));
    }

    @Test
    public void testRegisterUserWithValidData() throws Exception {
        mockMvc.perform(post("/register")
                .param("name", "johny sins")
                .param("email", "john@example.com")
                .param("password", "sdfsgdsfds"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration-success"));
    }

}
