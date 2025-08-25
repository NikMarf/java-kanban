package practicum.service;

import practicum.model.Task;
import java.util.ArrayList;

public interface HistoryManager {

    static final int HISTORY_LIMIT = 10;

    void add(Task task);
    ArrayList<Task> getHistory();
}
