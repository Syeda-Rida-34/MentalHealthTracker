package app.repo;

import app.model.User;

public interface UserRepository {
    void saveUser(User user) throws Exception;
    User findByUsername(String username) throws Exception;
}
