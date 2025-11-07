package app.appconfig;

public class AppConfig {
    private static AppConfig instance;
    private String storageFile = "entries.csv";

    private AppConfig() {}

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public String getStorageFile() { return storageFile; }
    public void setStorageFile(String file) { this.storageFile = file; }
}
