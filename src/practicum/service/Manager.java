package practicum.service;

public class Manager {
    public TaskManager getDefault() {
        TaskManager defaultManager = new InMemoryTaskManager();
        return defaultManager;
    }
}
