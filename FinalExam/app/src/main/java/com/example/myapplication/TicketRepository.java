package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketRepository {

    public interface DataCallback<T> {
        void onData(T data);
    }

    private final TicketDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    // استفاده از Data Structure
    private final QueueManager queueManager = new QueueManager();

    public TicketRepository(Context context) {
        dao = AppDatabase.getInstance(context).ticketDao();
    }

    public void getAll(DataCallback<List<Ticket>> cb) {
        executor.execute(() -> {
            List<Ticket> list = dao.getAllOrdered();
            queueManager.setAll(list);
            mainHandler.post(() -> cb.onData(list));
        });
    }

    public void addTicket(String name, DataCallback<List<Ticket>> cb) {
        executor.execute(() -> {
            int maxPos = dao.getMaxPosition();
            int newPos = maxPos + 1;

            Ticket t = new Ticket(name, newPos, System.currentTimeMillis());
            long id = dao.insert(t);
            t.id = id;

            queueManager.enqueue(t);

            List<Ticket> list = dao.getAllOrdered();
            queueManager.setAll(list);

            mainHandler.post(() -> cb.onData(list));
        });
    }

    public void deleteTicket(Ticket ticket, DataCallback<List<Ticket>> cb) {
        executor.execute(() -> {
            dao.delete(ticket);
            queueManager.removeById(ticket.id);

            // بعد از حذف، position ها باید از نو مرتب شوند
            List<Ticket> list = dao.getAllOrdered();
            for (int i = 0; i < list.size(); i++) {
                dao.updatePosition(list.get(i).id, i);
            }

            List<Ticket> finalList = dao.getAllOrdered();
            queueManager.setAll(finalList);

            mainHandler.post(() -> cb.onData(finalList));
        });
    }

    // ذخیره ترتیب جدید پس از Drag & Drop
    public void saveOrder(List<Ticket> ordered, DataCallback<List<Ticket>> cb) {
        executor.execute(() -> {
            for (int i = 0; i < ordered.size(); i++) {
                dao.updatePosition(ordered.get(i).id, i);
            }

            List<Ticket> list = dao.getAllOrdered();
            queueManager.setAll(list);

            mainHandler.post(() -> cb.onData(list));
        });
    }
}
