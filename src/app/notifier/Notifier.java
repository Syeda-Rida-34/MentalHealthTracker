package app.notifier;

import app.model.Entry;

public interface Notifier {
    void notifyLowMoodStreak(long userId, int days, Entry lastEntry);
}
