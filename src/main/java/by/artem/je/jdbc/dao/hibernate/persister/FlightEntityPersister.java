package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.classes.Airport;
import by.artem.je.jdbc.dao.classes.Flight;
import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightEntityPersister extends EntityPersister<Flight> {

    @Getter
    private static final FlightEntityPersister INSTANCE = new FlightEntityPersister();
    @Override
    public Flight getById(Serializable id) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Flight obj = session.get(Flight.class, id);
            session.getTransaction().commit();
            return obj;
        }
    }

    @Override
    public List<Flight> getAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Flight> list =  session.createCriteria(Flight.class).list();
            session.getTransaction().commit();
            return list;
        }
    }
}
