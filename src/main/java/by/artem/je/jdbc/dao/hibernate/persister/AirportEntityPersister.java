package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.classes.Aircraft;
import by.artem.je.jdbc.dao.classes.Airport;
import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public class AirportEntityPersister extends EntityPersister<Airport>{


    @Override
    public Airport getById(Serializable id) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Airport obj = session.get(Airport.class, id);
            session.getTransaction().commit();
            return obj;
        }
    }

    @Override
    public List<Airport> getAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Airport> list =  session.createCriteria(Airport.class).list();
            session.getTransaction().commit();
            return list;
        }
    }
}
