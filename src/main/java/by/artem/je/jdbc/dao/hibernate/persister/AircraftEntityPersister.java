package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.classes.Aircraft;
import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AircraftEntityPersister extends EntityPersister<Aircraft> {

    @Getter
    private static final AircraftEntityPersister INSTANCE = new AircraftEntityPersister();


    @Override
    public Aircraft getById(Serializable id) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Aircraft obj = session.get(Aircraft.class, id);
            session.getTransaction().commit();
            return obj;
        }
    }

    @Override
    public List<Aircraft> getAll() {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Aircraft> list =  session.createCriteria(Aircraft.class).list();
            session.getTransaction().commit();
            return list;
        }
    }
}
