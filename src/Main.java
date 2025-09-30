
import practicum.model.Task;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;


public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();

    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.returnAllTask()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.returnAllEpic()) {
            System.out.println(epic);

            for (Task task : manager.outputByIdSubTask(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.returnAllSubTask()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}

