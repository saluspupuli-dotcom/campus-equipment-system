package edu.cit.eupena.felix.campusequipmentloan.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user.getUsername(), user.getPassword());
    }

    // ✅ Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        userService.login(user.getUsername(), user.getPassword());
        return "Login successful!";
    }

    // ✅ Logout (simple placeholder)
    @PostMapping("/logout")
    public String logout() {
        return "Logged out successfully!";
    }
}
