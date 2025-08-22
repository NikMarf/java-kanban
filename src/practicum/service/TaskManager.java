package practicum.service;

import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {

    public static int counterGenerationId = 0;

    private HashMap<Integer, Task> taskCollection;
    private HashMap<Integer, Epic> epicCollection;
    private HashMap<Integer, SubTask> subTaskCollection;

    public TaskManager() {
        taskCollection = new HashMap<>();
        epicCollection = new HashMap<>();
        subTaskCollection = new HashMap<>();
    }

    public HashMap<Integer, Task> getTaskCollection() {
        return taskCollection;
    }

    public HashMap<Integer, Epic> getEpicCollection() {
        return epicCollection;
    }

    public HashMap<Integer, SubTask> getSubTaskCollection() {
        return subTaskCollection;
    }

    private int setTaskId() {
        counterGenerationId++;
        return counterGenerationId;
    }

    public void addTask(Task task) {
        //Добавление нового Task
        int id = setTaskId();
        task.setId(id);
        taskCollection.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        //Добавление нового Epic
        int id = setTaskId();
        epic.setId(id);
        epicCollection.put(epic.getId(), epic);
        epic.setStatus(checkStatusEpicProgress(epic.getId()));
    }

    public void addSubTask(SubTask subTask) {
        int id = setTaskId();
        subTask.setId(id);
        subTaskCollection.put(subTask.getId(), subTask);
    }

    public void addSubTaskInEpic(SubTask newSubTask) {
        //Добавление нового SubTask в Epic
        int idSubTask = setTaskId();
        newSubTask.setId(idSubTask);
        subTaskCollection.put(newSubTask.getId(), newSubTask);
        ArrayList<Integer> repetittionsSubTask = new ArrayList<>();
        for (Integer id : epicCollection.keySet()) {
            if (((Integer)newSubTask.getIdParentTask()).equals(id)) {
                Epic epic = epicCollection.get(id);
                newSubTask.setIdParentTask(id);
                epic.getSubTasks().add(newSubTask);
                epic.setStatus(checkStatusEpicProgress(epic.getId()));
            }
        }
        for(Epic epic : epicCollection.values()) {

            for (int j = 0; j < epic.getSubTasks().size(); j++) {
                if (j < epic.getSubTasks().size() - 1) {
                    if (epic.getSubTasks().get(j).equals(epic.getSubTasks().get(j + 1))) {
                        repetittionsSubTask.add(j + 1);
                    }
                }
            }

            if (!repetittionsSubTask.isEmpty()) {
                for (Integer rep : repetittionsSubTask) {
                    epic.getSubTasks().remove((int) rep);
                }
            }
        }
    }

    public void updateTask(Task task) {
        // Обновление Task
        if (taskCollection.containsKey(task.getId())) {
            taskCollection.put(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        // Обновление Epic
        if (epicCollection.containsKey(epic.getId())) {
            epicCollection.put(epic.getId(), epic);
            epic.setStatus(checkStatusEpicProgress(epic.getId()));
        }
    }

    public void updateSubTask(SubTask newSubTask, int idSubtask) {
        // Обновление SubTask в Epic
        ArrayList<Integer> repetittionsSubTask = new ArrayList<>();
        for(Epic epic : epicCollection.values()) {
            int i;
            for (i = 0; i < epic.getSubTasks().size(); i++) {
                if (epic.getSubTasks().get(i).getId() == idSubtask) {
                    newSubTask.setIdParentTask(epic.getId());
                    epic.getSubTasks().add(i, newSubTask);
                    epic.getSubTasks().remove(i+1);
                }
            }

            epic.setStatus(checkStatusEpicProgress(epic.getId()));

            for (i = 0; i < epic.getSubTasks().size(); i++) {
                for (int j = i; j < epic.getSubTasks().size(); j++) {
                    if (j < epic.getSubTasks().size() - 1) {
                        if (epic.getSubTasks().get(j).equals(epic.getSubTasks().get(j+1))) {
                            repetittionsSubTask.add(j+1);
                        }
                    }
                }
            }
            if (!repetittionsSubTask.isEmpty()) {
                for (Integer rep : repetittionsSubTask) {
                    epic.getSubTasks().remove((int) rep);
                }
            }
        }
    }

    public HashMap<Integer, Task> returnAllTask() {
        //Возвращение всеx Task
        return taskCollection;
    }

    public HashMap<Integer, Epic> returnAllEpic() {
        //Возвращение всех Epic
        return epicCollection;
    }

    public HashMap<Integer, SubTask> returnAllSubTask() {
        //Вывод всех SubTask из всех Epic
        return subTaskCollection;
    }

    public void removeAllTask() {
        //Удаление всеx Task
        taskCollection.clear();
    }

    public void removeAllEpic() {
        //Удаление всеx Epic и следовательно SubTask
        epicCollection.clear();
        subTaskCollection.clear();
    }

    public void removeAllSubTask() {
        //Удаление всеx SubTask из всех Epic
        subTaskCollection.clear();
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            epic.getSubTasks().clear();
            epic.setStatus(StatusProgress.NEW);
        }

    }

    public void removeByIdTask (int id) {
        //Удаление Task по идентификатору
        taskCollection.remove(id);
    }

    public void RemoveByIdEpic(int id) {
        //Удаление Epic по идентификатору
        epicCollection.remove(id);
        for (SubTask sb : subTaskCollection.values()) {
            if (sb.getIdParentTask() == id) {
                subTaskCollection.remove(sb.getId());
            }
        }
    }

    public void removeByIsSubTask(int id) {
        //Удаление SubTask по идентификатору из всех Epic
        subTaskCollection.remove(id);
        for(Epic epic : epicCollection.values()) {
            int i;
            for (i = 0; i < epic.getSubTasks().size(); i++) {
                if (epic.getSubTasks().get(i).getId() == id) {
                    epic.getSubTasks().remove(i);
                    epic.setStatus(checkStatusEpicProgress(epic.getId()));
                }
            }
        }

    }

    public Task outputByIdTask(int idOutput) {
        //Возвращение конретного Task
        return taskCollection.get(idOutput);
    }

    public Epic outputByIdEpic(int idOutput) {
        //Возвращение конретного Epic
        return epicCollection.get(idOutput);
    }

    public SubTask outputByIdSubTaskTask(int idOutput) {
        //Возвращение конретного SubTask
        return subTaskCollection.get(idOutput);
    }

    public ArrayList<SubTask> outputByIdSubTask(int idOutputEpicOrSubTask) {
        //Возвращение конретного SubTask из Epic
        ArrayList<SubTask> idParentSubTask = new ArrayList<>();
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            if (id == idOutputEpicOrSubTask) {
                for (SubTask sb : epic.getSubTasks()) {
                    idParentSubTask.add(sb);
                }
            } else {
                for (SubTask sb : epic.getSubTasks()) {
                    if (sb.getId() == idOutputEpicOrSubTask) {
                        idParentSubTask.add(sb);
                    }
                }
            }
        }
        return idParentSubTask;
    }

    private StatusProgress checkStatusEpicProgress(int id) {
        //Присвоение статуса Epic
        Epic epic = epicCollection.get(id);
        StatusProgress statusProgress = StatusProgress.NEW;
        int i = 0;
        for (SubTask sb : epic.getSubTasks()) {
            switch (sb.getStatus()) {
                case NEW:
                    break;
                case IN_PROGRESS:
                    statusProgress = StatusProgress.IN_PROGRESS;
                    continue;
                case DONE:
                    if ((epic.getSubTasks().size()-1) == i) {
                        statusProgress = StatusProgress.DONE;
                        return statusProgress;
                    } else {
                        i++;
                        statusProgress = StatusProgress.IN_PROGRESS;
                        break;
                    }

            }
        }

        return statusProgress;
    }





}

