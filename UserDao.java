import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static String url = "jdbc:postgresql://localhost:5432/hw14";
    private static String user = "postgres";
    private static String password = "root";

    private static final String INSERT_USER_SQL = "INSERT INTO users (login, password) VALUES (?, ?);";
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM USERS WHERE  login = ?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM USERS";
    private static final String DELETE_USERS_SQL = "DELETE FROM USERS WHERE login = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE USERS SET login = ?, password = ? WHERE login = ?;";

    public UserDao() {
    }

    public void insertUser(User user) {
        try (Connection connection = DBConnection.connect(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User selectUser(String login) {
        User user = null;
        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String loginUser = resultSet.getString("login");
                String passwordUser = resultSet.getString("password");
                user = new User(loginUser, passwordUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> selectAllUser() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String loginUser = resultSet.getString("login");
                String passwordUser = resultSet.getString("password");
                users.add(new User(loginUser, passwordUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(String login) throws SQLException {
        boolean rowDeleted = false;
        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
            preparedStatement.setString(1, login);
            System.out.println(preparedStatement);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public boolean updateUser(User user, String login) {
        boolean rowUpdated = false;
        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, login);
            System.out.println(preparedStatement);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean isExists(String login) {
        User user = selectUser(login);
        if (user != null) {
            return true;
        }
        return false;
    }

}
