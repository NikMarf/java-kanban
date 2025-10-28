package practicum.service;

import practicum.model.Epic;
import practicum.model.SubTask;
import practicum.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TaskManager {
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    void addSubTaskInEpic(SubTask newSubTask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateEpic(Epic epic, int idEpic);

    public void updateSubTask(SubTask subTask);

    void updateSubTask(SubTask newSubTask, int idSubtask);

    ArrayList<Task> returnAllTask();

    ArrayList<Epic> returnAllEpic();

    ArrayList<SubTask> returnAllSubTask();

    void removeAllTask();

    void removeAllEpic();

    void removeAllSubTask();

    void removeByIdTask(int id);

    void removeByIdEpic(int id);

    void removeByIsSubTask(int id);

    Task getByIdTask(int idOutput);

    Epic getByIdEpic(int idOutput);

    SubTask getByIdSubTaskTask(int idOutput);

    List<SubTask> outputByIdSubTask(int idOutputEpicOrSubTask);

    ArrayList<Task> getHistory();

}
