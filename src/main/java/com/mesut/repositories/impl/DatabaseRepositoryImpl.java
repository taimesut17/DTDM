package com.mesut.repositories.impl;

import com.mesut.pojo.DatabaseUser;
import com.mesut.repositories.DatabaseRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DatabaseRepositoryImpl implements DatabaseRepository {

    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    public DatabaseUser createDatabase(DatabaseUser d) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        s.persist(d);
        return d;
    }

    @Override
    public DatabaseUser getDatabaseById(int id) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT u FROM DatabaseUser u WHERE u.id = :id", DatabaseUser.class);
        q.setParameter("id",id);
        return (DatabaseUser) q.getSingleResult();
    }

    @Override
    public List<DatabaseUser> getDatabaseByUsername(String name) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT u FROM DatabaseUser u WHERE u.username = :username", DatabaseUser.class);
        q.setParameter("username",name);
        return q.getResultList();
    }

    @Override
    public void deleteDatabaseByUsernameAndName(String username, String name) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT u FROM DatabaseUser u WHERE u.username = :username and u.name = :name", DatabaseUser.class);
        q.setParameter("username",username);
        q.setParameter("name",name);

        // Tìm database theo ID
        DatabaseUser d = (DatabaseUser) q.getSingleResult();

        if (d != null) {
            // Xóa database nếu tìm thấy
            s.delete(d);
        } else {
            // Nếu không tìm thấy database với id này
            throw new IllegalArgumentException("Database does not exist");
        }
    }

    @Override
    public boolean isEmptyDatabase(String username, String name) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT u FROM DatabaseUser u WHERE u.username = :username and u.name = :name", DatabaseUser.class);
        q.setParameter("username",username);
        q.setParameter("name",name);

        return q.getResultList().isEmpty();
    }
}
