import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    public ArrayList<SubTask> subTasks;

    public Epic(String description, String name, StatusProgress status) {
        super(name, description, status);
        this.id = hashCode();
        subTasks = new ArrayList<>();
    }

    public void addSubTask(String description, String name, StatusProgress status) {
        SubTask subTaskForEpic = new SubTask(description, name, status, this.id);
        subTasks.add(subTaskForEpic);
        System.out.println("Сделано!");
    }

    public void addSubTask1(SubTask newSubTask) {
        SubTask subTaskForEpic = new SubTask(description, name, status, this.id);

        subTasks.add(subTaskForEpic);
        System.out.println("Сделано!");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasks=" + subTasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
