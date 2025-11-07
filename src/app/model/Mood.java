package app.model;

public enum Mood {
    VERY_BAD(-2), BAD(-1), NEUTRAL(0), GOOD(1), VERY_GOOD(2);
    private final int score;
    Mood(int score) { this.score = score; }
    public int getScore() { return score; }
}
