public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        //System.out.println("Поехали!");

        Task task1 = new Task("Реализовать ближе к вечеру", "Сходить к врачу", StatusProgress.NEW);
        Epic taskEpic1 = new Epic("Сделать срочно!", "Убраться в квартире", StatusProgress.NEW);
        Epic taskEpic2 = new Epic("Я хз что писать", "ТЕСТ", StatusProgress.NEW);
        //System.out.println(taskEpic1.id);
       //System.out.println(task1.id);
        taskEpic1.addSubTask("Сделать срочно!", "Пропылесосить", StatusProgress.NEW);
        taskEpic1.addSubTask("После пылесоса", "Протереть полки", StatusProgress.NEW);
        taskEpic2.addSubTask("Новая задача", "Отладить все", StatusProgress.NEW);
        //System.out.println(taskEpic1.subTasks.toString());
        //System.out.println(taskEpic1.toString());
        manager.epicCollection.put(taskEpic1.id, taskEpic1);
        manager.epicCollection.put(taskEpic2.id, taskEpic2);
        manager.taskCollection.put(task1.id, task1);
        //System.out.println(manager.epicCollection.toString());
        //manager.printAllSubTask();
        //manager.printAllEpic();
        //manager.printAllTask();
        manager.removeAllSubTask();
        //manager.removeAllTask();
        //manager.removeAllEpic();
        //manager.printAllTask();
        //System.out.println(manager.taskCollection);
        System.out.println(manager.epicCollection);
    }
}
