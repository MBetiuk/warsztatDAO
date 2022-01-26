package pl.coderslab.entity;

import pl.coderslab.cryptography.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email=?, username=?, password=? WHERE id=?";
    private static final String READ_ID_USER_QUERY = "SELECT * FROM users WHERE  id=?";
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String DELETE_ID_USER_QUERY = "DELETE  FROM users WHERE  id=?";

    public User create(User user) throws SQLException {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
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

    public User readMine(int userId) {
        User user = new User();

        try (Connection conn = DbUtil.getConnection();) {
            PreparedStatement stat = conn.prepareStatement(READ_ID_USER_QUERY);
            stat.setInt(1, userId);
            ResultSet resultSet = stat.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user);
                String id = String.valueOf(resultSet.getInt(1));
                userId = Integer.parseInt(id);
                System.out.println("odczytano użytkownika o id " + userId);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();

            /*TODO - po wpisaniu id, ktorego nie ma w bazie, NIE ZWRACA mi null */
        }
        return null;

    } //moja wersja

    public User read(int userId) {
        User user = new User();
        try (Connection conn = DbUtil.getConnection();) {
            PreparedStatement stat = conn.prepareStatement(READ_ID_USER_QUERY);

            stat.setInt(1, userId);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName((rs.getString("username")));
                user.setPassword(rs.getString("password"));
                System.out.println(user);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }  //wersja wg Arka

    public void delete(int userId) {
        User user = new User();

        try (Connection conn = DbUtil.getConnection();) {
            PreparedStatement stat = conn.prepareStatement(DELETE_ID_USER_QUERY);
            stat.setInt(1, userId);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement stat = conn.prepareStatement(UPDATE_USER_QUERY);
            stat.setString(1, user.getUserName());
            stat.setString(2, user.getEmail());
            stat.setString(3, this.hashPassword(user.getPassword()));
            stat.setInt(4, user.getId());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {  /* TODO - updateUser wg mnie-poprawić */
        try (Connection conn = DbUtil.getConnection();) {
            PreparedStatement stat = conn.prepareStatement(UPDATE_USER_QUERY);
            stat.setInt(1, user.getId());
            stat.setString(2, user.getUserName());
            stat.setString(3, user.getEmail());
            stat.setString(4, hashPassword(user.getPassword()));

            stat.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public User[] readAll() {
        User[] users = new User[0];
        try (Connection conn = DbUtil.getConnection();) {
            PreparedStatement stat = conn.prepareStatement(READ_ALL_USERS_QUERY);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users = Arrays.copyOf(users, users.length + 1);
                user = users[users.length - 1];
                for (User u : users) {
                    System.out.println(u);
                }
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }  /* TODO - zamienić na wyszukaj WSZYTKO po PODAJ PARAMETR  - tablica obiektów?*/

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}



