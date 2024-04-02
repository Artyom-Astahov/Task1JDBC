package by.artem.je.jdbc.dao.hibernate;

import by.artem.je.jdbc.dao.classes.Ticket;
import by.artem.je.jdbc.dao.hibernate.persister.AircraftEntityPersister;
import by.artem.je.jdbc.dao.hibernate.persister.TicketEntityPersister;
import by.artem.je.jdbc.dao.hibernate.persister.UserEntityPersister;
import by.artem.je.jdbc.entity.RoleEnum;
import by.artem.je.jdbc.entity.User;

import java.time.LocalDate;
import java.util.List;

public class HibernateRunner {

    public static void main(String[] args) {


        TicketEntityPersister ticketEntityPersister = TicketEntityPersister.getINSTANCE();
        List<Ticket> list = ticketEntityPersister.getAllByFlightId(2L);

//        System.out.println(userEntityPersister.getAll());
////        System.out.println(userEntityPersister.getById(0));

    }
}
