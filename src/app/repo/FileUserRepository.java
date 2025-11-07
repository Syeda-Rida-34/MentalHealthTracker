package app.repo;

import app.model.User;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private final Path filePath = Paths.get("users.csv");

    public FileUserRepository() {
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public synchronized void saveUser(User user) throws Exception {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            bw.write(user.getUsername() + "," + user.getPassword());
            bw.newLine();
        }
    }

    @Override
    public synchronized User findByUsername(String username) throws Exception {
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] p = line.split(",");
            if (p[0].equals(username)) {
                return new User(0, p[0], p[1]);
            }
        }
        return null;
    }
}
