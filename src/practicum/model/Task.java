package practicum.model;

import java.util.Objects;

public class Task {
    String name;
    String description;
    StatusProgress status;
    int id;

    public Task(String name, String description, StatusProgress status) {
        this.name = name; // Поле имени
        this.description = description; // Поле описани
        this.status = status; // Поле статуса
        this.id = hashCode(); // Поле идентификатора
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

    public void setStatus(StatusProgress status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status && id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
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
