package com.digyai.notificationservice.controller;

import com.digyai.notificationservice.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam String userId,
            @RequestParam(required = false) String email,
            HttpSession session) {

        session.setAttribute("userId", userId);

        if (email != null && !email.isBlank()) {
            userService.saveUserProfile(userId, email);
            logger.info("[AUTH] Login: userId={} email={}", userId, email);
        } else {
            logger.info("[AUTH] Login: userId={} (no email provided)", userId);
        }

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "userId", userId,
                "message", "Logged in successfully"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("status", "success", "message", "Logged out"));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> me(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of("status", "unauthenticated"));
        }
        String email = userService.getUserEmail(userId);
        return ResponseEntity.ok(Map.of(
                "status", "authenticated",
                "userId", userId,
                "email", email != null ? email : ""
        ));
    }
}
