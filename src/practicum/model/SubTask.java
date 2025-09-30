package practicum.model;

public class SubTask extends Task {

    private int idParentTask; //Поле хранения идентификатора родителя

    public SubTask(String name, String description, StatusProgress status, int idParentTask) {
        super(name, description, status);
        this.idParentTask = idParentTask;
    }

    public SubTask(String name, String description, StatusProgress status, int id, int idParentTask) {
        super(name, description, status, id);
        this.idParentTask = idParentTask;
    }

    public SubTask(String name, String description, StatusProgress status) {
        super(name, description, status);
    }

    public int getIdParentTask() {
        return idParentTask;
    }

    public void setIdParentTask(int idParentTask) {
        this.idParentTask = idParentTask;
    }


    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                ", idParentTask=" + idParentTask +
                '}';
    }
}
