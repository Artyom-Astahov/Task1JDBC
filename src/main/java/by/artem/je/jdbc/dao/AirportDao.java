package by.artem.je.jdbc.dao;

import by.artem.je.jdbc.dao.classes.Airport;
import by.artem.je.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirportDao implements Dao<byte[], Airport> {

    private static final AirportDao INSTANCE = new AirportDao();

    private AirportDao() {
    }

    private final String CREATE_SQL = """
            insert into flight_repo.public.airport (code, country, city)
            VALUES (?, ?, ?);
            """;
    private final String READ_ALL_SQL = """
                        select code, country, city from flight_repo.public.airport
            """;
    private final String READ_BY_ID_SQL = READ_ALL_SQL + """
                        where code = ?
            """;
    private final String UPDATE_SQL = """
                           update flight_repo.public.airport set country = ?, city = ? where code = ?;
            """;
    private final String DELETE_SQL = """
                   delete from flight_repo.public.airport where code = ?
            """;

    public static AirportDao getInstance() {
        return INSTANCE;
    }


    @Override
    public Airport create(Airport airport) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setBytes(1, airport.getCode());
            statement.setString(2, airport.getCountry());
            statement.setString(3, airport.getCity());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                airport.setCode(keys.getBytes("code"));
            return airport;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Airport airport) {
        try (Connection connectionManager = ConnectionManager.get()) {
            var statement = connectionManager.prepareStatement(UPDATE_SQL);
            statement.setBytes(3, airport.getCode());
            statement.setString(1, airport.getCountry());
            statement.setString(2, airport.getCity());
            var resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Airport buildAirport(ResultSet result) throws SQLException {
        return new Airport(
                result.getBytes("code"),
                result.getString("country"),
                result.getString("city")
        );
    }

    @Override
    public List<Airport> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_ALL_SQL);
            List<Airport> airports = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                airports.add(buildAirport(result));
            return airports;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Airport> findById(byte[] bytes) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setBytes(1, bytes);
            var result = statement.executeQuery();
            Airport airport = null;
            if (result.next())
                airport = buildAirport(result);
            return Optional.ofNullable(airport);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(byte[] bytes) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(DELETE_SQL);
            statement.setBytes(1, bytes);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
