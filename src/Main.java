import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.FileBackedTaskManager;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        TaskManager manager = new InMemoryTaskManager();
        TaskManager managerSave = new FileBackedTaskManager();


        printAllTasks(managerSave);

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

