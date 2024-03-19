package by.artem.je.jdbc;

import by.artem.je.jdbc.dao.AircraftDao;
import by.artem.je.jdbc.dao.AirportDao;
import by.artem.je.jdbc.dao.classes.Aircraft;
import by.artem.je.jdbc.dao.util.DatabaseQueries;

import java.sql.SQLException;
import java.util.Arrays;

public class jdbcRunner {
    public static void main(String[] args) throws SQLException {
        AircraftDao aircraftDao = AircraftDao.getInstance();
        Aircraft aircraft = new Aircraft();
        aircraft.setModel("Cirrus Vision Jet");
        aircraft.setId(5);
//        System.out.println(aircraftDao.create(aircraft));
//        System.out.println(aircraftDao.update(aircraft));
//        System.out.println(aircraftDao.delete(5));
        System.out.println(aircraftDao.findById(2).get());

    }
}
