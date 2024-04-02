package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.classes.Seat;
import by.artem.je.jdbc.dao.classes.Ticket;
import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketEntityPersister extends EntityPersister<Ticket> {

    @Getter
    private static final TicketEntityPersister INSTANCE = new TicketEntityPersister();

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
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Ticket> list = session.createCriteria(Ticket.class).list();
            session.getTransaction().commit();
            return list;
        }
    }

    public List<Ticket> getAllByFlightId(Long id) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sqlQueryByFlightId = """
                    SELECT id, passport_no, passenger_name, flight_id, seat_no, cost 
                    from flight_repo.public.ticket as t 
                    where t.flight_id = %s
                                        
                    """.formatted(id);
            List<Ticket> list = session.createSQLQuery(sqlQueryByFlightId).addEntity(Ticket.class).list();
            session.getTransaction().commit();
            return list;

        }
    }
}
