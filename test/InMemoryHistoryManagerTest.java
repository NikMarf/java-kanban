import org.junit.jupiter.api.Test;
import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.InMemoryHistoryManager;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    void checkImmutabilityAtCallTaskLocatedTail() {
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
        taskManager.getByIdSubTaskTask(3);


        assertEquals(taskManager.getHistory().get(0), taskCheck,
                "Добавленный Task сместился в истории некорректно");
        assertEquals(taskManager.getHistory().get(1), epicCheck,
                "Добавленный Task сместился в истории некорректно");
        assertEquals(taskManager.getHistory().get(2), subTaskCheck,
                "Добавленный Task сместился в истории некорректно");
    }

    @Test
    void checkCorrectHistoryAtCallTaskLocatedTailAndGetAnotherTask() {
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
        taskManager.getByIdSubTaskTask(3);
        taskManager.getByIdTask(1);

        assertEquals(taskManager.getHistory().get(0), epicCheck,
                "Добавленный Task не сместился в истории корректно");
        assertEquals(taskManager.getHistory().get(1), subTaskCheck,
                "Добавленный Task не сместился в истории корректно");
        assertEquals(taskManager.getHistory().get(2), taskCheck,
                "Добавленный Task не сместился в истории корректно");
    }

    @Test
    void shouldReturnEmptyHistoryInitially() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        assertTrue(historyManager.getHistory().isEmpty(),
                "История должна быть пустой при создании менеджера");
    }

    @Test
    void shouldAddAndRetrieveTaskFromHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Task1", "Description Task1", StatusProgress.NEW);
        task.setId(1);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }

    @Test
    void shouldNotDuplicateTaskInHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Task1", "Description Task1", StatusProgress.NEW);
        task.setId(1);

        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(),
                "История не должна содержать дубликатов");
    }

    @Test
    void shouldRemoveTaskFromBeginningMiddleAndEnd() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task t1 = new Task("Task1", "Description Task1", StatusProgress.NEW);
        t1.setId(1);
        Task t2 = new Task("Task2", "Description Task2", StatusProgress.NEW);
        t2.setId(2);
        Task t3 = new Task("Task3", "Description Task3", StatusProgress.NEW);
        t3.setId(3);

        historyManager.add(t1);
        historyManager.add(t2);
        historyManager.add(t3);

        historyManager.remove(1); // начало
        assertEquals(List.of(t2, t3), historyManager.getHistory());

        historyManager.remove(2); // середина
        assertEquals(List.of(t3), historyManager.getHistory());

        historyManager.remove(3); // конец
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void shouldNotFailWhenRemovingNonExistentTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Task1", "Description Task1", StatusProgress.NEW);
        task.setId(1);
        historyManager.add(task);

        assertDoesNotThrow(() -> historyManager.remove(5),
                "Удаление несуществующей задачи не должно вызывать исключение");
    }

    @Test
    void shouldNotThrowWhenAddingNullTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        assertDoesNotThrow(() -> historyManager.add(null),
                "Добавление null-задачи не должно вызывать исключение");
    }

    @Test
    void shouldThrowNPEIfTaskWithoutId() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("NoID", "Task", StatusProgress.NEW);
        assertThrows(NullPointerException.class, () -> historyManager.add(task),
                "Добавление задачи без id должно вызывать исключение");
    }
}