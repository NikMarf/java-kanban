package practicum.service;

import practicum.model.Epic;
import practicum.model.StatusProgress;
import practicum.model.SubTask;
import practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public int counterGenerationId = 0;

    private HashMap<Integer, Task> taskCollection;
    private HashMap<Integer, Epic> epicCollection;
    private HashMap<Integer, SubTask> subTaskCollection;

    public InMemoryTaskManager() {
        taskCollection = new HashMap<>();
        epicCollection = new HashMap<>();
        subTaskCollection = new HashMap<>();
    }


    @Override
    public void addTask(Task task) {
        //Добавление нового Task
        int id = setTaskId();
        task.setId(id);
        taskCollection.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        //Добавление нового Epic
        int id = setTaskId();
        epic.setId(id);
        epicCollection.put(epic.getId(), epic);
        epic.setStatus(checkStatusEpicProgress(epic.getId()));
    }

    @Override
    public void addSubTask(SubTask subTask) {
        int id = setTaskId();
        subTask.setId(id);
        subTaskCollection.put(subTask.getId(), subTask);
    }

    @Override
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

    @Override
    public void updateTask(Task task) {
        // Обновление Task
        if (taskCollection.containsKey(task.getId())) {
            taskCollection.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        // Обновление Epic
        if (epicCollection.containsKey(epic.getId())) {
            epicCollection.put(epic.getId(), epic);
            epic.setStatus(checkStatusEpicProgress(epic.getId()));
        }
    }

    @Override
    public void updateEpic(Epic epic, int idEpic) {
        // Обновление Epic
        if (epicCollection.containsKey(idEpic)) {
            epicCollection.put(idEpic, epic);
            epic.setStatus(checkStatusEpicProgress(idEpic));
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        // Обновление Epic
        if (subTaskCollection.containsKey(subTask.getId())) {
            subTaskCollection.put(subTask.getId(), subTask);
        }
    }

    @Override
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

    @Override
    public ArrayList<Task> returnAllTask() {
        //Возвращение всеx Task
        ArrayList<Task> allTask = new ArrayList<>();
        for (Task task : taskCollection.values()) {
            allTask.add(task);
        }
        return allTask;
    }

    @Override
    public ArrayList<Epic> returnAllEpic() {
        //Возвращение всех Epic
        ArrayList<Epic> allEpic = new ArrayList<>();
        for (Epic epic : epicCollection.values()) {
            allEpic.add(epic);
        }
        return allEpic;
    }

    @Override
    public ArrayList<SubTask> returnAllSubTask() {
        //Вывод всех SubTask из всех Epic
        ArrayList<SubTask> allSubTask = new ArrayList<>();
        for (SubTask subTask : subTaskCollection.values()) {
            allSubTask.add(subTask);
        }
        return allSubTask;
    }

    @Override
    public void removeAllTask() {
        //Удаление всеx Task
        taskCollection.clear();

    }

    @Override
    public void removeAllEpic() {
        //Удаление всеx Epic и следовательно SubTask
        epicCollection.clear();
        subTaskCollection.clear();
    }

    @Override
    public void removeAllSubTask() {
        //Удаление всеx SubTask из всех Epic
        subTaskCollection.clear();
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            epic.getSubTasks().clear();
            epic.setStatus(StatusProgress.NEW);
        }
    }

    @Override
    public void removeByIdTask(int id) {
        //Удаление Task по идентификатору
        taskCollection.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeByIdEpic(int id) {
        //Удаление Epic по идентификатору
        epicCollection.remove(id);
        for (SubTask sb : subTaskCollection.values()) {
            if (sb.getIdParentTask() == id) {
                subTaskCollection.remove(sb.getId());
            }
        }
        historyManager.remove(id);
    }

    @Override
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
        historyManager.remove(id);
    }

    @Override
    public Task getByIdTask(int idOutput) {
        //Возвращение конретного Task
        historyManager.add(taskCollection.get(idOutput));
        return taskCollection.get(idOutput);
    }

    @Override
    public Epic getByIdEpic(int idOutput) {
        //Возвращение конретного Epic
        historyManager.add(epicCollection.get(idOutput));
        return epicCollection.get(idOutput);
    }

    @Override
    public SubTask getByIdSubTaskTask(int idOutput) {
        //Возвращение конретного SubTask
        historyManager.add(subTaskCollection.get(idOutput));
        return subTaskCollection.get(idOutput);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
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
                    if ((epic.getSubTasks().size() - 1) == i) {
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

    private int setTaskId() {
        counterGenerationId++;
        return counterGenerationId;
    }

}

