package com.bystrican.Notes;

import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
@Alternative
public class taskDaoJPA implements TaskDao {
    @PostgresDB
    @PersistenceContext(unitName = "jta")
    EntityManager entityManager;
    @Override
    public void delete(int id) {
        Task task = this.read(id);
        if(task ==null) return ;
        task = entityManager.merge(task);
        entityManager.remove(task);
    }


    @Override
    public void insert(Task task) {
        entityManager.persist(task);
    }

    @Override
    public void update(Task task) {
        entityManager.merge(task);
    }

    @Override
    public Task read(int id) {
        return entityManager.find(Task.class,id);
    }

    @Override
    public List<Task> read() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = criteriaBuilder.createQuery(Task.class);
        Root<Task> taskRoot = criteria.from(Task.class);
        criteria.select(taskRoot).orderBy(criteriaBuilder.asc(taskRoot.get("id")));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    //search text in name or description
    public List<Task> search(String searched) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = criteriaBuilder.createQuery(Task.class);
        Root<Task> taskRoot = criteria.from(Task.class);
        Predicate inName= criteriaBuilder.like(taskRoot.get("name"),("%"+searched+"%"));
        Predicate inDescription=criteriaBuilder.like(taskRoot.get("description"),("%"+searched+"%"));
        Predicate inNameOrDescription= criteriaBuilder.or(inName,inDescription);
        criteria.select(taskRoot).where(inNameOrDescription);
        return entityManager.createQuery(criteria).getResultList();
    }
}
