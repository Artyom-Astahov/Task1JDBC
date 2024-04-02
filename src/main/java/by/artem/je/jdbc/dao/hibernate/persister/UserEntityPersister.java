package by.artem.je.jdbc.dao.hibernate.persister;

import by.artem.je.jdbc.dao.classes.Ticket;
import by.artem.je.jdbc.dao.hibernate.util.HibernateUtil;
import by.artem.je.jdbc.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityPersister extends EntityPersister<User> {

    @Getter
    private static final UserEntityPersister INSTANCE = new UserEntityPersister();

    @Override
    public User getById(Serializable id) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User obj = session.get(User.class, id);
            session.getTransaction().commit();
            return obj;
        }
    }

    @Override
    public List<User> getAll() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> list = session.createCriteria(User.class).list();
            session.getTransaction().commit();
            return list;
        }
    }

    public Optional<User> getEmailAndPassword(String email, String password) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sqlQueryGetEmailAndPassword = """
                     select id, name, birthday, email, password, role from flight_repo.public.users 
                     where email = '%s' and password = '%s'
                    """.formatted(email, password);
            List<User> list  = session.createSQLQuery(sqlQueryGetEmailAndPassword).addEntity(User.class).list();
            User user = null;
            if(list.iterator().hasNext()){
                user = list.iterator().next();
            }

            session.getTransaction().commit();
            return Optional.ofNullable(user);
        }
    }
}
