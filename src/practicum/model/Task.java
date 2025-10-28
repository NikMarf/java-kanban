package practicum.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private StatusProgress status;
    private int id;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String name, String description, StatusProgress status) {
        this.name = name; // Поле имени
        this.description = description; // Поле описани
        this.status = status; // Поле статуса
    }

    public Task(String name, String description, StatusProgress status, int id) {
        this.name = name; // Поле имени
        this.description = description; // Поле описани
        this.status = status; // Поле статуса
        this.id = id; // Поле идентификатора
    }

    public Task(String name, String description, StatusProgress status, long duration, LocalDateTime startTime) {
        this.name = name; // Поле имени
        this.description = description; // Поле описани
        this.status = status; // Поле статуса
        this.startTime = startTime; // Поле даты и времени когда предполагается приступить к выполнению задачи
        this.duration = Duration.ofMinutes(duration); // Поле продолжительности задачи
    }

    public Task(String name, String description, StatusProgress status, int id, long duration, LocalDateTime startTime) {
        this.name = name; // Поле имени
        this.description = description; // Поле описани
        this.status = status; // Поле статуса
        this.id = id; // Поле идентификатора
        this.startTime = startTime; // Поле даты и времени когда предполагается приступить к выполнению задачи
        this.duration = Duration.ofMinutes(duration); // Поле продолжительности задачи
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public StatusProgress getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusProgress status) {
        this.status = status;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof SubTask || o instanceof Epic) {
                Task task = (Task) o;
                return id == task.id;
            }
        }
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        if (duration == null && startTime == null) {
            return "Task{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", status=" + status +
                    ", id=" + id +
                    '}';
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
            return "Task{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", status=" + status +
                    ", id=" + id +
                    ", duration=" + duration +
                    ", startTime=" + startTime.format(formatter) +
                    '}';
        }
    }

    public String toStringSave() {
        return id + "," + TaskFields.TASK + "," + name + "," + status + "," + description + "/n";
    }
}
