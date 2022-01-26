package pl.coderslab.entity;

import java.sql.SQLException;

public class MainDao {
    public static void main(String[] args) throws SQLException {

        User user = new User("kacperka","kacper@o2.pl","kuul35,");
        UserDao userDao = new UserDao();
//        userDao.create(user);


        userDao.read(15);

    }
}
