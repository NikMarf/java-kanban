package practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> subTasks; //Список дочерних SubTask
    private LocalDateTime endTime;

    public Epic(String description, String name, StatusProgress status) {
        super(name, description, status);
        this.subTasks = new ArrayList<>();
    }

    public Epic(String description, String name, StatusProgress status, int id) {
        super(name, description, status, id);
        this.subTasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public LocalDateTime getStartTime() {
        if (!subTasks.isEmpty() && subTasks.size() > 1) {
            for (int i = 0; i < subTasks.size(); i++) {
                if (i == 0) {
                    setStartTime(subTasks.get(i).getStartTime());
                    setDuration(Duration.ofMinutes(subTasks.get(i).getDuration().toMinutes()));
                } else {
                    if (subTasks.get(i).getStartTime().isBefore(getStartTime())) {
                        setStartTime(subTasks.get(i).getStartTime());
                        setDuration(getDuration().plusMinutes(subTasks.get(i).getDuration().toMinutes()));
                    }
                }
            }
        }
        return super.getStartTime();
    }

    @Override
    public LocalDateTime getEndTime() {
        if (!subTasks.isEmpty() && subTasks.size() > 1) {
            for (int i = 0; i < subTasks.size(); i++) {
                if (i == 0) {
                    endTime = subTasks.get(i).getEndTime();
                    setDuration(Duration.ofMinutes(subTasks.get(i).getDuration().toMinutes()));
                } else {
                    if (subTasks.get(i).getEndTime().isAfter(endTime)) {
                        endTime = subTasks.get(i).getEndTime();
                        setDuration(getDuration().plusMinutes(subTasks.get(i).getDuration().toMinutes()));
                    }
                }
            }
        }
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
