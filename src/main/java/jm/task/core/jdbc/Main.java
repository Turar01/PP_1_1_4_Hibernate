package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;



public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserService userDao = new UserServiceImpl();

        userDao.createUsersTable();
        userDao.saveUser("Naruto", "Uzumaki", (byte) 18);
        userDao.saveUser("Kakashi", "Hatake", (byte) 30);
        userDao.saveUser("Sasuke", "Uchiha", (byte) 19);
        userDao.saveUser("Sakura", "Haruno", (byte) 17);

        userDao.getAllUsers().forEach(System.out::println);

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
