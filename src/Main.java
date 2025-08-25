import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();
    }
}
