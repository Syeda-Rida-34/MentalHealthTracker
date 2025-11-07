package app.repo;

import app.model.Entry;
import java.util.List;

public interface EntryRepository {
    void save(Entry e) throws Exception;
    List<Entry> findAllByUser(long userId) throws Exception;
}
