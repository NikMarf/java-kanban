public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        //System.out.println("Поехали!");

        Task task1 = new Task("Реализовать ближе к вечеру", "Сходить к врачу", StatusProgress.NEW);
        Epic taskEpic1 = new Epic("Сделать срочно!", "Убраться в квартире", StatusProgress.NEW);
        Epic taskEpic2 = new Epic("Я хз что писать", "ТЕСТ", StatusProgress.NEW);
        //System.out.println(taskEpic1.id);
       //System.out.println(task1.id);
        taskEpic1.addSubTask("Сделать срочно!", "Пропылесосить", StatusProgress.DONE);
        taskEpic1.addSubTask("После пылесоса", "Протереть полки", StatusProgress.NEW);
        taskEpic2.addSubTask("Новая задача", "Отладить все", StatusProgress.NEW);
        SubTask subTest = new SubTask("ХХХХХ", "ЫЫЫЫЫЫЫ", StatusProgress.IN_PROGRESS);
        SubTask subTest1 = new SubTask("dddddd", "ЫЫЫooЫЫ", StatusProgress.DONE);
        //System.out.println(taskEpic1.subTasks.toString());
        //System.out.println(taskEpic1.toString());
        manager.epicCollection.put(taskEpic1.id, taskEpic1);
        manager.epicCollection.put(taskEpic2.id, taskEpic2);
        manager.taskCollection.put(task1.id, task1);
        //System.out.println(manager.epicCollection.toString());
        //manager.printAllSubTask();
        manager.printAllEpic();
        //manager.printAllTask();
        //manager.removeAllSubTask();
        //manager.removeAllTask();
        //manager.removeAllEpic();
        manager.printAllTask();
        //System.out.println(manager.taskCollection);
        System.out.println(manager.epicCollection);
        manager.addSubTaskInEpic(subTest, 1784124353);
        //System.out.println(manager.outputByIdSubTask());
        System.out.println(manager.epicCollection);
        Task task2 = new Task("Реализовать ближе к вечеру", "Сходить к врачу", StatusProgress.IN_PROGRESS);
        manager.updateTask(task2, -1158995916);
        manager.printAllTask();
        //Epic taskEpic3 = new Epic("Сделать потом!", "Убраться в квартире", StatusProgress.NEW);
        //manager.updateEpic(taskEpic3,1784124353);
        SubTask subTest2 = new SubTask("sss", "oo", StatusProgress.DONE);
        SubTask subTest3 = new SubTask("TTTT", "PPPPP", StatusProgress.DONE);
        manager.addSubTaskInEpic(subTest,-446346727);
        System.out.println(manager.epicCollection);
        manager.updateSubTask(subTest2, 2029377073);
        manager.updateSubTask(subTest2, -210281634);
        manager.updateSubTask(subTest1, -171482827);
        System.out.println(manager.epicCollection);
    }
}
