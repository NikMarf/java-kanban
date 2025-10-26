import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.FileBackedTaskManager;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

import java.io.IOException;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        //TaskManager managerSave = new FileBackedTaskManager();
        //printAllTasks(managerSave);
        Task task1 = new Task("Я хз что писать писать", "ТЕСТ ТЕСТ", StatusProgress.IN_PROGRESS);
        Task task2 = new Task("Работай", "ПЛС", StatusProgress.NEW);
        Epic taskEpic1 = new Epic("Я хз что писать писать", "ТЕСТ ТЕСТ", StatusProgress.NEW);
        Epic taskEpic2 = new Epic("Я хз что писать", "ТЕСТ", StatusProgress.NEW);
        //System.out.println(taskEpic1.id);
        //System.out.println(task1.id);
        SubTask taskSub1 = new SubTask("Сделать срочно!", "Пропылесосить", StatusProgress.NEW, 3, 300, LocalDateTime.of(2010, 10, 10, 10, 0));
        SubTask taskSub2 = new SubTask("Сделать срочноBUBUB!", "Пропылесосить", StatusProgress.DONE, 3, 500, LocalDateTime.of(2010, 10, 2, 15, 0));
        //SubTask taskSub3 = new SubTask("После пылесоса", "Протереть полки", StatusProgress.NEW, 3, 60, LocalDateTime.of(2009, 11, 2, 15, 0));
        //SubTask taskSub4 = new SubTask("Новая задача", "Отладить все", StatusProgress.NEW, 3, 80, LocalDateTime.of(2009, 11, 2, 15, 30));
        SubTask subTest = new SubTask("ХХХХХ", "ЫЫЫЫЫЫЫ", StatusProgress.IN_PROGRESS, 4);
        SubTask subTestPR = new SubTask("ХХХХХ", "ЫЫЫЫЫЫЫ", StatusProgress.IN_PROGRESS, 4);
        SubTask subTest1 = new SubTask("dddddd", "ЫЫЫooЫЫ", StatusProgress.DONE, 3);

        manager.addTask(task1);
        manager.addTask(task2);
        //System.out.println(manager.returnAllTask());
        manager.addEpic(taskEpic1);
        manager.addEpic(taskEpic2);
        //System.out.println(manager.returnAllEpic());
        manager.addSubTaskInEpic(taskSub1);
        manager.addSubTaskInEpic(taskSub2);
        //manager.addSubTaskInEpic(taskSub3);
       //manager.addSubTaskInEpic(taskSub4);
        //manager.addSubTaskInEpic(taskSub4);
        manager.addSubTaskInEpic(subTest);
        manager.addSubTaskInEpic(subTestPR);
        manager.addSubTaskInEpic(subTest1);
        System.out.println(manager.getPrioritizedTasks());
        printAllTasks(manager);

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

