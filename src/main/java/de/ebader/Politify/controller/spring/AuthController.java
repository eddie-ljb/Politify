package de.ebader.Politify.controller.spring;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import de.ebader.Politify.entities.benutzerdaten.User;
import de.ebader.Politify.repositories.benutzerdaten.UserRepository;
import de.ebader.Politify.services.UserDetailsImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    public static int zaehler = 0;
    
    @GetMapping("/signin")
    public ModelAndView showLoginPage(Model model) {
        model.addAttribute("logInRequest", new LoginRequest());
    	return new ModelAndView("login.html");
    }
    
    @GetMapping("/signup")
    public ModelAndView showRegisterPage(Model model) {
        model.addAttribute("signUpRequest", new SignupRequest());
        return new ModelAndView("register.html");
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody String payload) {
        // Extrahiere die Benutzeranmeldeinformationen aus dem Payload
        String[] parts = payload.split("&");
        String username = null;
        String password = null;
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                if ("benutzername".equals(key)) {
                    username = value;
                } else if ("passwort".equals(key)) {
                    password = value;
                }
            }
        }
        // Führe die Benutzerauthentifizierung durch
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt,
                                                     userDetails.getId(),
                                                     userDetails.getUsername(),
                                                     userDetails.getEmail(),
                                                     userDetails.getPermission()));
        } catch (AuthenticationException e) {
            // Fehlerbehandlung für ungültige Anmeldeinformationen
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody String payload) {
        // Extrahiere die Daten aus dem Payload
        String[] parts = payload.split("&");
        String benutzername = null;
        String email = null;
        String passwort = null;
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                if ("benutzername".equals(key)) {
                    benutzername = value;
                } else if ("email".equals(key)) {
                    email = value;
                } else if ("passwort".equals(key)) {
                    passwort = value;
                }
            }
        }

        // Überprüfe, ob der Benutzer bereits existiert
        if (userRepository.existsByUsername(benutzername)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Erstelle einen neuen Benutzer
        User user = new User();
        user.setId(Long.valueOf(zaehler));
        user.setBenutzername(benutzername);
        user.setEmail(email);
        user.setPasswort(encoder.encode(passwort));
        user.setPermission(1L); // Setze die Standardberechtigung für den neuen Benutzer
        zaehler++;
        userRepository.save(user);

        // Authentifiziere den neuen Benutzer
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(benutzername, passwort));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt,
                                                     userDetails.getId(),
                                                     userDetails.getUsername(),
                                                     userDetails.getEmail(),
                                                     userDetails.getPermission()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}