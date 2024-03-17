package by.Artem.je.util;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public final class DatabaseQueries {

    private static DatabaseQueries INSTANCE;
    private Connection connectionManager;
    private final Statement statement;

    private DatabaseQueries() throws SQLException {
        connectionManager = ConnectionManager.open();
        statement = connectionManager.createStatement();
    }


    public static DatabaseQueries getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new DatabaseQueries();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    public void close(){
        try {
            connectionManager.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public HashMap<String, Integer> getCommonNames() throws SQLException {
        String sqlRequest = """
                select passenger_name, count(passenger_name) as count_name 
                from flight_repo.public.ticket 
                group by passenger_name 
                having count(passenger_name) > 1
                order by count_name desc 
                limit 3;
                """;
        ResultSet resultSet = this.statement.executeQuery(sqlRequest);
        HashMap<String, Integer> commonNamesMap = new HashMap<>();
        while (resultSet.next()) {
            String key = resultSet.getString("passenger_name");
            Integer value = resultSet.getInt("count_name");
            commonNamesMap.put(key, value);
        }
        return commonNamesMap;
    }

    public HashMap<String, Integer> getTicketsPurchased() throws SQLException {
        String sqlRequest = """
                select passenger_name, count(passenger_name) as tickets_purchased 
                from flight_repo.public.ticket 
                group by passenger_name 
                order by tickets_purchased desc;
                """;
        ResultSet resultSet = this.statement.executeQuery(sqlRequest);
        HashMap<String, Integer> ticketsPurchasedMap = new HashMap<>();
        while (resultSet.next()) {
            String key = resultSet.getString("passenger_name");
            Integer value = resultSet.getInt("tickets_purchased");
            ticketsPurchasedMap.put(key, value);
        }
        return ticketsPurchasedMap;
    }

    public int updateTicketName(int id, String name) throws SQLException {
        String sqlRequest = """
                    update flight_repo.public.ticket set passenger_name = '%s' where id = %s
                    """.formatted(name, id);
        int resultSet = this.statement.executeUpdate(sqlRequest);
        return resultSet;
    }

    public int[] updateTableFlightAndTicket(int flight_id) throws SQLException {
        String sqlRequestTicket = """
                update flight_repo.public.ticket set passenger_name = 'Ivan Ivanich' where flight_id = %s
                """.formatted(flight_id);
        String sqlRequestFlight = """
                update flight_repo.public.flight set status = 'CANCELLED' where id = %s
                """.formatted(flight_id);
        int reusltSetTicket = this.statement.executeUpdate(sqlRequestTicket);
        int reusltSetFlight = this.statement.executeUpdate(sqlRequestFlight);
        return new int[]{reusltSetTicket, reusltSetFlight};
    }


}
