package app.repo;

import app.model.Entry;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Base64;   // ✅ IMPORTANT: Base64 import added

public class FileEntryRepository implements EntryRepository {
    private final Path filePath;

    public FileEntryRepository(String fileName) {
        this.filePath = Paths.get(fileName);
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Unable to create storage file", ex);
        }
    }

    // ✅ FIXED: save method added back
    @Override
    public synchronized void save(Entry e) throws Exception {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {

            // Encode to Base64
            String enc = Base64.getEncoder().encodeToString(e.toString().getBytes());

            bw.write(enc);
            bw.newLine();
        }
    }

    // ✅ SAFE findAllByUser
    @Override
    public synchronized List<Entry> findAllByUser(long userId) throws Exception {
        List<String> lines = Files.readAllLines(filePath);
        List<Entry> list = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            try {
                // Safe Base64 decoding
                String decoded = new String(Base64.getDecoder().decode(line));

                Entry e = Entry.fromCSV(decoded);
                if (e != null && e.getUserId() == userId) {
                    list.add(e);
                }

            } catch (IllegalArgumentException ex) {
                System.out.println("Skipping invalid/corrupt entry: " + line);
            }
        }

        list.sort(Comparator.comparing(Entry::getDate));
        return list;
    }
}
