package by.artem.je.jdbc.dao;

import by.artem.je.jdbc.dao.classes.Aircraft;
import by.artem.je.jdbc.dao.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AircraftDao implements Dao<Integer, Aircraft> {
    private static final AircraftDao INSTANCE = new AircraftDao();

    private AircraftDao() {
    }

    private final String CREATE_SQL = """
            insert into flight_repo.public.aircraft (model)
            VALUES (?);
            """;
    private final String READ_ALL_SQL = """
                        select id, model from flight_repo.public.aircraft
            """;
    private final String READ_BY_ID_SQL = READ_ALL_SQL + """
                        where id = ?
            """;
    private final String UPDATE_SQL = """
                           update flight_repo.public.aircraft set model = ? where id = ?;
            """;
    private final String DELETE_SQL = """
                   delete from flight_repo.public.aircraft where id = ?          
            """;

    public static AircraftDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Aircraft create(Aircraft aircraft) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, aircraft.getModel());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                aircraft.setId(keys.getInt("id"));
            return aircraft;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Aircraft aircraft) {
        try (Connection connectionManager = ConnectionManager.get()) {
            var statement = connectionManager.prepareStatement(UPDATE_SQL);
            statement.setInt(2, aircraft.getId());
            statement.setString(1, aircraft.getModel());
            var resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Aircraft buildAircraft(ResultSet result) throws SQLException {
        return new Aircraft(
                result.getInt("id"),
                result.getString("model")
        );
    }

    @Override
    public List<Aircraft> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_ALL_SQL);
            List<Aircraft> aircrafts = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                aircrafts.add(buildAircraft(result));
            return aircrafts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Aircraft> findById(Integer integer) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setInt(1, integer);
            var result = statement.executeQuery();
            Aircraft aircraft = null;
            if (result.next())
                aircraft = buildAircraft(result);

            return Optional.ofNullable(aircraft);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer integer) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(DELETE_SQL);
            statement.setInt(1, integer);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
