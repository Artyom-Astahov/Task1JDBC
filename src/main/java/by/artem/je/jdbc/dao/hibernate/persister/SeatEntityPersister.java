package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.classes.Flight;
import by.artem.je.jdbc.dao.classes.Seat;
import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatEntityPersister extends EntityPersister<Seat> {

    @Getter
    private static final SeatEntityPersister INSTANCE = new SeatEntityPersister();
    @Override
    public Seat getById(Serializable id) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Seat obj = session.get(Seat.class, id);
            session.getTransaction().commit();
            return obj;
        }
    }

    @Override
    public List<Seat> getAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Seat> list =  session.createCriteria(Seat.class).list();
            session.getTransaction().commit();
            return list;
        }
    }
}