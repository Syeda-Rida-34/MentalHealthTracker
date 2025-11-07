package app.analysis;

import app.model.Entry;
import java.util.List;

public interface AnalysisStrategy {
    double analyzeAverage(List<Entry> entries);
}
