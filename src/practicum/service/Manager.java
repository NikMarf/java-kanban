package practicum.service;

import java.io.IOException;

public class Manager {
    public TaskManager getDefault() throws IOException {
        TaskManager defaultManager = new InMemoryTaskManager();
        return defaultManager;
    }

    public HistoryManager getDefaultHistory() {
        HistoryManager defaultHistoryManager = new  InMemoryHistoryManager();
        return defaultHistoryManager;
    }
}
