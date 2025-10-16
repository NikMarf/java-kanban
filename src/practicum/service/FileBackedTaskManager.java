package practicum.service;

import practicum.exceptiones.ManagerSaveException;
import practicum.model.*;

import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String DIR = System.getProperty("user.dir");
    private static final String FILE_MEMORY_NAME = "csvTaskMemory.csv";
    private static boolean isNotCallConstructorFile = true;

    public FileBackedTaskManager()  {
        Path path = Paths.get(DIR, FILE_MEMORY_NAME);
        try {
            Files.createFile(path);
            System.out.println("Создан файл памяти в директории: " + DIR);
        } catch (IOException e) {
            System.out.println("Файл памяти уже существует");
            isNotCallConstructorFile = false;
            FileBackedTaskManager loadManager = loadFromFile(path.toFile());
            setTaskCollection(loadManager.getTaskCollection());
            setEpicCollection(loadManager.getEpicCollection());
            setSubTaskCollection(loadManager.getSubTaskCollection());
            setHistoryManager(loadManager.getHistoryManager());
        }
    }

    public FileBackedTaskManager(File file, boolean isNotCallConstructorFlag) {
        if (isNotCallConstructorFlag) {
            FileBackedTaskManager loadManager = loadFromFile(file);
            setTaskCollection(loadManager.getTaskCollection());
            setEpicCollection(loadManager.getEpicCollection());
            setSubTaskCollection(loadManager.getSubTaskCollection());
            setHistoryManager(loadManager.getHistoryManager());
        }
    }

    public FileBackedTaskManager(File file) {
            isNotCallConstructorFile = false;
            FileBackedTaskManager loadManager = loadFromFile(file);
            setTaskCollection(loadManager.getTaskCollection());
            setEpicCollection(loadManager.getEpicCollection());
            setSubTaskCollection(loadManager.getSubTaskCollection());
            setHistoryManager(loadManager.getHistoryManager());
            System.out.println("Загрузка файла памяти: " + file.getPath());
    }

    @Override
    public HashMap<Integer, Task> getTaskCollection() {
        return super.getTaskCollection();
    }

    @Override
    public HashMap<Integer, Epic> getEpicCollection() {
        return super.getEpicCollection();
    }

    @Override
    public HashMap<Integer, SubTask> getSubTaskCollection() {
        return super.getSubTaskCollection();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void addSubTaskInEpic(SubTask newSubTask) {
        super.addSubTaskInEpic(newSubTask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int idEpic) {
        super.updateEpic(epic, idEpic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask newSubTask, int idSubtask) {
        super.updateSubTask(newSubTask, idSubtask);
        save();
    }

    @Override
    public ArrayList<Task> returnAllTask() {
        return super.returnAllTask();
    }

    @Override
    public ArrayList<Epic> returnAllEpic() {
        return super.returnAllEpic();
    }

    @Override
    public ArrayList<SubTask> returnAllSubTask() {
        return super.returnAllSubTask();
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeAllSubTask() {
        super.removeAllSubTask();
        save();
    }

    @Override
    public void removeByIdTask(int id) {
        super.removeByIdTask(id);
        save();
    }

    @Override
    public void removeByIdEpic(int id) {
        super.removeByIdEpic(id);
        save();
    }

    @Override
    public void removeByIsSubTask(int id) {
        super.removeByIsSubTask(id);
        save();
    }

    @Override
    public Task getByIdTask(int idOutput) {
        return super.getByIdTask(idOutput);
    }

    @Override
    public Epic getByIdEpic(int idOutput) {
        return super.getByIdEpic(idOutput);
    }

    @Override
    public SubTask getByIdSubTaskTask(int idOutput) {
        return super.getByIdSubTaskTask(idOutput);
    }

    @Override
    public ArrayList<SubTask> outputByIdSubTask(int idOutputEpicOrSubTask) {
        return super.outputByIdSubTask(idOutputEpicOrSubTask);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return super.getHistory();
    }

    private void save() {
        try (BufferedWriter memoryFile = new BufferedWriter(new FileWriter(FILE_MEMORY_NAME))) {
            for (Task task : returnAllTask()) {
                memoryFile.write(toString(task) + "\n");
            }
            for (Epic epic : returnAllEpic()) {
                memoryFile.write(toString(epic) + "\n");
            }
            for (SubTask subtask : returnAllSubTask()) {
                memoryFile.write(toString(subtask) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении файла", e);
        }
    }

    private String toString(Task task) {
        StringBuilder example = new StringBuilder();
        String strClass = task.getClass().getTypeName();
        String str = "";
        for (int i = strClass.length() - 1; i < strClass.length(); i--) {
            if (strClass.charAt(i) != '.') {
                example.append(strClass.charAt(i));
            } else {
                break;
            }
        }
        example.reverse();
        switch (example.toString()) {
            case "Task":
                return str = String.join(",",
                        String.valueOf(task.getId()),
                        TaskFields.TASK.toString(),
                        task.getName(),
                        task.getStatus().toString(),
                        task.getDescription());
            case "Epic":
                return str = String.join(",",
                        String.valueOf(task.getId()),
                        TaskFields.EPIC.toString(),
                        task.getName(),
                        task.getStatus().toString(),
                        task.getDescription());
            case "SubTask":
                SubTask sub = (SubTask)task;
                return str = String.join(",",
                        String.valueOf(task.getId()),
                        TaskFields.SUBTASK.toString(),
                        task.getName(),
                        task.getStatus().toString(),
                        task.getDescription(),
                        String.valueOf(sub.getIdParentTask()));
            default:
                return str;
        }
    }

    private static Task fromString(String value) {
        String[] splitTask = value.split(",");
        switch (splitTask[1]) {
            case "TASK":
                return new Task(splitTask[2], splitTask[4], StatusProgress.valueOf(splitTask[3]),
                        Integer.parseInt(splitTask[0]));
            case "EPIC":
                return new Epic(splitTask[2], splitTask[4], StatusProgress.valueOf(splitTask[3]),
                        Integer.parseInt(splitTask[0]));
            case "SUBTASK":
                return new SubTask(splitTask[2], splitTask[4], StatusProgress.valueOf(splitTask[3]),
                        Integer.parseInt(splitTask[0]), Integer.parseInt(splitTask[5]));
            default:
                return null;
        }
    }

    private static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager backedTaskManager = new FileBackedTaskManager(file, isNotCallConstructorFile);
        try (BufferedReader loadMemory = new BufferedReader(new FileReader(file))) {
            while (loadMemory.ready()) {
                String str = loadMemory.readLine();

                StringBuilder example = new StringBuilder();
                String strClass = fromString(str).getClass().getTypeName();
                for (int i = strClass.length() - 1; i < strClass.length(); i--) {
                    if (strClass.charAt(i) != '.') {
                        example.append(strClass.charAt(i));
                    } else {
                        break;
                    }
                }
                example.reverse();

                if (example.toString().equals("Task")) {
                    Task task = fromString(str);
                    backedTaskManager.addTask(task);
                } else if (example.toString().equals("Epic")) {
                    Task epic = fromString(str);
                    backedTaskManager.addEpic((Epic) epic);
                } else if (example.toString().equals("SubTask")) {
                    Task subTask = fromString(str);
                    backedTaskManager.addSubTaskInEpic((SubTask) subTask);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла", e);
        }
        return backedTaskManager;
    }
}
