package by.artem.je.jdbc.dao;

import by.artem.je.jdbc.dao.classes.Aircraft;
import by.artem.je.jdbc.dao.classes.Ticket;
import by.artem.je.jdbc.dao.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao implements Dao<Long, Ticket>{
    private static final TicketDao INSTANCE = new TicketDao();

    private TicketDao() {
    }

    private final String CREATE_SQL = """
            insert into flight_repo.public.ticket (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?);
            """;
    private final String READ_ALL_SQL = """
                        select id, model from flight_repo.public.ticket
            """;
    private final String READ_BY_ID_SQL = READ_ALL_SQL + """
                        where id = ?
            """;
    private final String UPDATE_SQL = """
                           update flight_repo.public.ticket set 
                             passport_no = ?,
                             passenger_name = ?,
                             flight_id = ?,
                             seat_no = ?,
                             cost = ?
                             where id = ?;
            """;
    private final String DELETE_SQL = """
                   delete from flight_repo.public.ticket where id = ?          
            """;

    public TicketDao getInstance(){
        return INSTANCE;
    }

    @Override
    public Ticket create(Ticket ticket) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setBytes(4, ticket.getSeatNo());
            statement.setFloat(5, ticket.getCost());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                ticket.setId(keys.getLong("id"));
            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Ticket ticket) {
        try (Connection connectionManager = ConnectionManager.get()) {
            var statement = connectionManager.prepareStatement(UPDATE_SQL);
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setBytes(4, ticket.getSeatNo());
            statement.setFloat(5, ticket.getCost());
            statement.setFloat(6, ticket.getId());
            var resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ticket buildTicket(ResultSet result) throws SQLException {
        return new Ticket(
                result.getLong("id"),
                result.getString("passport_no"),
                result.getString("passgenger_name"),
                result.getLong("flight_no"),
                result.getBytes("seat_no"),
                result.getFloat("cost")
        );
    }

    @Override
    public List<Ticket> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_ALL_SQL);
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                tickets.add(buildTicket(result));
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> findById(Long aLong) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setLong(1, aLong);
            var result = statement.executeQuery();
            Ticket ticket = null;
            if (result.next())
                ticket = buildTicket(result);

            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, aLong);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
