package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users" +
            "(id BIGINT AUTO_INCREMENT PRIMARY KEY , " +
            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String CLEAR_TABLE_SQL = "DELETE FROM User";
    private static final String GET_ALL_USERS_HQL = "FROM User";

    public UserDaoHibernateImpl() {

    }

    private Session getSession() {
        return getSessionFactory().openSession();
    }

    private void executeInTransaction(Session session, Runnable action) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            action.run();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        try (Session session = getSession()) {
            executeInTransaction(session, () -> {
                Query<User> query = session.createSQLQuery(CREATE_TABLE_SQL).addEntity(User.class);
                query.executeUpdate();
            });
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSession()) {
            executeInTransaction(session, () -> {
                Query<User> query = session.createSQLQuery(DROP_TABLE_SQL).addEntity(User.class);
                query.executeUpdate();
            });
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSession()) {
            executeInTransaction(session, () -> {
                User user = new User(name, lastName, age);
                session.save(user);
            });
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSession()) {
            executeInTransaction(session, () -> {
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                }
            });
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSession()) {
            return session.createQuery(GET_ALL_USERS_HQL, User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSession()) {
            executeInTransaction(session, () -> {
                Query<User> query = session.createQuery(CLEAR_TABLE_SQL);
                query.executeUpdate();
            });
        }
    }

}
