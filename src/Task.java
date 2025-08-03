import java.util.Objects;

public class Task {
    String name;
    String description;
    StatusProgress status;
    int id;

    public Task(String name, String description, StatusProgress status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = hashCode();
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
}
