package edu.cit.eupena.felix.campusequipmentloan.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String password) {
        // Check if username already exists
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already taken!");
        }

        User user = new User(username, password); // (⚠️ Hash password in real apps!)
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password)) // (⚠️ hash check in real apps)
                .orElseThrow(() -> new RuntimeException("Invalid credentials!"));
    }
}
