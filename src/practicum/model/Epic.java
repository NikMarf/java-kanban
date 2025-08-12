package practicum.model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    ArrayList<SubTask> subTasks; //Список дочерних SubTask

    public Epic(String description, String name, StatusProgress status) {
        super(name, description, status);
        this.id = hashCode();
        subTasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
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
