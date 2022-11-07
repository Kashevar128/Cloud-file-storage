package org.vinogradov.common.commonClasses;

import java.io.Serializable;

public class User implements Serializable {

    private String nameUser;

    private String password;

    public User(String nameUser, String password) {
        this.nameUser = nameUser;
        this.password = password;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPassword() {
        return password;
    }
}
