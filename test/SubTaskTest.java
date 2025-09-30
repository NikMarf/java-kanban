import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.model.Epic;

import org.junit.jupiter.api.Test;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SubTaskTest {
    @Test
    void addNewEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW);

        taskManager.addSubTask(subTask);

        final int subTaskId = subTask.getId();

        final Task savedSubTask = taskManager.getByIdSubTaskTask(subTaskId);

        assertNotNull(savedSubTask, "Подзадача не найдена.");
        assertEquals(subTask, savedSubTask, "Подзадачи не совпадают.");

        final ArrayList<SubTask> subTasks = taskManager.returnAllSubTask();

        assertNotNull(subTasks, "Задачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void checkEqualsIdWhenAddSubTaskInManager() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW, 1);

        taskManager.addEpic(epic);
        subTask.setId(1);
        taskManager.addSubTaskInEpic(subTask);

        final int taskIdEpic = epic.getId();
        final int taskIdSubTask = subTask.getId();

        assertNotEquals(taskIdEpic, taskIdSubTask, "У epic и subTask одинаковые id");
    }

    @Test
    void checkUpdateSubTaskWithNonExistentId() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW);
        SubTask updatesubTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.DONE, 2);

        taskManager.addEpic(epic);
        taskManager.addSubTaskInEpic(subTask);
        taskManager.updateSubTask(updatesubTask, 3);

        assertNotEquals(StatusProgress.DONE, taskManager.getByIdSubTaskTask(2).getStatus(),
                "Записался новый SubTask по несуществующему id");
        assertNull(taskManager.getByIdSubTaskTask(3), "Записался SubTask по несуществующему id");
    }

    @Test
    void checkConflictGenIdAndManualId() {
        TaskManager taskManager = new InMemoryTaskManager();
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW);
        SubTask newSubTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW, 1);

        taskManager.addSubTask(subTask);
        taskManager.addSubTask(newSubTask);

        assertNotEquals(subTask, newSubTask,
                "Конфликт двух SubTask с сгенерированным и ручным выставлением id");
    }

    @Test
    void checkImmutabilityWhenAddInMemoryTaskManager() {
        TaskManager taskManager = new InMemoryTaskManager();
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW,1, 0);

        String expectedName = subTask.getName();
        String expectedDescription = subTask.getDescription();
        StatusProgress expectedStatus = subTask.getStatus();
        int expectedId = subTask.getId();
        int expectedIdParentTask = subTask.getIdParentTask();

        taskManager.addSubTask(subTask);

        assertEquals(expectedName, taskManager.getByIdSubTaskTask(1).getName(),
                "Имя задачи изменилось");
        assertEquals(expectedDescription, taskManager.getByIdSubTaskTask(1).getDescription(),
                "Описание задачи изменилось");
        assertEquals(expectedStatus, taskManager.getByIdSubTaskTask(1).getStatus(),
                "Статус задачи изменился");
        assertEquals(expectedId, taskManager.getByIdSubTaskTask(1).getId(),
                "Id изменился");
        assertEquals(expectedIdParentTask, taskManager.getByIdSubTaskTask(1).getIdParentTask(),
                "IdParentTask изменился");
    }

    @Test
    void checkHistoryManagerOnImmutabilityHistoryWhenUpdateSubTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.NEW);
        SubTask updateSubTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description",
                StatusProgress.DONE, 1, 0);

        taskManager.addSubTask(subTask);
        taskManager.getByIdSubTaskTask(1);
        taskManager.updateSubTask(updateSubTask);

        assertNotEquals(taskManager.returnAllSubTask().get(0).getStatus(), taskManager.getHistory().get(0).getStatus(),
                "История вызовов изменилась");

    }
}
