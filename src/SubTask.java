import java.util.Objects;

public class SubTask extends Task {
    int idParentTask;

    public SubTask(String name, String description, StatusProgress status, int idParentTask) {
        super(name, description, status);
        this.id = hashCode();
        this.idParentTask = idParentTask;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return idParentTask == subTask.idParentTask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idParentTask);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", idParentTask=" + idParentTask +
                '}';
    }
}
