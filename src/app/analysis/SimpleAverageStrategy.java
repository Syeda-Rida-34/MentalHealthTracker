package app.analysis;

import app.model.Entry;
import java.util.List;

public class SimpleAverageStrategy implements AnalysisStrategy {
    @Override
    public double analyzeAverage(List<Entry> entries) {
        if (entries == null || entries.isEmpty()) return 0.0;
        double sum = 0;
        for (var e : entries) sum += e.getMood().getScore();
        return sum / entries.size();
    }
}
