package practicum.service;

import practicum.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public int counterGenerationId = 0;

    private HashMap<Integer, Task> taskCollection;
    private HashMap<Integer, Epic> epicCollection;
    private HashMap<Integer, SubTask> subTaskCollection;
    private final Set<Task> PrioritizedTasks;

    public HashMap<Integer, Task> getTaskCollection() {
        return taskCollection;
    }

    public HashMap<Integer, Epic> getEpicCollection() {
        return epicCollection;
    }

    public HashMap<Integer, SubTask> getSubTaskCollection() {
        return subTaskCollection;
    }

    public Set<Task> getPrioritizedTasks() {
        return PrioritizedTasks;
    }

    public InMemoryHistoryManager getHistoryManager() {
        return historyManager;
    }

    public void setTaskCollection(HashMap<Integer, Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public void setEpicCollection(HashMap<Integer, Epic> epicCollection) {
        this.epicCollection = epicCollection;
    }

    public void setSubTaskCollection(HashMap<Integer, SubTask> subTaskCollection) {
        this.subTaskCollection = subTaskCollection;
    }

    public void setHistoryManager(InMemoryHistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public InMemoryTaskManager() {
        taskCollection = new HashMap<>();
        epicCollection = new HashMap<>();
        subTaskCollection = new HashMap<>();
        PrioritizedTasks = new TreeSet<>(comparator);
    }

    Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getClass().equals(o2.getClass())) {
                if (o1.getStartTime() != null && o2.getStartTime() != null) {
                    if (o1.getStartTime().isAfter(o2.getStartTime())) {
                        return 1;
                    } else if (o1.getStartTime().isBefore(o2.getStartTime())) {
                        return -1;
                    }
                }
                return 0;
            } else {
                if (o1.getStartTime() != null && o2.getStartTime() != null) {
                    if (o1.getStartTime().isAfter(o2.getStartTime())) {
                        return 1;
                    } else if (o1.getStartTime().isBefore(o2.getStartTime())) {
                        return -1;
                    } else {
                        return -1;
                    }
                }
                return 0;
            }

        }
    };

    public boolean isTimeCollisions(Task t1, Task t2) {
        if ((t1.getStartTime() == null && t2.getStartTime() == null) || !t1.getClass().equals(t2.getClass())) {
            return false;
        } else {
            return t1.getStartTime().plusMinutes(t1.getDuration().toMinutes()).isAfter(t2.getStartTime()) &&
                    (t2.getStartTime().plusMinutes(t2.getDuration().toMinutes()).isAfter(t1.getStartTime()
                            .plusMinutes(t1.getDuration().toMinutes())) ||
                            t2.getStartTime().plusMinutes(t2.getDuration().toMinutes()).isAfter(t1.getStartTime()));
        }
    }

    public boolean isTimeCollisionsCollection(Task taskCheck) {
        return getPrioritizedTasks().stream()
                .filter(task -> task.getId() != taskCheck.getId())
                .anyMatch(task -> isTimeCollisions(task, taskCheck));
    }

    @Override
    public void addTask(Task task) {

        if (isTimeCollisionsCollection(task)) {
            throw new IllegalArgumentException("Обнаружено временное пересечение задач");
        } else {
            //Добавление нового Task
            if (task.getId() != 0 && !taskCollection.containsKey(task.getId())) {
                taskCollection.put(task.getId(), task);
                if (task.getStartTime() != null && task.getDuration() != null) {
                    PrioritizedTasks.add(task);
                }
            } else {
                int id = setTaskId();
                task.setId(id);
                taskCollection.put(task.getId(), task);
                if (task.getStartTime() != null && task.getDuration() != null) {
                    PrioritizedTasks.add(task);
                }
            }
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (isTimeCollisionsCollection(epic)) {
            throw new IllegalArgumentException("Обнаружено временное пересечение задач");
        } else {
            //Добавление нового Epic
            if (epic.getId() != 0 && !epicCollection.containsKey(epic.getId())) {
                epicCollection.put(epic.getId(), epic);
                epic.setStatus(checkStatusEpicProgress(epic.getId()));
                if (epic.getStartTime() != null && epic.getDuration() != null) {
                    PrioritizedTasks.add(epic);
                }
            } else {
                int id = setTaskId();
                epic.setId(id);
                epicCollection.put(epic.getId(), epic);
                epic.setStatus(checkStatusEpicProgress(epic.getId()));
                if (epic.getStartTime() != null && epic.getDuration() != null) {
                    PrioritizedTasks.add(epic);
                }
            }
        }

    }

    @Override
    public void addSubTask(SubTask subTask) {
        if (isTimeCollisionsCollection(subTask)) {
            throw new IllegalArgumentException("Обнаружено временное пересечение задач");
        } else {
            if (subTask.getId() != 0 && !subTaskCollection.containsKey(subTask.getId())) {
                subTaskCollection.put(subTask.getId(), subTask);
                if (subTask.getStartTime() != null && subTask.getDuration() != null) {
                    PrioritizedTasks.add(subTask);
                }
            } else {
                int id = setTaskId();
                subTask.setId(id);
                subTaskCollection.put(subTask.getId(), subTask);
                if (subTask.getStartTime() != null && subTask.getDuration() != null) {
                    PrioritizedTasks.add(subTask);
                }
            }
        }

    }

    @Override
    public void addSubTaskInEpic(SubTask newSubTask) {
        if (isTimeCollisionsCollection(newSubTask)) {
            throw new IllegalArgumentException("Обнаружено временное пересечение задач");
        } else {
            //Добавление нового SubTask в Epic
            newSubTask.setId(setTaskId());
            subTaskCollection.put(newSubTask.getId(), newSubTask);
            ArrayList<Integer> repetittionsSubTask = new ArrayList<>();
            for (Integer id : epicCollection.keySet()) {
                if (((Integer)newSubTask.getIdParentTask()).equals(id)) {
                    Epic epic = epicCollection.get(id);
                    newSubTask.setIdParentTask(id);
                    epic.getSubTasks().add(newSubTask);
                    epic.setStatus(checkStatusEpicProgress(epic.getId()));
                    if (newSubTask.getStartTime() != null && newSubTask.getDuration() != null) {
                        epic.setStartTime(newSubTask.getStartTime());
                        epic.setEndTime(newSubTask.getEndTime());
                        epic.setDuration(newSubTask.getDuration());
                        PrioritizedTasks.add(newSubTask);
                    }
                    if (epic.getSubTasks().size() == 1) {
                        if (epic.getStartTime() != null && epic.getDuration() != null) {
                            PrioritizedTasks.add(epic);
                        }
                    }

                }
            }

            for (Epic epic : epicCollection.values()) {
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

    }

    public void addSubTaskInEpicStream(SubTask newSubTask) {
        if (isTimeCollisionsCollection(newSubTask)) {
            throw new IllegalArgumentException("Обнаружено временное пересечение задач");
        }

        // Присваиваем ID и сохраняем подзадачу
        newSubTask.setId(setTaskId());
        subTaskCollection.put(newSubTask.getId(), newSubTask);

        // Находим эпик, к которому принадлежит сабтаск
        epicCollection.entrySet().stream()
                .filter(entry -> entry.getKey().equals(newSubTask.getIdParentTask()))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(epic -> {
                    epic.getSubTasks().add(newSubTask);
                    epic.setStatus(checkStatusEpicProgress(epic.getId()));

                    // Обновляем время и приоритеты
                    if (newSubTask.getStartTime() != null && newSubTask.getDuration() != null) {
                        epic.setStartTime(newSubTask.getStartTime());
                        epic.setEndTime(newSubTask.getEndTime());
                        epic.setDuration(newSubTask.getDuration());
                        PrioritizedTasks.add(newSubTask);
                    }

                    if (epic.getSubTasks().size() == 1 &&
                            epic.getStartTime() != null &&
                            epic.getDuration() != null) {
                        PrioritizedTasks.add(epic);
                    }
                });

        // Удаляем повторяющиеся подзадачи в каждом эпике
        epicCollection.values().forEach(epic -> {
            List<SubTask> uniqueSubtasks = epic.getSubTasks().stream()
                    .distinct()
                    .toList();
            epic.getSubTasks().clear();
            epic.getSubTasks().addAll(uniqueSubtasks);
        });
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

        for (Epic epic : epicCollection.values()) {
            int i;

            for (i = 0; i < epic.getSubTasks().size(); i++) {
                if (epic.getSubTasks().get(i).getId() == idSubtask) {
                    newSubTask.setIdParentTask(epic.getId());
                    epic.getSubTasks().add(i, newSubTask);
                    epic.getSubTasks().remove(i + 1);
                }
            }

            epic.setStatus(checkStatusEpicProgress(epic.getId()));

            for (i = 0; i < epic.getSubTasks().size(); i++) {
                for (int j = i; j < epic.getSubTasks().size(); j++) {
                    if (j < epic.getSubTasks().size() - 1) {
                        if (epic.getSubTasks().get(j).equals(epic.getSubTasks().get(j + 1))) {
                            repetittionsSubTask.add(j + 1);
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
        for (Epic epic : epicCollection.values()) {
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
        if (taskCollection.get(idOutput) != null) {
            historyManager.add(taskCollection.get(idOutput));
        }
        return taskCollection.get(idOutput);
    }

    @Override
    public Epic getByIdEpic(int idOutput) {
        //Возвращение конретного Epic
        if (epicCollection.get(idOutput) != null) {
            historyManager.add(epicCollection.get(idOutput));
        }
        return epicCollection.get(idOutput);
    }

    @Override
    public SubTask getByIdSubTaskTask(int idOutput) {
        //Возвращение конретного SubTask
        if (subTaskCollection.get(idOutput) != null) {
            historyManager.add(subTaskCollection.get(idOutput));
        }
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

    public List<SubTask> outputByIdSubTaskStream(int idOutputEpicOrSubTask) {
        //Возвращение конретного SubTask из Epic
        if (getEpicCollection().containsKey(idOutputEpicOrSubTask)) {
            return subTaskCollection.values().stream()
                    .filter(subTask -> subTask.getIdParentTask() != idOutputEpicOrSubTask)
                    .toList();
        } else if (getSubTaskCollection().containsKey(idOutputEpicOrSubTask)) {
            return subTaskCollection.values().stream()
                    .filter(subTask -> subTask.getId() != idOutputEpicOrSubTask)
                    .toList();
        } else {
            return new ArrayList<>();
        }
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

