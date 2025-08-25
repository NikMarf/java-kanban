package practicum.service;

import practicum.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    public ArrayList<Task> listHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (listHistory.size() >= HISTORY_LIMIT) {
            listHistory.remove(0);
        }
        listHistory.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(listHistory);
    }
}
