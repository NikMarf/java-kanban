import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.HistoryManager;
import practicum.service.InMemoryTaskManager;
import practicum.service.Manager;
import practicum.service.TaskManager;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest {

    private TaskManager manager;
    private Epic epic;
    private SubTask sub1;
    private SubTask sub2;

    @Test
    void checkReturnManagerReady() throws IOException {
        Manager manager = new Manager();
        TaskManager taskManager = manager.getDefault();
        assertNotNull(taskManager, "Менеджер задач не должен быть null");
    }

    @Test
    void checkReturnManagerHistoryReady() {
        Manager manager = new Manager();
        HistoryManager historyManager = manager.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер задач не должен быть null");
    }

    @Test
    void shouldDetectTimeOverlapBetweenTasks() {
        InMemoryTaskManager managerTaskManager = new InMemoryTaskManager();
        Task t1 = new Task("Task 1", "Desc", StatusProgress.NEW, 60,
                LocalDateTime.of(2025, 10, 10, 10, 0));
        Task t2 = new Task("Task 2", "Desc", StatusProgress.NEW,30,
                LocalDateTime.of(2025, 10, 10, 10, 30));

        managerTaskManager.addTask(t1);
        boolean hasCollision = managerTaskManager.isTimeCollisions(t1, t2);

        assertTrue(hasCollision, "Пересечение задач по времени должно быть обнаружено");
    }

    @Test
    void shouldNotDetectOverlapWhenTasksSeparatedInTime() {
        InMemoryTaskManager managerTaskManager = new InMemoryTaskManager();
        Task t1 = new Task("Task 1", "Desc", StatusProgress.NEW, 60,
                LocalDateTime.of(2025, 10, 10, 10, 0));
        Task t2 = new Task("Task 2", "Desc", StatusProgress.NEW, 30,
                LocalDateTime.of(2025, 10, 10, 11, 0));

        managerTaskManager.addTask(t1);

        boolean hasCollision = managerTaskManager.isTimeCollisions(t1, t2);

        assertFalse(hasCollision, "Задачи без пересечения не должны конфликтовать");
    }

    @Test
    void shouldIgnoreTasksWithoutStartTime() {
        InMemoryTaskManager managerTaskManager = new InMemoryTaskManager();
        Task t1 = new Task("Task 1", "Desc", StatusProgress.NEW);
        Task t2 = new Task("Task 2", "Desc", StatusProgress.NEW);

        boolean hasCollision = managerTaskManager.isTimeCollisions(t1, t2);

        assertFalse(hasCollision, "Задачи без времени не должны конфликтовать");
    }


    @BeforeEach
    void setTasks() {
        manager = new InMemoryTaskManager();
        epic = new Epic("Epic 1", "Test Epic", StatusProgress.NEW);
        manager.addEpic(epic);

        sub1 = new SubTask("Sub 1", "Subtask 1", StatusProgress.NEW, epic.getId());
        sub2 = new SubTask("Sub 2", "Subtask 2", StatusProgress.NEW, epic.getId());

        manager.addSubTaskInEpic(sub1);
        manager.addSubTaskInEpic(sub2);
    }

    @Test
    void shouldSetEpicStatusNewIfAllSubtasksNew() {
        assertEquals(StatusProgress.NEW, manager.getByIdEpic(epic.getId()).getStatus());
    }

    @Test
    void shouldSetEpicStatusDoneIfAllSubtasksDone() {
        sub1.setStatus(StatusProgress.DONE);
        sub2.setStatus(StatusProgress.DONE);
        manager.updateSubTask(sub1, sub1.getId());
        manager.updateSubTask(sub2, sub2.getId());
        assertEquals(StatusProgress.DONE, manager.getByIdEpic(epic.getId()).getStatus());
    }

    @Test
    void shouldSetEpicStatusInProgressIfMixedStatuses() {
        sub1.setStatus(StatusProgress.NEW);
        sub2.setStatus(StatusProgress.DONE);
        manager.updateSubTask(sub1, sub1.getId());
        manager.updateSubTask(sub2, sub2.getId());
        assertEquals(StatusProgress.IN_PROGRESS, manager.getByIdEpic(epic.getId()).getStatus());
    }

    @Test
    void shouldSetEpicStatusInProgressIfAllInProgress() {
        sub1.setStatus(StatusProgress.IN_PROGRESS);
        sub2.setStatus(StatusProgress.IN_PROGRESS);
        manager.updateSubTask(sub1, sub1.getId());
        manager.updateSubTask(sub2, sub2.getId());
        assertEquals(StatusProgress.IN_PROGRESS, manager.getByIdEpic(epic.getId()).getStatus());
    }



    abstract void checkInMemoryTaskManagerFunctionality();
}
