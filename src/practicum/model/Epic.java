package practicum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> subTasks; //Список дочерних SubTask

    public Epic(String description, String name, StatusProgress status) {
        super(name, description, status);
        subTasks = new ArrayList<>();
    }

    public Epic(String description, String name, StatusProgress status, int id) {
        super(name, description, status, id);
        subTasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public LocalDateTime getEndTime() {
        return getStartTime().plusMinutes(getDuration().toMinutes());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasks=" + subTasks +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }

    @Override
    public String toStringSave() {
        return super.getId() + "," + TaskFields.EPIC + "," + super.getName() + "," +
                super.getStatus() + "," + super.getDescription() + "/n";
    }
}
