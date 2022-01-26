package pl.coderslab.entity;

import pl.coderslab.cryptography.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainDao {
    public static void main(String[] args) throws SQLException {

        User user = new User("kacperka","kacper@o2.pl","kuul35,");

        UserDao userDao = new UserDao();
//        userDao.create(user); ok
//        userDao.readMine(1);
//        userDao.read(12); ok
//        System.out.println(userDao.read(12)) ;ok  /* TODO - trzeba tutaj dać souta, bo z metody się nie drukuje */
        userDao.readAll(); /* TODO - nie drukuje sie tablica */
//        userDao.delete(12); ok

//        User userToUpdate = userDao.read(15); /*TODO - nie korzysta z metody update */
//        userToUpdate.setUserName("Arkadiuszmmm");  /* TODO - ustawia ręcznie */
//        userToUpdate.setEmail("arekmmm@coderslab.pl");
//        userToUpdate.setPassword("superPassword");
//        userDao.update(userToUpdate);




    }

}
