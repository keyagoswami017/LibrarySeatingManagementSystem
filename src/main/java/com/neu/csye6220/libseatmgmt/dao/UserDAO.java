package com.neu.csye6220.libseatmgmt.dao;

import com.neu.csye6220.libseatmgmt.dao.interfaces.IUserDAO;
import com.neu.csye6220.libseatmgmt.exception.DataAccessException;
import com.neu.csye6220.libseatmgmt.model.User;
import com.neu.csye6220.libseatmgmt.model.Admin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO extends BaseDAO implements IUserDAO {

    @Override
    public void createUser(User user) {
        // Implementation to save user to the database
        try {
                begin();
                getSession().persist(user);
                commit();
        } catch(HibernateException e){
            rollback();
            throw new DataAccessException("Error saving User", e);
        } finally {
            close(); // <<< VERY IMPORTANT
        }
    }
    @Override
    public User updateUser(User user) {
        // Implementation to update user in the database
        try {
                begin();
                getSession().merge(user);
                commit();
                return user;
        } catch(HibernateException e){
            rollback();
            throw new DataAccessException("Error updating User", e);
        } finally {
            close(); // <<< VERY IMPORTANT
        }
    }
    @Override
    public void registerUser(User user)  {
        // Implementation to delete user from the database
        createUser(user);
    }
    @Override
    public User getUserById(Long id)   {
        // Implementation to get user by ID from the database
        try {
            return getSession().get(User.class, id);
        } catch (HibernateException e) {
            throw new DataAccessException("Error Fetching User by ID "+ id, e);
        } finally {
            close(); // <<< VERY IMPORTANT
        }
    }
    @Override
    public User getUserByEmail(String email)   {
        // Implementation to get user by email from the database
       try {
            begin();
            Session session = getSession();
              return session.createQuery("FROM User WHERE email = :email", User.class)
                        .setParameter("email", email)
                      .uniqueResultOptional()
                        .orElse(null);

       } catch (HibernateException e) {
              rollback();
           throw new DataAccessException("Error Fetching User by Email "+ email, e);
       } finally {
           close(); // <<< VERY IMPORTANT
       }
    }
    @Override
    public boolean emailExists(String email){
        // Implementation to check if email exists in the database
        try {
            begin();
            Long count = getSession().createQuery(
                            "SELECT COUNT(*) FROM User WHERE email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();

            count += getSession().createQuery(
                            "SELECT COUNT(*) FROM Admin WHERE email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();
            commit();
            return count > 0;
        } catch (HibernateException e){
            return false;
        } finally {
            close(); // <<< VERY IMPORTANT
        }
    }

    @Override
    public List<User> getAllUsers(){
        // Implementation to get all users from the database
        try {
                begin();
                return getSession().createQuery("FROM User", User.class)
                        .list();

        } catch (HibernateException e){
            rollback();
            throw new DataAccessException("Error Fetching All Users", e);
        } finally {
            close(); // <<< VERY IMPORTANT
        }

    }

}
