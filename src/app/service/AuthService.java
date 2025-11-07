package app.service;

import app.model.User;
import app.repo.UserRepository;

public class AuthService {
    private final UserRepository repo;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean register(String username, String password) throws Exception {
        User existing = repo.findByUsername(username);
        if (existing != null) return false;
        repo.saveUser(new User(0, username, password));
        return true;
    }

    public boolean login(String username, String password) throws Exception {
        User u = repo.findByUsername(username);
        return u != null && u.getPassword().equals(password);
    }
}
