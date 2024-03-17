package by.Artem.je.jdbc;

import by.Artem.je.util.DatabaseQueries;

import java.sql.SQLException;
import java.util.Arrays;

public class jdbcRunner {
    public static void main(String[] args) throws SQLException {
        DatabaseQueries dbq = DatabaseQueries.getInstance();
        System.out.println(dbq.getCommonNames());
        System.out.println(dbq.getTicketsPurchased());
        System.out.println(dbq.updateTicketName(2, "Egor Petrovich"));
        System.out.println(Arrays.toString(dbq.updateTableFlightAndTicket(2)));
        dbq.close();

    }
}
