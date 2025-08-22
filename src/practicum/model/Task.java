package practicum.model;

import java.util.Objects;

public class Task {
    private String name;
    String description;
    StatusProgress status;
    int id;

    public Task(String name, String description, StatusProgress status) {
        this.name = name; // Поле имени
        this.description = description; // Поле описани
        this.status = status; // Поле статуса
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

    public void setId(int id) {  // нужен для установки нового id
        this.id = id;
    }

    public void setStatus(StatusProgress status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
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
}
