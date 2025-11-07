package app.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class Entry {
    private static final AtomicLong ID_GEN = new AtomicLong(1);
    private final long id;
    private final long userId;
    private final LocalDate date;
    private final Mood mood;
    private final String note;

    // Constructor with auto-generated ID
    public Entry(long userId, LocalDate date, Mood mood, String note) {
        this.id = ID_GEN.getAndIncrement();
        this.userId = userId;
        this.date = date;
        this.mood = mood;
        this.note = note == null ? "" : note;
    }

    // Constructor for CSV import (with ID)
    public Entry(long id, long userId, LocalDate date, Mood mood, String note) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.mood = mood;
        this.note = note == null ? "" : note;
    }

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public LocalDate getDate() { return date; }
    public Mood getMood() { return mood; }
    public String getNote() { return note; }

    @Override
    public String toString() {
        return id + "," + userId + "," + date + "," + mood + "," + note.replace(",", " ");
    }

    public static Entry fromCSV(String line) {
        try {
            String[] parts = line.split(",", 5);
            if (parts.length < 5) return null;
            long id = Long.parseLong(parts[0]);
            long userId = Long.parseLong(parts[1]);
            LocalDate date = LocalDate.parse(parts[2]);
            Mood mood = Mood.valueOf(parts[3]);
            String note = parts[4];
            return new Entry(id, userId, date, mood, note);
        } catch (Exception e) {
            return null;
        }
    }
}
