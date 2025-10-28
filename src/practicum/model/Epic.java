package practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            if (super.getStartTime() == null && super.getDuration() == null) {
                for (int i = 0; i < subTasks.size(); i++) {
                    if (i == 0) {
                        setStartTime(subTasks.get(i).getStartTime());
                        //setDuration(Duration.ofMinutes(subTasks.get(i).getDuration().toMinutes()));
                    } else {
                        if (subTasks.get(i).getStartTime().isBefore(getStartTime())) {
                            setStartTime(subTasks.get(i).getStartTime());
                            //setDuration(getDuration().plusMinutes(subTasks.get(i).getDuration().toMinutes()));
                        }
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
                    //setDuration(Duration.ofMinutes(subTasks.get(i).getDuration().toMinutes()));
                } else {
                    if (subTasks.get(i).getEndTime().isAfter(endTime)) {
                        endTime = subTasks.get(i).getEndTime();
                        //setDuration(getDuration().plusMinutes(subTasks.get(i).getDuration().toMinutes()));
                    }
                }
            }
        }
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (getEndTime() != null) {
            if (getEndTime().isBefore(endTime)) {
                this.endTime = endTime;
            }
        } else {
            this.endTime = endTime;
        }
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        if (super.getStartTime() != null) {
            if (super.getStartTime().isAfter(startTime)) {
                super.setStartTime(startTime);
            }
        } else {
            super.setStartTime(startTime);
        }
    }

    @Override
    public void setDuration(Duration duration) {
        if (super.getDuration() != null) {
            super.setDuration(this.getDuration().plusMinutes(duration.toMinutes()));

        } else {
            super.setDuration(duration);
        }
    }

    @Override
    public String toString() {
        if (getDuration() == null && getStartTime() == null) {
            return "Epic{" +
                    "subTasks=" + subTasks +
                    ", name='" + getName() + '\'' +
                    ", description='" + getDescription() + '\'' +
                    ", status=" + getStatus() +
                    ", id=" + getId() +
                    '}';
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
            return "Epic{" +
                    "subTasks=" + subTasks +
                    ", name='" + getName() + '\'' +
                    ", description='" + getDescription() + '\'' +
                    ", status=" + getStatus() +
                    ", id=" + getId() +
                    ", duration=" + getDuration() +
                    ", startTime=" + getStartTime().format(formatter) +
                    '}';
        }
    }

    @Override
    public String toStringSave() {
        return super.getId() + "," + TaskFields.EPIC + "," + super.getName() + "," +
                super.getStatus() + "," + super.getDescription() + "/n";
    }
}
