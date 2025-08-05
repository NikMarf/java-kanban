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
    }

    public void addSubTaskInEpic(SubTask newSubTask, Integer idParent) {
        for (Integer id : epicCollection.keySet()) {
            if (idParent.equals(id)) {
                Epic epic = epicCollection.get(id);
                epic.subTasks.add(newSubTask);
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



}

