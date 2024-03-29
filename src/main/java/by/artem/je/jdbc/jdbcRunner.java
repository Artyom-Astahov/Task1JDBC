package by.artem.je.jdbc;

import by.artem.je.jdbc.dao.FlightDao;
import by.artem.je.jdbc.dao.TicketDao;

import java.sql.SQLException;

public class jdbcRunner {
    public static void main(String[] args) throws SQLException {
        FlightDao flightDao = FlightDao.getInstance();
        TicketDao ticketDao = TicketDao.getInstance();
        ticketDao.findAllByFlightId(2L);

//        System.out.println(aircraftDao.create(aircraft));
//        System.out.println(aircraftDao.update(aircraft));
//        System.out.println(aircraftDao.delete(5));
        System.out.println(flightDao.findAll());

    }
}
