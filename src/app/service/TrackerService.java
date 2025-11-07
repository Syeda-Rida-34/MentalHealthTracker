package app.service;

import app.model.*;
import app.repo.EntryRepository;
import app.notifier.Notifier;
import app.analysis.AnalysisStrategy;

import java.time.LocalDate;
import java.util.*;

public class TrackerService {
    private final EntryRepository repo;
    private final List<Notifier> notifiers = new ArrayList<>();
    private AnalysisStrategy strategy;

    public TrackerService(EntryRepository repo, AnalysisStrategy strategy) {
        this.repo = repo;
        this.strategy = strategy;
    }

    public void registerNotifier(Notifier n) { notifiers.add(n); }

    public void addEntry(long userId, LocalDate date, Mood mood, String note) throws Exception {
        Entry e = new Entry(userId, date, mood, note);
        repo.save(e);
        checkLowMoodStreak(userId);
    }

    public List<Entry> getEntries(long userId) throws Exception {
        return repo.findAllByUser(userId);
    }

    public double getAverageMood(long userId) throws Exception {
        List<Entry> entries = getEntries(userId);
        return strategy.analyzeAverage(entries);
    }

    private void checkLowMoodStreak(long userId) throws Exception {
        List<Entry> entries = getEntries(userId);
        if (entries.size() < 3) return;
        int streak = 0;
        for (int i = entries.size() - 1; i >= 0 && streak < 3; i--) {
            var mood = entries.get(i).getMood();
            if (mood == Mood.BAD || mood == Mood.VERY_BAD) streak++;
            else break;
        }
        if (streak >= 3) {
            Entry last = entries.get(entries.size() - 1);
            for (Notifier n : notifiers) n.notifyLowMoodStreak(userId, streak, last);
        }
    }
}
