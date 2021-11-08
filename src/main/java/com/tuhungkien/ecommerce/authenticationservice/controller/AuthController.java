package com.tuhungkien.ecommerce.authenticationservice.controller;

import com.tuhungkien.ecommerce.authenticationservice.entity.UserInfo;
import com.tuhungkien.ecommerce.authenticationservice.model.AccountCreationRequest;
import com.tuhungkien.ecommerce.authenticationservice.model.AccountCreationResponse;
import com.tuhungkien.ecommerce.authenticationservice.model.AuthenticationRequest;
import com.tuhungkien.ecommerce.authenticationservice.model.AuthenticationResponse;
import com.tuhungkien.ecommerce.authenticationservice.service.AuthDataService;
import com.tuhungkien.ecommerce.authenticationservice.service.CustomUserDetailsService;
import com.tuhungkien.ecommerce.authenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthDataService authDataService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        System.out.println("haha");
        return ResponseEntity.ok("success");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(
            @RequestBody AccountCreationRequest accountCreationRequest)
            throws Exception {

        if (authDataService.findByUsername(accountCreationRequest.getUsername()) != null) {
            return ResponseEntity.ok(
                    new AccountCreationResponse("failure", "Username already exist"));
        }

        if (authDataService.findByEmail(accountCreationRequest.getEmail()) != null) {
            return ResponseEntity.ok(
                    new AccountCreationResponse("failure", "Email already exist"));
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(accountCreationRequest.getEmail());
        userInfo.setFirstName(accountCreationRequest.getFirstname());
        userInfo.setLastName(accountCreationRequest.getLastname());
        userInfo.setPassword(accountCreationRequest.getPassword());
        userInfo.setUsername(accountCreationRequest.getUsername());

        authDataService.createUserProfile(userInfo);
        return ResponseEntity.ok(new AccountCreationResponse("success", null));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) {

        System.out.println("login req" + authenticationRequest);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            // tra ve jwt
            String jwt = jwtTokenUtil.generateToken(userDetails);
            System.out.println(jwt);
            return  ResponseEntity.ok((new AuthenticationResponse(jwt, null, authenticationRequest.getUsername(), "login successfully")));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AuthenticationResponse(null, "Incorrect username or password.",
                    null, "login failure"));
        } catch (Exception e) {
            return ResponseEntity.ok(new AuthenticationResponse(null, "Username does not exist.",
                    null, "login failure"));
        }
    }
}
