package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.classes.Seat;
import by.artem.je.jdbc.dao.classes.Ticket;
import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public class TicketEntityPersister extends EntityPersister<Ticket>{
    @Override
    public Ticket getById(Serializable id) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Ticket obj = session.get(Ticket.class, id);
            session.getTransaction().commit();
            return obj;
        }
    }

    @Override
    public List<Ticket> getAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Ticket> list =  session.createCriteria(Ticket.class).list();
            session.getTransaction().commit();
            return list;
        }
    }
}
