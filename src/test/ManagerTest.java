import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.model.Epic;

import org.junit.jupiter.api.Test;
import practicum.service.InMemoryTaskManager;
import practicum.service.Manager;
import practicum.service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    @Test
    void checkReturnManagerReady() {
        Manager manager = new Manager();
        TaskManager taskManager = manager.getDefault();
        assertNotNull(taskManager, "Менеджер задач не должен быть null");
    }

    @Test
    void checkInMemoryTaskManagerFunctionality() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", StatusProgress.NEW);
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW);

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubTaskInEpic(subTask);

        Task taskCheck = taskManager.getByIdTask(1);
        Task epicCheck = taskManager.getByIdEpic(2);
        Task subTaskCheck = taskManager.getByIdSubTaskTask(3);

        assertEquals(taskManager.returnAllTask().get(0), taskCheck,
                "Добавленный Task не существует в InMemoryTaskManager");
        assertEquals(taskManager.returnAllEpic().get(0), epicCheck,
                "Добавленный Task не существует в InMemoryTaskManager");
        assertEquals(taskManager.returnAllSubTask().get(0), subTaskCheck,
                "Добавленный Task не существует в InMemoryTaskManager");
    }
}
