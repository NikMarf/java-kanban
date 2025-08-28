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
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewEpic description", StatusProgress.NEW);

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
    void checkEqualsIdWhenAddSubTaskInManager() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", StatusProgress.NEW);

        taskManager.addEpic(epic);
        subTask.setId(1);
        taskManager.addSubTaskInEpic(subTask);

        final int taskIdEpic = epic.getId();
        final int taskIdSubTask = subTask.getId();

        assertNotEquals(taskIdEpic, taskIdSubTask, "У epic и subTask одинаковые id");
    }

    @Test
    void checkUpdateEpicWitchIdSubTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", StatusProgress.NEW);
        SubTask updatesubTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", StatusProgress.DONE, 2);

        taskManager.addEpic(epic);
        taskManager.addSubTaskInEpic(subTask);
        taskManager.updateSubTask(updatesubTask, 3);

        assertNotEquals(StatusProgress.DONE, taskManager.getByIdSubTaskTask(2).getStatus(), "Записался SubTask по несуществующему id");
    }

    @Test
    void checkUpdateSubTaskWithNonExistentId() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", StatusProgress.NEW);
        SubTask subTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", StatusProgress.NEW);
        SubTask updatesubTask = new SubTask("Test addNewSubTask", "Test addNewSubTask description", StatusProgress.DONE, 2);

        taskManager.addEpic(epic);
        taskManager.addSubTaskInEpic(subTask);
        taskManager.updateSubTask(updatesubTask, 3);

        assertNotEquals(StatusProgress.DONE, taskManager.getByIdSubTaskTask(2).getStatus(), "Записался SubTask по несуществующему id");
    }
}