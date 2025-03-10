package com.example.healthcare_system.utiltest;

import com.example.healthcare_system.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {
    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    private String token;

    @BeforeEach
    void setUp() {
        when(userDetails.getUsername()).thenReturn("testuser");
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    void testGenerateToken() {
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void testValidateToken_Valid() {
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_Invalid() {
        UserDetails anotherUser = new User("wrongUser", "password", new java.util.ArrayList<>());
        assertFalse(jwtUtil.validateToken(token, anotherUser));
    }
}
