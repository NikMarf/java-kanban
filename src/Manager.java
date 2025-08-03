import java.util.HashMap;
import java.util.ArrayList;

public class Manager {

    public HashMap<Integer, Task> taskCollection;
    public HashMap<Integer, Epic> epicCollection;

    public Manager() {
        taskCollection = new HashMap<>();
        epicCollection = new HashMap<>();
    }

    public void printAllTask() {
        for (Integer id : taskCollection.keySet()) {
            Task task = taskCollection.get(id);
            System.out.println(task.toString());

        }
    }

    public void printAllEpic() {
        for (Integer id : epicCollection.keySet()) {
            Task epic = epicCollection.get(id);
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
}

