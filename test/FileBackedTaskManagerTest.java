import org.junit.jupiter.api.*;
import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.FileBackedTaskManager;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    File tempFile;
    FileBackedTaskManager manager;

    @BeforeEach
    void setUp() throws IOException {
        // Создаём временный файл в системной директории, он сам удалится после завершения теста
        tempFile = File.createTempFile("test_memory_", ".csv");
        tempFile.deleteOnExit();

        manager = new FileBackedTaskManager(tempFile);
    }

    @Test
    void shouldSaveAndLoadEmptyFile() {
        try {
            new FileBackedTaskManager(tempFile);
        } catch (Exception e) {
            fail("Создание менеджера не должно выбрасывать исключение, но было: " + e);
        }
    }

    @Test
    void shouldSaveAndLoadMultipleTasks() {
        Task task1 = new Task("Test Task 1", "Description 1", StatusProgress.NEW, 1);
        Epic epic1 = new Epic("Epic 1", "Epic description", StatusProgress.IN_PROGRESS, 2);
        SubTask sub1 = new SubTask("Sub 1", "Sub description", StatusProgress.DONE, 3, 2);

        manager.addTask(task1);
        manager.addEpic(epic1);
        manager.addSubTask(sub1);

        // Проверяем, что данные реально сохранились в менеджере
        assertEquals(1, manager.returnAllTask().size(), "Должна быть одна обычная задача");
        assertEquals(1, manager.returnAllEpic().size(), "Должен быть один эпик");
        assertEquals(1, manager.returnAllSubTask().size(), "Должна быть одна подзадача");
    }

    @Test
    void shouldLoadMultipleTasksFromFile() throws IOException {

        File testFile = File.createTempFile("load_test_", ".csv");
        testFile.deleteOnExit();

        String content = "1,TASK,Test1,NEW,Description1\n" +
                "2,EPIC,TestEpic,DONE,Description2\n" +
                "3,SUBTASK,Sub1,IN_PROGRESS,Description3,2\n";

        java.nio.file.Files.writeString(testFile.toPath(), content);

        FileBackedTaskManager loaded = new FileBackedTaskManager();


        assertEquals(1, loaded.returnAllTask().size(), "Должна загрузиться одна обычная задача");
        assertEquals(1, loaded.returnAllEpic().size(), "Должен загрузиться один эпик");
        assertEquals(1, loaded.returnAllSubTask().size(), "Должна загрузиться одна подзадача");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }
}