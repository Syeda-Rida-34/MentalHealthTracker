package app.ui;

import app.model.*;
import app.service.TrackerService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIView {
    private final TrackerService service;
    private JFrame frame;
    private JProgressBar moodBar;
    private long currentUserId; // <-- Add this to store logged-in user ID

    private final String[] moodEmojis = {"😢", "☹️", "😐", "🙂", "😄"};

    // Quotes & Games data
    private final Map<Mood, String[]> moodQuotes = new HashMap<>();
    private final Map<Mood, String> moodGames = new HashMap<>();
    private final Map<Mood, String[]> moodExercises = new HashMap<>();

    public GUIView(TrackerService service) {
        this.service = service;
        initMoodData();
    }

    private void initMoodData() {
        moodQuotes.put(Mood.VERY_BAD, new String[]{
                "Even the darkest days end with light. Hold on — you’re stronger than you feel.",
                "One small step today can change your whole tomorrow. Don’t give up.",
                "Tough moments don’t last, but tough people do — and that’s you."
        });
        moodQuotes.put(Mood.BAD, new String[]{
                "It’s okay to feel low — this moment will pass.",
                "Breathe… better days are quietly on their way.",
                "You’re doing better than you think. Stay kind to yourself."
        });
        moodQuotes.put(Mood.NEUTRAL, new String[]{
                "Stay present — one clear step at a time.",
                "A calm mind creates the strongest results.",
                "Focus on what you can control, and let the rest be."
        });
        moodQuotes.put(Mood.GOOD, new String[]{
                "Use this good energy — today is a great day to make progress.",
                "Small consistent steps build big achievements.",
                "You’re in a good flow — keep going."
        });
        moodQuotes.put(Mood.VERY_GOOD, new String[]{
                "Your light is showing — keep shining, keep growing.",
                "Great things happen when your heart feels this alive.",
                "You’re unstoppable today — let this energy lead you forward."
        });

        moodGames.put(Mood.VERY_BAD, "Pop bubbles to lift your mood — watch colors and confetti burst!");
        moodGames.put(Mood.BAD, "Match the colors to feel calm and positive — simple and relaxing!");
        moodGames.put(Mood.NEUTRAL, "Flip cards and match pairs — boost your focus and concentration!");
        moodGames.put(Mood.GOOD, "Tap fast to score points — use your energy productively!");
        moodGames.put(Mood.VERY_GOOD, "Draw and express yourself — let your creativity shine!");

        moodExercises.put(Mood.VERY_BAD, new String[]{
                "Take 5 deep breaths and feel the tension leave.",
                "Stretch your arms and twist gently — let your body relax.",
                "Step outside for a short walk — breathe fresh air."
        });
        moodExercises.put(Mood.BAD, new String[]{
                "Write 3 things you’re thankful for today.",
                "Tense and relax each muscle slowly — feel calm.",
                "Smile for 30 seconds — your body will feel lighter."
        });
        moodExercises.put(Mood.NEUTRAL, new String[]{
                "Close eyes and breathe slowly for 5 minutes.",
                "Choose one small task and focus only on it.",
                "Visualize a calm place — sea, park, or forest."
        });
        moodExercises.put(Mood.GOOD, new String[]{
                "Write 1–2 tasks you can finish today.",
                "Work focused for 25 minutes, then take a short break.",
                "Organize your desk or room for clear energy."
        });
        moodExercises.put(Mood.VERY_GOOD, new String[]{
                "Draw, write, dance, or play music — express yourself.",
                "Set a small goal for today or this week.",
                "Compliment or motivate someone — spread positivity."
        });
    }


    // LOGIN SCREEN WITH ICON, NAME, LOGIN & SIGNUP BUTTONS (Fixed Icon Size)
public void showLoginUI() {
    frame = new JFrame("SDA Mental Health Tracker");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 450);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(Color.decode("#F5F7FA"));

    // Top Panel with Icon + Project Name
    JPanel topPanel = new JPanel();
    topPanel.setBackground(Color.decode("#F5F7FA"));
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

    // Icon (Scaled properly)
    ImageIcon originalIcon = new ImageIcon("path/to/your/icon.png");
    Image img = originalIcon.getImage();
    Image scaledImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH); // ✅ Scale icon
    JLabel iconLabel = new JLabel(new ImageIcon(scaledImg));
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    topPanel.add(Box.createRigidArea(new Dimension(0,40)));
    topPanel.add(iconLabel);

    // Project Name
    JLabel title = new JLabel("🧠 MOODLYST", SwingConstants.CENTER);
    title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 28));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    topPanel.add(Box.createRigidArea(new Dimension(0,15)));
    topPanel.add(title);
    topPanel.add(Box.createRigidArea(new Dimension(0,50)));

    frame.add(topPanel, BorderLayout.NORTH);

    // Buttons Panel
    JPanel btnPanel = new JPanel();
    btnPanel.setBackground(Color.decode("#F5F7FA"));
    btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));

    JButton loginBtn = new JButton("Login");
    loginBtn.setBackground(Color.decode("#4CAF50"));
    loginBtn.setForeground(Color.WHITE);
    loginBtn.setFocusPainted(false);
    loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    loginBtn.setMaximumSize(new Dimension(150, 40)); // ✅ Fixed button size

    JButton registerBtn = new JButton("Sign Up");
    registerBtn.setBackground(Color.decode("#FF9800"));
    registerBtn.setForeground(Color.WHITE);
    registerBtn.setFocusPainted(false);
    registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    registerBtn.setMaximumSize(new Dimension(150, 40)); // ✅ Fixed button size

    btnPanel.add(loginBtn);
    btnPanel.add(Box.createRigidArea(new Dimension(0,20)));
    btnPanel.add(registerBtn);

    frame.add(btnPanel, BorderLayout.CENTER);

    // Login & Signup actions
    loginBtn.addActionListener(e -> showLoginDialog());
    registerBtn.addActionListener(e -> showSignUpDialog());

    frame.setVisible(true);
}

// Login Dialog with fixed input box sizes
private void showLoginDialog() {
    JDialog loginDialog = new JDialog(frame, "Login", true);
    loginDialog.setSize(350, 250);
    loginDialog.setLocationRelativeTo(frame);
    loginDialog.setLayout(new BoxLayout(loginDialog.getContentPane(), BoxLayout.Y_AXIS));
    loginDialog.getContentPane().setBackground(Color.decode("#F5F7FA"));

    JTextField userField = new JTextField();
    userField.setMaximumSize(new Dimension(300, 35)); // ✅ fixed size
    userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    userField.setBorder(BorderFactory.createTitledBorder("User ID"));

    JPasswordField passField = new JPasswordField();
    passField.setMaximumSize(new Dimension(300, 35)); // ✅ fixed size
    passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    passField.setBorder(BorderFactory.createTitledBorder("Password"));

    JButton loginBtn = new JButton("Login");
    loginBtn.setBackground(Color.decode("#4CAF50"));
    loginBtn.setForeground(Color.WHITE);
    loginBtn.setFocusPainted(false);
    loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    loginBtn.setMaximumSize(new Dimension(150, 35));

    loginDialog.add(Box.createRigidArea(new Dimension(0,15)));
    loginDialog.add(userField);
    loginDialog.add(Box.createRigidArea(new Dimension(0,10)));
    loginDialog.add(passField);
    loginDialog.add(Box.createRigidArea(new Dimension(0,20)));
    loginDialog.add(loginBtn);

    loginBtn.addActionListener(ev -> {
        String userIdStr = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();
        if(userIdStr.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(loginDialog, "Please enter User ID and Password!");
            return;
        }

        boolean loginSuccess = false;

        try {
            java.io.File file = new java.io.File("users.txt");
            if(!file.exists()) file.createNewFile();

            java.util.Scanner sc = new java.util.Scanner(file);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 2) {
                    String savedId = parts[0].trim();
                    String savedPass = parts[1].trim();
                    if(savedId.equals(userIdStr) && savedPass.equals(password)) {
                        loginSuccess = true;
                        currentUserId = Long.parseLong(savedId);
                        break;
                    }
                }
            }
            sc.close();

            if(loginSuccess) {
                JOptionPane.showMessageDialog(loginDialog, "Login successful!");
                loginDialog.dispose();
                frame.dispose();
                showMainUI();
            } else {
                JOptionPane.showMessageDialog(loginDialog, "Invalid User ID or Password!");
            }

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(loginDialog, "Error reading users file: " + ex.getMessage());
        }
    });

    loginDialog.setVisible(true);
}

// Signup Dialog with fixed input box sizes
private void showSignUpDialog() {
    JDialog signUpDialog = new JDialog(frame, "Sign Up", true);
    signUpDialog.setSize(350, 250);
    signUpDialog.setLocationRelativeTo(frame);
    signUpDialog.setLayout(new BoxLayout(signUpDialog.getContentPane(), BoxLayout.Y_AXIS));
    signUpDialog.getContentPane().setBackground(Color.decode("#F5F7FA"));

    JTextField newUserField = new JTextField();
    newUserField.setMaximumSize(new Dimension(300, 35)); // ✅ fixed size
    newUserField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    newUserField.setBorder(BorderFactory.createTitledBorder("New User ID"));

    JPasswordField newPassField = new JPasswordField();
    newPassField.setMaximumSize(new Dimension(300, 35)); // ✅ fixed size
    newPassField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    newPassField.setBorder(BorderFactory.createTitledBorder("Password"));

    JButton createBtn = new JButton("Create Account");
    createBtn.setBackground(Color.decode("#4CAF50"));
    createBtn.setForeground(Color.WHITE);
    createBtn.setFocusPainted(false);
    createBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    createBtn.setMaximumSize(new Dimension(150, 35));

    signUpDialog.add(Box.createRigidArea(new Dimension(0,15)));
    signUpDialog.add(newUserField);
    signUpDialog.add(Box.createRigidArea(new Dimension(0,10)));
    signUpDialog.add(newPassField);
    signUpDialog.add(Box.createRigidArea(new Dimension(0,20)));
    signUpDialog.add(createBtn);

    createBtn.addActionListener(ev -> {
        String newUserId = newUserField.getText().trim();
        String newPassword = new String(newPassField.getPassword()).trim();
        if(newUserId.isBlank() || newPassword.isBlank()) {
            JOptionPane.showMessageDialog(signUpDialog, "Please enter User ID and Password!");
            return;
        }

        try {
            java.io.File file = new java.io.File("users.txt");
            java.io.FileWriter fw = new java.io.FileWriter(file, true);
            fw.write(newUserId + "," + newPassword + "\n");
            fw.close();
            JOptionPane.showMessageDialog(signUpDialog, "Account created successfully!");
            signUpDialog.dispose();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(signUpDialog, "Error saving user: " + ex.getMessage());
        }
    });

    signUpDialog.setVisible(true);
}


    // MAIN UI now uses currentUserId
    public void showMainUI() {
        frame = new JFrame("SDA Mental Health Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.decode("#F5F7FA"));

    // Top nav
    JPanel topNav = new JPanel(new BorderLayout());
    topNav.setBackground(Color.decode("#007BFF"));
    topNav.setPreferredSize(new Dimension(frame.getWidth(), 60));
    topNav.setBorder(new EmptyBorder(10, 15, 10, 15));

    JLabel logo = new JLabel("🧠 MOODLYST");
    logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
    logo.setForeground(Color.WHITE);
    JLabel profile = new JLabel("👤 RFM");
    profile.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
    profile.setForeground(Color.WHITE);

    topNav.add(logo, BorderLayout.WEST);
    topNav.add(profile, BorderLayout.EAST);
    frame.add(topNav, BorderLayout.NORTH);

    // Main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    mainPanel.setBackground(Color.decode("#F5F7FA"));

    JLabel greeting = new JLabel("Hi, how are you feeling today?");
    greeting.setFont(new Font("Segoe UI", Font.BOLD, 16));
    greeting.setForeground(Color.decode("#333333"));
    greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainPanel.add(greeting);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Mood buttons
    JPanel moodPanel = new JPanel(new GridLayout(1, 5, 10, 0));
    moodPanel.setBackground(Color.decode("#F5F7FA"));

    Mood[] moods = Mood.values();
    for (int i = 0; i < moods.length; i++) {
        final Mood selectedMood = moods[i];
        JButton btn = new JButton("<html><center>" + moodEmojis[i] + "</center></html>");
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setBackground(Color.decode("#E0E0E0"));
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(Color.decode("#D1EED9")); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(Color.decode("#E0E0E0")); }
        });

        btn.addActionListener(e -> {
            try {
                long userId = currentUserId;
                LocalDate date = LocalDate.now();
                String note = "";
                service.addEntry(userId, date, selectedMood, note);

                JOptionPane.showMessageDialog(frame,
                        "Selected Mood: " + selectedMood + " " + moodEmojis[selectedMood.ordinal()],
                        "Mood Selection",
                        JOptionPane.INFORMATION_MESSAGE);

                updateMoodProgress();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error saving mood: " + ex.getMessage());
            }
        });

        moodPanel.add(btn);
    }
    mainPanel.add(moodPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Cards Panel
    JPanel cardsPanel = new JPanel(new GridLayout(4, 1, 0, 15));
    cardsPanel.setBackground(Color.decode("#F5F7FA"));
    String[] cardNames = {"📝 Jeneral Entry", "🧘 Meditation", "💡 Tips", "📜 View History"};
    Color[] cardColors = {Color.decode("#4CAF50"), Color.decode("#FF9800"), Color.decode("#03A9F4"), Color.decode("#9C27B0")};

    for (int i = 0; i < cardNames.length; i++) {
        JButton card = new JButton(cardNames[i]);
        card.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        card.setForeground(Color.WHITE);
        card.setBackground(cardColors[i]);
        card.setFocusPainted(false);
        card.setOpaque(true);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        Color original = cardColors[i];

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { card.setBackground(original.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { card.setBackground(original); }
        });

        int idx = i;
        card.addActionListener(e -> {
            if (idx == 0) showAddEntryDialog();
            else if (idx == 1) showMeditationDialog();
            else if (idx == 2) showTipsGamesDialog();
            else if (idx == 3) showHistoryDialog();
        });

        cardsPanel.add(card);
    }
    mainPanel.add(cardsPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

 // Progress bar (elegant, normal size, label removed)
    JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    progressPanel.setBackground(Color.decode("#F5F7FA"));
    progressPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

    moodBar = new JProgressBar(0, 100);
    moodBar.setValue(0);
    moodBar.setStringPainted(true);
    moodBar.setForeground(Color.decode("#4CAF50"));
    moodBar.setBackground(Color.decode("#E0E0E0"));
    moodBar.setPreferredSize(new Dimension(300, 20)); // professional size

    progressPanel.add(moodBar);
    mainPanel.add(progressPanel);

    frame.add(mainPanel, BorderLayout.CENTER);
    frame.setVisible(true);

    // Update progress for currentUserId initially
    updateMoodProgress();
}

   // UPDATED updateMoodProgress method using currentUserId
private void updateMoodProgress() {
    try {
        List<Entry> entries = service.getEntries(currentUserId); // current user only
        double avgScore = 0;
        int count = 0;
        for (int i = Math.max(0, entries.size() - 7); i < entries.size(); i++) {
            avgScore += entries.get(i).getMood().getScore() + 2; // -2..2 => 0..4
            count++;
        }
        if (count > 0) avgScore = (avgScore / (count * 4)) * 100;
        moodBar.setValue((int) avgScore);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    // Add Entry Dialog
private void showAddEntryDialog() {
    JDialog dialog = new JDialog(frame, "📝 Add Entry", true);
    dialog.setSize(400, 350);
    dialog.setLocationRelativeTo(frame);
    dialog.setLayout(new GridBagLayout());
    dialog.getContentPane().setBackground(Color.decode("#F5F7FA"));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8,8,8,8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // ❌ User ID removed from input (label and field optional)
    // JLabel userLabel = new JLabel("User ID:");
    // userLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    // JTextField userField = new JTextField();

    JLabel dateLabel = new JLabel("Date (yyyy-mm-dd):");
    dateLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    JTextField dateField = new JTextField(LocalDate.now().toString());

    JLabel moodLabel = new JLabel("Mood:");
    moodLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    JComboBox<Mood> moodCombo = new JComboBox<>(Mood.values());

    JLabel noteLabel = new JLabel("Note:");
    noteLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    JTextArea noteArea = new JTextArea(3,20);
    noteArea.setLineWrap(true); noteArea.setWrapStyleWord(true);
    JScrollPane noteScroll = new JScrollPane(noteArea);

    JButton saveBtn = new JButton("💾 Save");
    saveBtn.setBackground(Color.decode("#4CAF50")); saveBtn.setForeground(Color.WHITE);
    JButton cancelBtn = new JButton("❌ Cancel");
    cancelBtn.setBackground(Color.decode("#E0E0E0")); cancelBtn.setForeground(Color.BLACK);

    gbc.gridx=0; gbc.gridy=0; dialog.add(dateLabel, gbc); gbc.gridx=1; dialog.add(dateField, gbc);
    gbc.gridx=0; gbc.gridy=1; dialog.add(moodLabel, gbc); gbc.gridx=1; dialog.add(moodCombo, gbc);
    gbc.gridx=0; gbc.gridy=2; dialog.add(noteLabel, gbc); gbc.gridx=1; dialog.add(noteScroll, gbc);
    gbc.gridx=0; gbc.gridy=3; dialog.add(saveBtn, gbc); gbc.gridx=1; dialog.add(cancelBtn, gbc);

    saveBtn.addActionListener(e -> {
        try {
            // ✅ User ID directly from currentUserId
            long userId = currentUserId;
            LocalDate date = LocalDate.parse(dateField.getText());
            Mood mood = (Mood) moodCombo.getSelectedItem();
            String note = noteArea.getText();
            service.addEntry(userId, date, mood, note);
            JOptionPane.showMessageDialog(dialog, "Entry saved successfully!");
            dialog.dispose();
        } catch(Exception ex) { 
            JOptionPane.showMessageDialog(dialog,"Error: "+ex.getMessage()); 
        }
    });

    cancelBtn.addActionListener(e -> dialog.dispose());
    dialog.setVisible(true);
}



    // history dialogue
  private void showHistoryDialog() {
    try {
        long userId = currentUserId;

        List<Entry> entries = service.getEntries(userId);
        if(entries.isEmpty()) { 
            JOptionPane.showMessageDialog(frame,"No entries found."); 
            return; 
        }

        JDialog dialog = new JDialog(frame,"📜 History", true);
        dialog.setSize(500,400); 
        dialog.setLocationRelativeTo(frame);

        String[] cols = {"Date","Mood","Note"};
        DefaultTableModel model = new DefaultTableModel(cols,0);
        for(Entry e : entries)
            model.addRow(new Object[]{e.getDate(), moodEmojis[e.getMood().ordinal()] + " " + e.getMood(), e.getNote()});

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        dialog.add(scroll);
        dialog.setVisible(true);
    } 
    catch(Exception e) { 
        JOptionPane.showMessageDialog(frame,"Error: "+e.getMessage()); 
    }
}





    // Meditation Dialog with Countdown (Scrollable Exercises)
private void showMeditationDialog() {
    try {
        long userId = currentUserId;

        List<Entry> entries = service.getEntries(userId);
        if(entries.isEmpty()) { 
            JOptionPane.showMessageDialog(frame, "No entries found for this User."); 
            return; 
        }

        // Get latest mood
        Mood latestMood = entries.get(entries.size() - 1).getMood();
        String[] exercises = moodExercises.get(latestMood);

        JDialog dialog = new JDialog(frame, "🧘 Meditation", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.decode("#E3F2FD"));

        // Header: Mood emoji + Mood name
        JLabel headerLabel = new JLabel(
            moodEmojis[latestMood.ordinal()] + " " + latestMood.name() + " Meditation", 
            SwingConstants.CENTER
        );
        headerLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        headerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dialog.add(headerLabel, BorderLayout.NORTH);

        // Exercises list (scrollable)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(String ex : exercises) listModel.addElement("• " + ex);  // Normal bullet
        JList<String> exerciseList = new JList<>(listModel);
        exerciseList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        exerciseList.setBackground(Color.decode("#E3F2FD"));
        JScrollPane scrollPane = new JScrollPane(exerciseList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Exercises"));
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Timer label
        JLabel timerLabel = new JLabel("02:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        timerLabel.setForeground(Color.decode("#333333"));

        // Start Timer button
        JButton startBtn = new JButton("Start Timer");
        startBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        startBtn.setBackground(Color.decode("#4CAF50")); 
        startBtn.setForeground(Color.WHITE);

        // South panel with some gap
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.setBackground(Color.decode("#E3F2FD"));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(Box.createRigidArea(new Dimension(0,10)));
        southPanel.add(startBtn);
        southPanel.add(Box.createRigidArea(new Dimension(0,15))); // gap between button & timer
        southPanel.add(timerLabel);
        southPanel.add(Box.createRigidArea(new Dimension(0,10)));
        dialog.add(southPanel, BorderLayout.SOUTH);

        startBtn.addActionListener(e -> {
            startBtn.setEnabled(false);
            new Timer(1000, new AbstractAction() {
                int remaining = 2*60;
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int m = remaining / 60;
                    int s = remaining % 60;
                    timerLabel.setText(String.format("%02d:%02d", m, s));
                    remaining--;
                    if(remaining < 0) {
                        ((Timer)evt.getSource()).stop();
                        timerLabel.setText("Time's up! Well done!");
                        startBtn.setEnabled(true);
                    }
                }
            }).start();
        });

        dialog.setVisible(true);

    } catch(Exception e) {
        JOptionPane.showMessageDialog(frame,"Error: "+e.getMessage());
    }
}

// tips, game, quotes
private void showTipsGamesDialog() {
    try {
        long userId = currentUserId;

        List<Entry> entries = service.getEntries(userId);
        if(entries.isEmpty()) { JOptionPane.showMessageDialog(frame, "No entries found."); return; }

        Mood latestMood = entries.get(entries.size() - 1).getMood();
        String[] quotes = moodQuotes.get(latestMood);

        // Define games for each mood
        Map<Mood, String[]> moodGamesNames = new HashMap<>();
        Map<String, String> gameLinks = new HashMap<>();

        moodGamesNames.put(Mood.VERY_BAD, new String[]{"Temple Run 2", "Moto X3M"});
        moodGamesNames.put(Mood.BAD, new String[]{"Blaze Drifter", "Forgotten Hill Fall"});
        moodGamesNames.put(Mood.NEUTRAL, new String[]{"Happy Glass", "Auto Ninja"});
        moodGamesNames.put(Mood.GOOD, new String[]{"Water Color Sort", "Detective Puzzle"});
        moodGamesNames.put(Mood.VERY_GOOD, new String[]{"Nuts and Bolts Puzzle", "TicTacToe"});

        gameLinks.put("Temple Run 2", "https://poki.com/en/g/temple-run-2-holi-festival");
        gameLinks.put("Moto X3M", "https://poki.com/en/g/moto-x3m");
        gameLinks.put("Blaze Drifter", "https://poki.com/en/g/blaze-drifter");
        gameLinks.put("Forgotten Hill Fall", "https://poki.com/en/g/forgotten-hill-fall-html5");
        gameLinks.put("Happy Glass", "https://poki.com/en/g/happy-glass");
        gameLinks.put("Auto Ninja", "https://poki.com/en/g/auto-ninja");
        gameLinks.put("Water Color Sort", "https://poki.com/en/g/water-color-sort");
        gameLinks.put("Detective Puzzle", "https://poki.com/en/g/detective-loupe-puzzle-2");
        gameLinks.put("Nuts and Bolts Puzzle", "https://poki.com/en/g/nuts-and-bolts-screwing-puzzle");
        gameLinks.put("TicTacToe", "https://poki.com/en/g/tictactoe");

        JDialog dialog = new JDialog(frame, "💡 " + latestMood.name() + " Tips & Games", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.decode("#F5F7FA"));

        JLabel moodLabel = new JLabel("Mood: " + moodEmojis[latestMood.ordinal()] + " " + latestMood.name(), SwingConstants.CENTER);
        moodLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        moodLabel.setForeground(Color.decode("#333333"));
        moodLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        dialog.add(moodLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#F5F7FA"));

        // ✅ Quotes Button
        JButton quotesBtn = new JButton("💬 Quotes");
        quotesBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        quotesBtn.setBackground(Color.decode("#03A9F4"));
        quotesBtn.setForeground(Color.WHITE);
        quotesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        quotesBtn.setPreferredSize(new Dimension(220, 45));
        quotesBtn.setMaximumSize(new Dimension(220, 45));
        quotesBtn.setFocusPainted(false);
        quotesBtn.setOpaque(true);
        quotesBtn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        quotesBtn.addActionListener(ae -> {
            StringBuilder sb = new StringBuilder("💡 Your Quotes:\n\n");
            for(String q : quotes) sb.append("• ").append(q).append("\n");
            JOptionPane.showMessageDialog(dialog, sb.toString(), "Quotes", JOptionPane.INFORMATION_MESSAGE);
        });

        mainPanel.add(quotesBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ✅ Main Games Button
        JButton mainGameBtn = new JButton("🎮 Games");
        mainGameBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        mainGameBtn.setBackground(Color.decode("#FF9800"));
        mainGameBtn.setForeground(Color.WHITE);
        mainGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainGameBtn.setPreferredSize(new Dimension(220, 45));
        mainGameBtn.setMaximumSize(new Dimension(220, 45));
        mainGameBtn.setFocusPainted(false);
        mainGameBtn.setOpaque(true);
        mainGameBtn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // MINI GAME MENU
mainGameBtn.addActionListener(ae -> {
    JDialog gameMenu = new JDialog(dialog, "Select a Game", true);
    gameMenu.setSize(320, 200);
    gameMenu.setLocationRelativeTo(dialog);
    gameMenu.getContentPane().setBackground(Color.decode("#F5F7FA"));

    JPanel gamePanel = new JPanel();
    gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
    gamePanel.setBackground(Color.decode("#F5F7FA"));
    gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] moodGames = moodGamesNames.get(latestMood);

    // GAME BUTTON 1
    String g1Name = moodGames[0];
    switch(g1Name) {
        case "Temple Run 2": g1Name = "🏃🔥 " + g1Name; break;
        case "Moto X3M": g1Name = "🏍️ " + g1Name; break;
        case "Blaze Drifter": g1Name = "🚗💨 " + g1Name; break;
        case "Forgotten Hill Fall": g1Name = "🏚️😱 " + g1Name; break;
        case "Happy Glass": g1Name = "🥛🙂 " + g1Name; break;
        case "Auto Ninja": g1Name = "🤖🥷 " + g1Name; break;
        case "Water Color Sort": g1Name = "🎨💧 " + g1Name; break;
        case "Detective Puzzle": g1Name = "🕵️‍♂️🔍 " + g1Name; break;
        case "Nuts and Bolts Puzzle": g1Name = "🔩🛠️ " + g1Name; break;
        case "TicTacToe": g1Name = "❌⭕ " + g1Name; break;
    }

    JButton g1 = new JButton(g1Name);
    g1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
    g1.setBackground(Color.decode("#4CAF50")); 
    g1.setForeground(Color.WHITE);
    g1.setFocusPainted(false);
    g1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    g1.setOpaque(true);
    g1.setAlignmentX(Component.CENTER_ALIGNMENT);
    g1.setMaximumSize(new Dimension(220, 45));
    g1.setPreferredSize(new Dimension(220, 45));

    g1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) { g1.setBackground(new Color(56, 142, 60)); }
        public void mouseExited(java.awt.event.MouseEvent evt) { g1.setBackground(Color.decode("#4CAF50")); }
    });

    g1.addActionListener(ev -> {
        gameMenu.dispose();
        try { Desktop.getDesktop().browse(new java.net.URI(gameLinks.get(moodGames[0]))); }
        catch(Exception ex) { JOptionPane.showMessageDialog(dialog, ex.getMessage()); }
    });

    // GAME BUTTON 2
    String g2Name = moodGames[1];
    switch(g2Name) {
        case "Temple Run 2": g2Name = "🏃🔥 " + g2Name; break;
        case "Moto X3M": g2Name = "🏍️ " + g2Name; break;
        case "Blaze Drifter": g2Name = "🚗💨 " + g2Name; break;
        case "Forgotten Hill Fall": g2Name = "🏚️😱 " + g2Name; break;
        case "Happy Glass": g2Name = "🥛🙂 " + g2Name; break;
        case "Auto Ninja": g2Name = "🤖🥷 " + g2Name; break;
        case "Water Color Sort": g2Name = "🎨💧 " + g2Name; break;
        case "Detective Puzzle": g2Name = "🕵️‍♂️🔍 " + g2Name; break;
        case "Nuts and Bolts Puzzle": g2Name = "🔩🛠️ " + g2Name; break;
        case "TicTacToe": g2Name = "❌⭕ " + g2Name; break;
    }

    JButton g2 = new JButton(g2Name);
    g2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
    g2.setBackground(Color.decode("#4CAF50"));
    g2.setForeground(Color.WHITE);
    g2.setFocusPainted(false);
    g2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    g2.setOpaque(true);
    g2.setAlignmentX(Component.CENTER_ALIGNMENT);
    g2.setMaximumSize(new Dimension(220, 45));
    g2.setPreferredSize(new Dimension(220, 45));

    g2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) { g2.setBackground(new Color(56, 142, 60)); }
        public void mouseExited(java.awt.event.MouseEvent evt) { g2.setBackground(Color.decode("#4CAF50")); }
    });

    g2.addActionListener(ev -> {
        gameMenu.dispose();
        try { Desktop.getDesktop().browse(new java.net.URI(gameLinks.get(moodGames[1]))); }
        catch(Exception ex) { JOptionPane.showMessageDialog(dialog, ex.getMessage()); }
    });

    gamePanel.add(g1);
    gamePanel.add(Box.createRigidArea(new Dimension(0, 15)));
    gamePanel.add(g2);

    gameMenu.add(gamePanel);
    gameMenu.setVisible(true);
});

        mainPanel.add(mainGameBtn);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.setVisible(true);

    } catch(Exception e) {
        JOptionPane.showMessageDialog(frame,"Error: "+e.getMessage());
    }
}

}