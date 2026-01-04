package com.example.myapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QueueManager {

    // Data Structure: Queue با LinkedList
    private final LinkedList<Ticket> queue = new LinkedList<>();

    public void setAll(List<Ticket> items) {
        queue.clear();
        queue.addAll(items);
    }

    public void enqueue(Ticket ticket) {
        queue.addLast(ticket);
    }

    public boolean removeById(long id) {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).id == id) {
                queue.remove(i);
                return true;
            }
        }
        return false;
    }

    public void move(int from, int to) {
        if (from < 0 || to < 0 || from >= queue.size() || to >= queue.size()) return;
        Ticket item = queue.remove(from);
        queue.add(to, item);
    }

    public List<Ticket> snapshot() {
        return new ArrayList<>(queue);
    }
}
