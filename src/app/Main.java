package app;

import app.appconfig.AppConfig;
import app.analysis.SimpleAverageStrategy;
import app.notifier.ConsoleNotifier;
import app.repo.FileEntryRepository;
import app.service.TrackerService;
import app.ui.GUIView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Load config
                AppConfig cfg = AppConfig.getInstance();
                if(args.length > 0) cfg.setStorageFile(args[0]);

                // Setup repository & service
                var repo = new FileEntryRepository(cfg.getStorageFile());
                var svc = new TrackerService(repo, new SimpleAverageStrategy());
                svc.registerNotifier(new ConsoleNotifier());

                // Launch GUI
                GUIView guiView = new GUIView(svc);
                guiView.showLoginUI(); // <-- STARTS WITH LOGIN SCREEN

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Startup Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
