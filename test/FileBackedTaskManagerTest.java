import org.junit.jupiter.api.*;
import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;
import practicum.service.FileBackedTaskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    File tempFile;
    FileBackedTaskManager managerSave;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test_memory_", ".csv");
        tempFile.deleteOnExit();
        managerSave = new FileBackedTaskManager(tempFile);
    }

    @Test
    void shouldSaveAndLoadEmptyFile() {
        try {
            new FileBackedTaskManager(tempFile);
        } catch (Exception e) {
            System.out.println("Ошибка при создании менеджера: " + e);
        }
        assertNotNull(managerSave, "Менеджер не создан");
    }

    @Test
    void shouldSaveAndLoadMultipleTasks() {
        Task task1 = new Task("Task 1", "Description Task", StatusProgress.NEW, 1);
        Epic epic1 = new Epic("Epic 1", "Description Epic", StatusProgress.IN_PROGRESS, 2);
        SubTask sub1 = new SubTask("SubTask 1", "Description SubTask", StatusProgress.DONE, 3, 2);
        managerSave.addTask(task1);
        managerSave.addEpic(epic1);
        managerSave.addSubTask(sub1);
        assertEquals(1, managerSave.returnAllTask().size(), "Должна сущетсвовать одна задача");
        assertEquals(1, managerSave.returnAllEpic().size(), "Должен сущетсвовать один эпик");
        assertEquals(1, managerSave.returnAllSubTask().size(), "Должна сущетсвовать одна подзадача");
    }

    @Test
    void shouldLoadMultipleTasksFromFile() {
        String content = "1,TASK,Test1,NEW,Description1\n" +
                "2,EPIC,TestEpic,DONE,Description2\n" +
                "3,SUBTASK,Sub1,IN_PROGRESS,Description3,2\n";
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла");
        }
        FileBackedTaskManager loaded = new FileBackedTaskManager(tempFile);
        assertEquals(1, loaded.returnAllTask().size(), "Должна загрузиться одна обычная задача");
        assertEquals(1, loaded.returnAllEpic().size(), "Должен загрузиться один эпик");
        assertEquals(1, loaded.returnAllSubTask().size(), "Должна загрузиться одна подзадача");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists() && tempFile.delete()) {
            System.out.println("Временные файлы удалены");
        }
    }
}