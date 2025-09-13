package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao, AutoCloseable {
    EntityManager entityManager;
    EntityManagerFactory entityManagerFactory;

    public UserDaoHibernateImpl() {
        try (StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build()) {
            entityManagerFactory = Persistence.createEntityManagerFactory("UsersPersUnit");

            entityManager = entityManagerFactory.createEntityManager();
        }
    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Query q = entityManager.createNativeQuery(sql);

        int i = q.executeUpdate();

//        HibernateUtil
//        try (Session session = getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            sql = "CREATE TABLE IF NOT EXISTS users " +
//                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
//                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
//                    "age TINYINT NOT NULL)";
//
//            Query query = session.createSQLQuery(sql).addEntity(User.class);
//
//            transaction.commit();
    }
    //session.close();


    @Override
    public void dropUsersTable() {
        final String sql =
                "DROP TABLE `" + TABLE_NAME + "`";

        Query q = entityManager.createNativeQuery(sql);

        int i = q.executeUpdate();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();

            transaction.begin();
            User user = new User(name, lastName, age);
            entityManager.persist(user);

            transaction.commit();
        } catch (Exception e) {
            // 5. Откатываем транзакцию в случае ошибки
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error saving entity: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try {
            String hql = "SELECT u FROM User u";
            TypedQuery<User> query = entityManager.createQuery(hql, User.class);

            users = query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

    }

    @Override
    public void close() throws Exception {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
