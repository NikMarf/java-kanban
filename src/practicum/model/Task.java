package practicum.model;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private StatusProgress status;
    private int id;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusProgress status) {
        this.status = status;
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
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }

    public String toStringSave() {
        return id + "," + TaskFields.TASK + "," + name + "," + status + "," + description + "/n";
    }
}
