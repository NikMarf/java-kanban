import practicum.model.Epic;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;
import practicum.model.StatusProgress;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();
        Task task1 = new Task("Я хз что писать писать", "ТЕСТ ТЕСТ", StatusProgress.IN_PROGRESS);
        Task task2 = new Task("Работай", "ПЛС", StatusProgress.NEW);
        Epic taskEpic1 = new Epic("Я хз что писать писать", "ТЕСТ ТЕСТ", StatusProgress.NEW);
        Epic taskEpic2 = new Epic("Я хз что писать", "ТЕСТ", StatusProgress.NEW);
        //System.out.println(taskEpic1.id);
        //System.out.println(task1.id);
        SubTask taskSub1 = new SubTask("Сделать срочно!", "Пропылесосить", StatusProgress.NEW, 3);
        SubTask taskSub2 = new SubTask("Сделать срочноBUBUB!", "Пропылесосить", StatusProgress.DONE, 3);
        SubTask taskSub3 = new SubTask("После пылесоса", "Протереть полки", StatusProgress.NEW, 4);
        SubTask taskSub4 = new SubTask("Новая задача", "Отладить все", StatusProgress.NEW, 4);
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
        manager.addSubTaskInEpic(taskSub3);
        manager.addSubTaskInEpic(taskSub4);
        manager.addSubTaskInEpic(subTest);
        manager.addSubTaskInEpic(subTestPR);
        manager.addSubTaskInEpic(subTest1);
        //System.out.println(manager.returnAllEpic());
        manager.removeByIsSubTask(9);
        manager.removeByIsSubTask(10);
        //System.out.println(manager.returnAllEpic());
        //System.out.println(manager.returnAllSubTask());
        System.out.println(manager.getByIdTask(1));
        System.out.println(manager.getByIdTask(2));
        System.out.println(manager.getByIdEpic(3));
        System.out.println(manager.getByIdEpic(4));
        System.out.println(manager.getByIdSubTaskTask(5));
        System.out.println(manager.getByIdSubTaskTask(5));
        System.out.println(manager.getByIdSubTaskTask(5));
        System.out.println(manager.getByIdSubTaskTask(5));
        System.out.println(manager.getByIdSubTaskTask(5));
        System.out.println(manager.getByIdSubTaskTask(7));
        System.out.println(manager.getByIdSubTaskTask(5));

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

