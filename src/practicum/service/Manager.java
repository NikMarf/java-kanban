package practicum.service;

import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class Manager {

    public HashMap<Integer, Task> taskCollection;
    public HashMap<Integer, Epic> epicCollection;

    public Manager() {
        taskCollection = new HashMap<>();
        epicCollection = new HashMap<>();
    }

    public void addTask(Task task) {
        //Добавление нового Task
        taskCollection.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        //Добавление нового Epic
        epicCollection.put(epic.getId(), epic);
        epic.setStatus(checkStatusEpicProgress(epic.getId()));
    }

    public void addSubTaskInEpic(SubTask newSubTask, Integer idParent) {
        //Добавление нового SubTask в Epic
        ArrayList<Integer> repetittionsSubTask = new ArrayList<>();
        for (Integer id : epicCollection.keySet()) {
            if (idParent.equals(id)) {
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

    public void updateTask(Task task, int id) {
        // Обновление Task
        if (taskCollection.containsKey(id)) {
            taskCollection.put(id, task);
        }

        for (Task ts : taskCollection.values()) {
            if (ts.equals(task)) {
                epicCollection.remove(id);
            }
        }
    }

    public void updateEpic(Epic epic, int id) {
        // Обновление Epic
        if (epicCollection.containsKey(id)) {
            epicCollection.put(id, epic);
            epic.setStatus(checkStatusEpicProgress(epic.getId()));
        }

        for (Epic ep : epicCollection.values()) {
            if (ep.equals(epic)) {
                epicCollection.remove(id);
            }
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

    public void printAllTask() {
        //Вывод всеx Task
        for (Integer id : taskCollection.keySet()) {
            Task task = taskCollection.get(id);
            System.out.println(task.toString());
        }
    }

    public void printAllEpic() {
        //Вывод всех Epic
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            System.out.println(epic.toString());
        }
    }

    public void printAllSubTask() {
        //Вывод всех SubTask из всех Epic
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            for (SubTask sb : epic.getSubTasks()) {
                System.out.println(sb.toString());
            }
        }
    }

    public void removeAllTask() {
        //Удаление всеx Task
        taskCollection.clear();
    }

    public void removeAllEpic() {
        //Удаление всеx Epic
        epicCollection.clear();
    }

    public void removeAllSubTask() {
        //Удаление всеx SubTask из всех Epic
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            epic.getSubTasks().clear();
        }
    }

    public void removeByIdTask (int id) {
        //Удаление Task по идентификатору
        taskCollection.remove(id);
    }

    public void RemoveByIdEpic(int id) {
        //Удаление Epic по идентификатору
        epicCollection.remove(id);
    }

    public void removeByIsSubTask(int id) {
        //Удаление SubTask по идентификатору из всех Epic
        for(Epic epic : epicCollection.values()) {
            int i;
            for (i = 0; i < epic.getSubTasks().size(); i++) {
                if (epic.getSubTasks().get(i).getId() == id) {
                    epic.getSubTasks().remove(i);
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

    public ArrayList<SubTask> outputByIdSubTask(int idOutput) {
        //Возвращение конретного SubTask из Epic
        ArrayList<SubTask> idParentSubTask = new ArrayList<>();
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            if (id == idOutput) {
                for (SubTask sb : epic.getSubTasks()) {
                    idParentSubTask.add(sb);
                }
            } else {
                for (SubTask sb : epic.getSubTasks()) {
                    if (sb.getId() == idOutput) {
                        idParentSubTask.add(sb);
                    }
                }
            }
        }
        return idParentSubTask;
    }

    public StatusProgress checkStatusEpicProgress(int id) {
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

