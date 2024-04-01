package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public abstract class EntityPersister<T> {





    abstract public T getById(Serializable id);

    abstract public List<T> getAll();

    public void save(T obj){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
        }
    }

    public void update(T obj){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
        }
    }

    public void delete(T obj){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(obj);
            session.getTransaction().commit();
        }
    }
}
