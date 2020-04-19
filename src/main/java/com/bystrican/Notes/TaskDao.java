package com.bystrican.Notes;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TaskDao {
    void delete(int id);
    void insert(Task task);
    void update( Task task);
    Task read(int id);
    List<Task> read();
    List<Task> search(String searched);
}
