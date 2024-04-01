package by.artem.je.jdbc.dao.hibernate;

import by.artem.je.jdbc.dao.classes.Aircraft;
import by.artem.je.jdbc.dao.hibernate.persister.AircraftEntityPersister;

public class HibernateRunner {

    public static void main(String[] args) {
        AircraftEntityPersister aircraftEntityPersister = new AircraftEntityPersister();
        System.out.println(aircraftEntityPersister.getAll());
//        System.out.println(aircraftEntityPersister.getById(3));
    }
}
