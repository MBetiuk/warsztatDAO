package pl.coderslab.entity;

import pl.coderslab.cryptography.DbUtil;

import java.sql.*;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String MODIFY_USER_QUERY = "UPDATE users SET email=?, username=?, password=? WHERE id=?";
    private static final String READ_ID_USER_QUERY="SELECT * FROM users WHERE  id=?";
    private static final String READ_ALL_USERS_QUERY ="SELECT * FROM users";
    private static final String DELETE_ID_USER_QUERY="DELETE  FROM users WHERE  id=?";

    public User create(User user) throws SQLException {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                long id = resultSet.getLong(1);
                System.out.println("Inserted ID " + id);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        User user = new User();

        try (Connection conn = DbUtil.getConnection();){
            PreparedStatement stat = conn.prepareStatement(READ_ID_USER_QUERY);
            stat.setInt(1, userId);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            /*TODO - po wpisaniu id, ktorego nie ma w bazie, NIE ZWRACA mi null */
        }

    }
//    public User readAll(String ...columns){
//        Connection conn = DbUtil.getConnection(READ_ALL_USERS_QUERY);
//        return user;
//    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }



    }
