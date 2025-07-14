package com.example.controller;

import com.example.model.FCMToken;
import com.example.repository.FCMTokenRepo;
import com.example.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {

    @Autowired
    private FCMTokenRepo tokenRepo;

    @PostMapping("/register")
    public ResponseEntity<String> registerToken(@RequestBody Map<String, String> req){
        String userId = req.get("userId");
        String fcmToken = req.get("fcmToken");

        if (userId == null || fcmToken == null) {
            return ResponseEntity.badRequest().body("Missing userId or token");
        }

        Optional<FCMToken> existing = tokenRepo.findByUserId(userId);
        FCMToken token = existing.orElse(new FCMToken());

        token.setUserId(userId);
        token.setFcmToken(fcmToken);
        token.setUpdatedAt(LocalDateTime.now());
        tokenRepo.save(token);

        return ResponseEntity.ok("Token registered");
    }
}
