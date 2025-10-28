import org.junit.jupiter.api.Test;
import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.InMemoryTaskManager;
import practicum.service.TaskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}