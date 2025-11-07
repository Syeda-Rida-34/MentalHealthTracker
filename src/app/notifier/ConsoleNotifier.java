package app.notifier;

import app.model.Entry;

import javax.swing.*;

public class ConsoleNotifier implements Notifier {
    @Override
    public void notifyLowMoodStreak(long userId, int days, Entry lastEntry) {
        JOptionPane.showMessageDialog(null,
                "⚠️ Alert: User " + userId + " has been sad for " + days + " days in a row!\n" +
                        "Last mood: " + lastEntry.getMood() + " | Note: " + lastEntry.getNote(),
                "Mood Alert", JOptionPane.WARNING_MESSAGE);
    }
}
