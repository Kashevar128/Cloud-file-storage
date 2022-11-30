package org.vinogradov.common.commonClasses;

import java.io.Serializable;

public class User implements Serializable {

    private final String nameUser;

    private final String password;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof User user)) return false;
        return user.getNameUser().equals(this.getNameUser())
                && user.getPassword().equals(this.getPassword());
    }
}
