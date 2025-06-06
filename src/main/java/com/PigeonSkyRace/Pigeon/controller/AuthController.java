package com.PigeonSkyRace.Pigeon.controller;

import com.PigeonSkyRace.Pigeon.dto.AuthRequest;
import com.PigeonSkyRace.Pigeon.model.User;
import com.PigeonSkyRace.Pigeon.service.BreederService;
import com.PigeonSkyRace.Pigeon.util.PasswordUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationTime;

    @Autowired
    private BreederService breederService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User breeder) {
        breeder.setRole("breeder");
        User createdBreeder = breederService.createBreeder(breeder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBreeder);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<User> optionalUser = breederService.findByEmail(authRequest.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (PasswordUtil.hashPassword(authRequest.getPassword()).equals(user.getPassword())) {
                logger.info("User authenticated successfully: " + user.getEmail());

                String token = JWT.create()
                        .withSubject(user.getEmail())
                        .withClaim("userId", user.getId())
                        .withClaim("role", user.getRole())
                        .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
                        .sign(Algorithm.HMAC512(jwtSecret));

                return ResponseEntity.ok(Map.of("token", token));
            }
        }
        logger.warning("Unauthorized login attempt for email: " + authRequest.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}
