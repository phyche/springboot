package com.example.springboot.module;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="t_user")
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    /**用户ID*/
    private Integer id;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**头像*/
    private String pic;

    private String sName;
    private String studentCode;

    public User() {
    }

    public User(Integer id, String username, String password, String pic) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.pic = pic;
    }

    @Id
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="pic")
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Transient
    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    @Transient
    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
