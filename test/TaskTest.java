import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.model.Epic;

import org.junit.jupiter.api.Test;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class TaskTest {

    @Test
    void addNewTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", StatusProgress.NEW);

        taskManager.addTask(task);

        final int taskId = task.getId();

        final Task savedTask = taskManager.getByIdTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = taskManager.returnAllTask();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addTaskHeirsAndCompareThem() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewEpic description",
                StatusProgress.NEW);

        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);

        subTask.setId(1);

        final int taskIdEpic = epic.getId();
        final int taskIdSubTask = subTask.getId();

        final Task savedEpic = taskManager.getByIdEpic(taskIdEpic);
        final Task savedSubTask = taskManager.getByIdEpic(taskIdSubTask);

        assertNotNull(epic, "Задача не найдена.");
        assertNotNull(subTask, "Задача не найдена.");
        assertEquals(epic, subTask, "Задачи не совпадают.");
    }

    @Test
    void checkConflictGenIdAndManualId() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description",
                StatusProgress.NEW);
        Task newTask = new Task("Test addNewTask", "Test addNewTask description",
                StatusProgress.NEW, 1);

        taskManager.addTask(task);
        taskManager.addTask(newTask);

        assertNotEquals(task, newTask, "Конфликт двух Task с сгенерированным и ручным выставлением id");
    }

    @Test
    void checkImmutabilityWhenAddInMemoryTaskManager() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", StatusProgress.NEW,
                1);

        String expectedName = task.getName();
        String expectedDescription = task.getDescription();
        StatusProgress expectedStatus = task.getStatus();
        int expectedId = task.getId();

        taskManager.addTask(task);

        assertEquals(expectedName, taskManager.getByIdTask(1).getName(), "Имя задачи изменилось");
        assertEquals(expectedDescription, taskManager.getByIdTask(1).getDescription(),
                "Описание задачи изменилось");
        assertEquals(expectedStatus, taskManager.getByIdTask(1).getStatus(),
                "Статус задачи изменился");
        assertEquals(expectedId, taskManager.getByIdTask(1).getId(), "Id изменился");
    }

    @Test
    void checkHistoryManagerOnImmutabilityHistoryWhenUpdateTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", StatusProgress.NEW);
        Task updateTask = new Task("Test addNewTask", "Test addNewTask description",
                StatusProgress.DONE, 1);

        taskManager.addTask(task);
        taskManager.getByIdTask(1);
        taskManager.updateTask(updateTask);

        assertNotEquals(taskManager.returnAllTask().get(0).getStatus(), taskManager.getHistory().get(0).getStatus(),
                "История вызовов изменилась");

    }


}