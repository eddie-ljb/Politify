package de.ebader.Politify.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.ebader.Politify.entities.benutzerdaten.User;
import de.ebader.Politify.repositories.benutzerdaten.UserRepository;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        user.setPasswort(passwordEncoder.encode(user.getPasswort()));
        userRepository.save(user);
    }
}