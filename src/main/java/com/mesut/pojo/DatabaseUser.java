package com.mesut.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "databaseuser")
public class DatabaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "host")
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername_db() {
        return username_db;
    }

    public void setUsername_db(String username_db) {
        this.username_db = username_db;
    }

    public String getPassword_db() {
        return password_db;
    }

    public void setPassword_db(String password_db) {
        this.password_db = password_db;
    }

    @Basic(optional = false)
    @Column(name = "username_db")
    private String username_db;

    @Basic(optional = false)
    @Column(name = "password_db")
    private String password_db;
}
