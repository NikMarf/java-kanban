import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.model.Epic;

import org.junit.jupiter.api.Test;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class EpicTest {
    @Test
    void addNewEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);

        taskManager.addEpic(epic);

        final int taskId = epic.getId();

        final Task savedEpic = taskManager.getByIdEpic(taskId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final ArrayList<Epic> epics = taskManager.returnAllEpic();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void checkUpdateEpicWitchIdSubTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description",
                StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW);
        Epic updateEpic = new Epic("Test addNewEpic", "Test addNewEpic description",
                StatusProgress.DONE);

        taskManager.addEpic(epic);
        taskManager.addSubTaskInEpic(subTask);
        epic.setId(2);
        taskManager.updateEpic(updateEpic);

        assertNotEquals(StatusProgress.DONE, taskManager.getByIdSubTaskTask(2).getStatus(),
                "Записался SubTask по несуществующему id");
    }

    @Test
    void checkConflictGenIdAndManualId() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        Task newEpic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW,
                1);

        taskManager.addTask(epic);
        taskManager.addTask(newEpic);

        assertNotEquals(epic, newEpic, "Конфликт двух Epic с сгенерированным и ручным выставлением id");
    }

    @Test
    void checkImmutabilityWhenAddInMemoryTaskManager() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW,
                1);

        String expectedName = epic.getName();
        String expectedDescription = epic.getDescription();
        StatusProgress expectedStatus = epic.getStatus();
        int expectedId = epic.getId();

        taskManager.addEpic(epic);

        assertEquals(expectedName, taskManager.getByIdEpic(1).getName(),
                "Имя задачи изменилось");
        assertEquals(expectedDescription, taskManager.getByIdEpic(1).getDescription(),
                "Описание задачи изменилось");
        assertEquals(expectedStatus, taskManager.getByIdEpic(1).getStatus(),
                "Статус задачи изменился");
        assertEquals(expectedId, taskManager.getByIdEpic(1).getId(),
                "Id изменился");
    }

    @Test
    void checkHistoryManagerOnImmutabilityHistoryWhenUpdateEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.DONE);
        Epic updateEpic = new Epic("Test addNewEpic", "Test addNewEpic description",
                StatusProgress.DONE, 1);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.DONE, 1);

        taskManager.addEpic(epic);
        taskManager.addSubTaskInEpic(subTask);
        taskManager.getByIdEpic(1);
        taskManager.updateEpic(updateEpic);

        assertNotEquals(taskManager.returnAllEpic().get(0).getStatus(), taskManager.getHistory().get(0).getStatus(),
                "История вызовов изменилась");

    }
}
