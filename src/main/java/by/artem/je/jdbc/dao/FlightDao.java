package by.artem.je.jdbc.dao;

import by.artem.je.jdbc.dao.classes.Aircraft;
import by.artem.je.jdbc.dao.classes.Flight;
import by.artem.je.jdbc.dao.classes.FlightStatus;
import by.artem.je.jdbc.dao.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight>{

    private static final FlightDao INSTANCE = new FlightDao();

    private FlightDao() {
    }
    private final String CREATE_SQL = """
            insert into flight_repo.public.flight (
            flight_no, 
            departure_date, 
            departure_airport_code, 
            arrival_date,
            arrival_airport_code, 
            aircraft_id, 
            status)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    private final String READ_ALL_SQL = """
                        select id, model from flight_repo.public.flight
            """;
    private final String READ_BY_ID_SQL = READ_ALL_SQL + """
                        where id = ?
            """;
    private final String UPDATE_SQL = """
                           update flight_repo.public.flight set 
                           flight_no = ?, 
                           departure_date = ?,
                           departure_airport_code = ?,
                           arrival_date = ?,
                           aircraft_id = ?,
                           status = ?
                                  where id = ?;
            """;
    private final String DELETE_SQL = """
                   delete from flight_repo.public.aircraft where id = ?          
            """;

    public FlightDao getInstance(){
        return INSTANCE;
    }


    @Override
    public Flight create(Flight flight) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, flight.getFlightNo());
            statement.setDate(2, (Date) flight.getDepartureDate());
            statement.setBytes(3, flight.getDepartureAirportCode());
            statement.setDate(4, (Date) flight.getArrivalDate());
            statement.setInt(5,  flight.getAircraftId());
            statement.setString(6, flight.getStatus().toString());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                flight.setId(keys.getLong("id"));
            return flight;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Flight flight) {
        try (Connection connectionManager = ConnectionManager.get()) {
            var statement = connectionManager.prepareStatement(UPDATE_SQL);
            statement.setString(1, flight.getFlightNo());
            statement.setDate(2, (Date) flight.getDepartureDate());
            statement.setBytes(3, flight.getDepartureAirportCode());
            statement.setDate(4, (Date) flight.getArrivalDate());
            statement.setInt(5,  flight.getAircraftId());
            statement.setString(6, flight.getStatus().toString());
            var resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Flight buildFlight(ResultSet result) throws SQLException {
        return new Flight(
                result.getLong("id"),
                result.getString("flight_no"),
                result.getDate("departure_date"),
                result.getBytes("departure_airport_code"),
                result.getDate("arrival_date"),
                result.getBytes("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status"))

        );
    }
    @Override
    public List<Flight> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_ALL_SQL);
            List<Flight> flights = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                flights.add(buildFlight(result));
            return flights;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Long aLong) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setLong(1, aLong);
            var result = statement.executeQuery();
            Flight flight = null;
            if (result.next())
                flight = buildFlight(result);

            return Optional.ofNullable(flight);
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
