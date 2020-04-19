package com.bystrican.Notes;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.List;
@Local(TaskService.class)
@Stateless
@Alternative
public class TaskServiceEjb implements TaskService {
    @Inject
    TaskDao taskDao;
    @Override
    public void delete(int id) {
        taskDao.delete(id);
    }

    @Override
    public void insert(Task task) {
        taskDao.insert(task);
    }

    @Override
    public void update( Task task) {
        taskDao.update(task);
    }

    @Override
    public Task read(int id) {
        return taskDao.read(id);
    }

    @Override
    public List<Task> read() {
        return taskDao.read();
    }

    @Override
    public List<Task> search(String searched) {
        return taskDao.search(searched);
    }
}
