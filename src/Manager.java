import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {

    Scanner scanner = new Scanner(System.in);

    public HashMap<Integer, Task> taskCollection;
    public HashMap<Integer, Epic> epicCollection;

    public Manager() {
        taskCollection = new HashMap<>();
        epicCollection = new HashMap<>();
    }

    public void addTask(Task task) {
        taskCollection.put(task.id, task);
    }

    public void addEpic(Epic epic) {
        epicCollection.put(epic.id, epic);
        epic.status = checkStatusEpicProgress(epic.id);
    }

    public void addSubTaskInEpic(SubTask newSubTask, Integer idParent) {
        for (Integer id : epicCollection.keySet()) {
            if (idParent.equals(id)) {
                Epic epic = epicCollection.get(id);
                newSubTask.idParentTask = id;
                epic.subTasks.add(newSubTask);
                epic.status = checkStatusEpicProgress(epic.id);
            }
        }

    }

    public void updateTask(Task task, int id) {
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
        if (epicCollection.containsKey(id)) {
            epicCollection.put(id, epic);
            epic.status = checkStatusEpicProgress(epic.id);
        }

        for (Epic ep : epicCollection.values()) {
            if (ep.equals(epic)) {
                epicCollection.remove(id);
            }
        }

    }

    public void updateSubTask(SubTask newSubTask, int idSubtask) {
        ArrayList<Integer> repetittionsSubTask = new ArrayList<>();
        for(Epic epic : epicCollection.values()) {
            int i;
            for (i = 0; i < epic.subTasks.size(); i++) {
                if (epic.subTasks.get(i).id == idSubtask) {
                    newSubTask.idParentTask = epic.id;
                    epic.subTasks.add(i, newSubTask);
                    epic.subTasks.remove(i+1);

                }
            }
            epic.status = checkStatusEpicProgress(epic.id);

            for (i = 0; i < epic.subTasks.size(); i++) {
                for (int j = i; j < epic.subTasks.size(); j++) {
                    if (j < epic.subTasks.size() - 1) {
                        if (epic.subTasks.get(j).equals(epic.subTasks.get(j+1))) {
                            repetittionsSubTask.add(j+1);
                        }
                    }
                }
            }
            if (!repetittionsSubTask.isEmpty()) {
                for (Integer rep : repetittionsSubTask) {
                    epic.subTasks.remove((int) rep);
                }
            }
        }



    }

    public void printAllTask() {
        for (Integer id : taskCollection.keySet()) {
            Task task = taskCollection.get(id);
            System.out.println(task.toString());
        }
    }

    public void printAllEpic() {
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            System.out.println(epic.toString());
        }
    }

    public void printAllSubTask() {
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            for (SubTask sb : epic.subTasks) {
                System.out.println(sb.toString());
            }
        }
    }

    public void removeAllTask() {
        taskCollection.clear();
    }

    public void removeAllEpic() {
        epicCollection.clear();
    }

    public void removeAllSubTask() {
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            epic.subTasks.clear();
        }
    }

    public void removeByIdTask (int id) {
        taskCollection.remove(id);
    }

    public void RemoveByIdEpic(int id) {
        epicCollection.remove(id);
    }

    public void removeByIsSubTask(int id) {
        for(Epic epic : epicCollection.values()) {
            int i;
            for (i = 0; i < epic.subTasks.size(); i++) {
                if (epic.subTasks.get(i).id == id) {
                    epic.subTasks.remove(i);
                }
            }
        }

    }

    public Task outputByIdTask() {
        System.out.println("Введите id Task:");
        int idOutput = scanner.nextInt();
        return taskCollection.get(idOutput);
    }

    public Epic outputByIdEpic() {
        System.out.println("Введите id Epic:");
        int idOutput = scanner.nextInt();
        return epicCollection.get(idOutput);
    }

    public ArrayList<SubTask> outputByIdSubTask() {
        System.out.println("Введите id Parent или SubTask:");
        int idOutput = scanner.nextInt();
        ArrayList<SubTask> idParentSubTask = new ArrayList<>();
        for (Integer id : epicCollection.keySet()) {
            Epic epic = epicCollection.get(id);
            //System.out.println(epicCollection.get(id));
            if (id == idOutput) {
                for (SubTask sb : epic.subTasks) {
                    idParentSubTask.add(sb);
                }
            } else {
                for (SubTask sb : epic.subTasks) {
                    if (sb.id == idOutput) {
                        idParentSubTask.add(sb);
                    }
                }
            }
        }
        return idParentSubTask;
    }

    public StatusProgress checkStatusEpicProgress(int id) {
        Epic epic = epicCollection.get(id);
        StatusProgress statusProgress = StatusProgress.NEW;
        int i = 0;
        for (SubTask sb : epic.subTasks) {
            switch (sb.status) {
                case NEW:
                    break;
                case IN_PROGRESS:
                    statusProgress = StatusProgress.IN_PROGRESS;
                    continue;
                case DONE:
                    if ((epic.subTasks.size()-1) == i) {
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

