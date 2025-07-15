package com.example.controller;

import com.example.model.TokenRequest;
import com.example.model.FCMToken;
import com.example.repository.FCMTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tokens")
public class TokenRequestController {

    @Autowired
    private FCMTokenRepo tokenRepo;

    @PostMapping("/register")
    public ResponseEntity<String> registerToken(@RequestBody TokenRequest request) {
        System.out.println("📥 Received TokenRequest: " + request.getUserId() + " - " + request.getToken());

        if (request.getUserId() == null || request.getUserId().trim().isEmpty()
                || request.getToken() == null || request.getToken().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing userId or token");
        }

        // Save or update token
        FCMToken tokenEntry = new FCMToken();
        tokenEntry.setUserId(request.getUserId());
        tokenEntry.setFcmToken(request.getToken());
        tokenEntry.setUpdatedAt(LocalDateTime.now());

        tokenRepo.save(tokenEntry);

        return ResponseEntity.ok("✅ Token saved");
    }
}
