package practicum.service;

import practicum.model.Task;
import practicum.model.Node;

import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {

    public ArrayList<Task> listHistory = new ArrayList<>();
    public Map<Integer, Node<Task>> nodeHistory = new HashMap<>();

    public Node<Task> head;
    public Node<Task> tail;

    @Override
    public void add(Task task) {
        if (nodeHistory.containsKey(task.getId())) {
            removeNode(nodeHistory.get(task.getId()));
            linkLast(nodeHistory.get(task.getId()));
        } else {
            Node<Task> node = new Node<>(task);
            linkLast(node);
            nodeHistory.put(task.getId(), node);
        }
    }

    @Override
    public void remove(int id) {
        Node<Task> node = nodeHistory.remove(id);
        if (node != null) {
            if (node == head && node == tail) {
                head = null;
                tail = null;
            } else if (node == head) {
                head = node.next;
                head.prev = null;
            } else if (node == tail) {
                tail = node.prev;
                tail.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }

            node.prev = null;
            node.next = null;
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(getTasks());
    }

    private void linkLast(Node<Task> node) {
        if (node != tail) {
            if (tail == null) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
        }

        tail = node;

    }

    private ArrayList<Task> getTasks() {
        listHistory.clear();
        for (Node<Task> x = head; x != null; x = x.next) {
            listHistory.add(x.data);
        }
        return listHistory;
    }

    private void removeNode(Node<Task> node) {
        if (node == tail) {
            return;
        }
        if (node != head) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else {
            node.next.prev = null;
            head = node.next;
        }
        node.prev = null;
        node.next = null;
    }
}
