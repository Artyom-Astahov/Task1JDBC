package by.artem.je.jdbc.dao.dao_classes;

import by.artem.je.jdbc.dao.classes.Seat;
import by.artem.je.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatDao implements Dao<Integer, Seat>{

    private static final SeatDao INSTANCE = new SeatDao();

    private SeatDao() {
    }
    private final String CREATE_SQL = """
            insert into flight_repo.public.seat (aircraft_id, seat_no)
            VALUES (?, ?);
            """;
    private final String READ_ALL_SQL = """
                        select aircraft_id, seat_no from flight_repo.public.seat
            """;
    private final String READ_BY_ID_SQL = READ_ALL_SQL + """
                        where aircraft_id = ?
            """;
    private final String UPDATE_SQL = """
                           update flight_repo.public.seat set seat_no = ? where aircraft_id = ?;
            """;
    private final String DELETE_SQL = """
                   delete from flight_repo.public.seat where aircraft_id = ?          
            """;

    public static SeatDao getInstance(){
        return INSTANCE;
    }

    @Override
    public Seat create(Seat seat) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(CREATE_SQL);
            statement.setInt(1, seat.getAircraftId());
            statement.setBytes(2, seat.getSeatNo());
            statement.executeUpdate();
            return seat;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Seat seat) {
        try (Connection connectionManager = ConnectionManager.get()) {
            var statement = connectionManager.prepareStatement(UPDATE_SQL);
            statement.setInt(2, seat.getAircraftId());
            statement.setBytes(1, seat.getSeatNo());
            var resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Seat buildSeat(ResultSet result) throws SQLException {
        return new Seat(
                result.getInt("aircraft_id"),
                result.getBytes("sean_no")
        );
    }
    @Override
    public List<Seat> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_ALL_SQL);
            List<Seat> seats = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                seats.add(buildSeat(result));
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Seat> findById(Integer integer) {
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(READ_BY_ID_SQL);
            statement.setInt(1, integer);
            var result = statement.executeQuery();
            Seat seat = null;
            if (result.next())
                seat = buildSeat(result);

            return Optional.ofNullable(seat);
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
