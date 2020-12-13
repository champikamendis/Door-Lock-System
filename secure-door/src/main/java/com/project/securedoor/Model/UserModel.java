package com.project.securedoor.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String authority;


    public  String getAuthority() { return authority; }

    public void setAuthority(String authority) { this.authority = authority; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public void setEnabled(boolean b) {
    }
}
