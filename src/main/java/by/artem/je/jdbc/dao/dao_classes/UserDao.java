package by.artem.je.jdbc.dao.dao_classes;

import by.artem.je.jdbc.entity.Role;
import by.artem.je.jdbc.entity.User;
import by.artem.je.jdbc.util.ConnectionManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements Dao<Long, User> {

    @Getter
    private static final UserDao INSTANCE = new UserDao();

    private static final String CREATE_SQL =
            "insert into flight_repo.public.users (name, birthday, email, password, role) values (?,?,?,?,?)";

    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL =
            "select * from flight_repo.public.users where email = ? and password = ?";

    @Override
    public User create(User user) {

        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(CREATE_SQL, RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setDate(2, Date.valueOf(user.getBirthday()));
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                user.setId(keys.getInt("id"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(User user) {
        return false;
    }

    private User buildEnity(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("birthday").toLocalDate(),
                resultSet.getString("email"),
                resultSet.getString("password"),
               Role.valueOf(resultSet.getString("role"))
        );
    }
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public Optional<User> findEmailAndPassword(String email, String password){
        try (Connection connection = ConnectionManager.get()) {
            var statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL);
            statement.setString(1, email);
            statement.setString(2, password);

            var resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()){
                user = buildEnity(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
